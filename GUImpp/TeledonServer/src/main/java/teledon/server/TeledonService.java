package teledon.server;
import teledon.model.CazCaritabil;
import teledon.model.Donatie;
import teledon.model.Donator;
import teledon.model.Voluntar;
import teledon.persistence.repository.ICazRepo;
import teledon.persistence.repository.IDonatieRepo;
import teledon.persistence.repository.IDonatorRepo;
import teledon.persistence.repository.IVoluntarRepo;
import teledon.services.ITeledonObserver;
import teledon.services.ITeledonServices;
import teledon.services.TeledonException;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TeledonService implements ITeledonServices {

    private ICazRepo repoCaz;
    private IDonatorRepo repoDonator;
    private IVoluntarRepo repoVoluntar;
    private IDonatieRepo repoDonatie;
    private Map<String, ITeledonObserver> loggedClients;

    private final int defaultThreadsNo = 5;

    public TeledonService(IVoluntarRepo voluntarRepo,ICazRepo cazRepo, IDonatorRepo cazDonator,IDonatieRepo donatieRepo) {
        this.repoCaz = cazRepo;
        this.repoDonatie = donatieRepo;
        this.repoVoluntar = voluntarRepo;
        this.repoDonator = cazDonator;
        loggedClients = new ConcurrentHashMap<>();
    }

    public void updateCaz(CazCaritabil caz) {
        repoCaz.update(caz);
    }

   public Voluntar findByUsername(String username) {
        return repoVoluntar.findByUsername(username);
    }

   public List<CazCaritabil> getAll() {
       List<CazCaritabil> cazuri = StreamSupport
                .stream(repoCaz.findAll().spliterator(), false)
                .collect(Collectors.toList());
       return cazuri;
   }
    public List<Donator> getAllDonatori() {
        List<Donator> donatori = StreamSupport
                .stream(repoDonator.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return donatori;
    }

   public void addCaz(String descriere, int suma) {
        CazCaritabil caz = new CazCaritabil(descriere, suma);
        repoCaz.save(caz);
   }
   public void addDonator(String nume, String prenume, String adresa, String telefon) {
        Donator d = new Donator(adresa, telefon, nume, prenume);
        repoDonator.save(d);

   }


   public void addDonatie(Donator donator, CazCaritabil caz, int suma) {
       Donatie donatie = new Donatie(donator, caz, suma);
        repoDonatie.save(donatie);
   }

   public Donator findDonatorByNume(String nume, String prenume) {
        return repoDonator.findByNume(nume, prenume);
   }

   public void deleteDonator(Long idDonator) {
        repoDonator.delete(idDonator);
   }

   public void deleteCaz(Long idCaz) {
        repoCaz.delete(idCaz);
   }

    public String hash(String pass)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes(),0,pass.length());
            String z = new BigInteger(1,md.digest()).toString(16);
            System.out.println(z);
            return z;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }



    @Override
    public synchronized void login(Voluntar voluntar, ITeledonObserver client) throws TeledonException {
        Voluntar v = repoVoluntar.findByUsername(voluntar.getUsername());
        System.out.println(v);
        if(v != null) {
            System.out.println("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            if(! v.getPassword().equals(hash(voluntar.getPassword())))
                throw new TeledonException("Username sau parola incorecte");
            System.out.println("Bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            if(loggedClients.get(v.getUsername()) != null)
                throw new TeledonException("Voluntarul este deja logat.");
            loggedClients.put(voluntar.getUsername(), client);
            System.out.println("ccccccccccccccccccccccccccccccccccc");
        }
        else {
            throw new TeledonException("Autentificare esuata");
        }
    }

    @Override
    public void logout(Voluntar voluntar, ITeledonObserver client) throws TeledonException {

    }

    private void notifyClients(Donatie donatie) {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        System.out.println("ajunge aici1");
        for(String clientLogged : loggedClients.keySet()) {
            ITeledonObserver client = loggedClients.get(clientLogged);
            System.out.println("ajunge aici2");
            if(client!=null)
            {
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + clientLogged + "]");
                        System.out.println(client);
                        System.out.println("ajunge aici3");
                        synchronized (client) {
                            System.out.println("ajunge aici4");
                            client.newDonationAdded(donatie);
                        }
                        System.out.println("Notified");
                    } catch (TeledonException | RemoteException e) {
                        System.err.println("Error notifying client " + e);
                    }
                });
            }
        }
        executor.shutdown();
    }

    @Override
    public void newDonation(Donatie donatie) throws TeledonException {
        Donator donator = donatie.getDonator();
        donator = repoDonator.findByNume(donator.getNume(), donator.getPrenume());
        CazCaritabil caz = donatie.getCaz();
        int suma = donatie.getSuma();
        System.out.println(donator);
        if(donator == null){
            addDonator(donator.getAdresa(), donator.getTelefon(), donator.getNume(), donator.getPrenume());
            donator = findDonatorByNume(donator.getNume(), donator.getPrenume());
        }

        addDonatie(donator, caz, suma);
        caz.setSumaAdunata(caz.getSumaAdunata() + suma);
        updateCaz(caz);
        notifyClients(donatie);

    }

    @Override
    public Iterable<CazCaritabil> getAllCazuri() throws TeledonException {
        return repoCaz.findAll();
    }
}

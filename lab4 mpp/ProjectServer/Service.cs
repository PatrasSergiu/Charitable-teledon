using teledon.domain;
using teledon.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using teledon.services;

namespace ProjectServer
{
    public class Service: ITeledonServices
    {

        private IVoluntarRepo RepoVoluntar;
        private ICazRepo RepoCaz;
        private IDonatorRepo RepoDonator;
        private IDonatieRepo RepoDonatie;

        //
        private readonly IDictionary<String, ITeledonObserver> loggedClients;

        public Service(IVoluntarRepo repoVoluntar, ICazRepo repoCaz, IDonatorRepo repoDonator, IDonatieRepo repoDonatie)
        {
            RepoVoluntar = repoVoluntar;
            RepoCaz = repoCaz;
            RepoDonator = repoDonator;
            RepoDonatie = repoDonatie;
            this.loggedClients = new Dictionary<String, ITeledonObserver>();
        }

        public IEnumerable<Donator> findAllDonatori()
        {
            return RepoDonator.FindAll();
        } 

        public IEnumerable<CazCaritabil> findAllCazuri()
        {
            return RepoCaz.FindAll();
        }

        public Donator findDonatorByNume(string nume, string prenume)
        {
            return RepoDonator.FindByNume(nume, prenume);
        }

        public void addDonator(string nume, string prenume, string adresa, string telefon)
        {
            Donator d = new Donator(adresa, telefon, nume, prenume);
            RepoDonator.Save(d);
        }


        public void updateCaz(CazCaritabil caz)
        {
            RepoCaz.Update(caz);
        }



        public Voluntar findVoluntar(String username)
        {
            Voluntar voluntar = null;
            RepoVoluntar.FindAll().ToList().ForEach(x =>
            {
                if (x.Username == username)
                    voluntar = x;
            });

            return voluntar;
        }

        public void login(Voluntar voluntar, ITeledonObserver client)
        {
            Console.WriteLine("Server login1");
            Voluntar v = RepoVoluntar.FindByUsername(voluntar.Username);
            Console.WriteLine("Server login2");
            if (v != null)
            {
                if (loggedClients.ContainsKey(voluntar.Username))
                    throw new TeledonException("User already logged in.");
                if(voluntar.Password == v.Password)
                    loggedClients[voluntar.Username] = client;
                else
                    throw new TeledonException("Username or password is incorrect");

            }
            else
                throw new TeledonException("Username or password is incorrect");
        }

        public void logout(Voluntar voluntar, ITeledonObserver client)
        {
            throw new NotImplementedException();
        }

        public CazCaritabil[] GetCazuri()
        {
            IEnumerable<CazCaritabil> cazuri = RepoCaz.FindAll();
            Console.WriteLine("getCazuri server");
            return cazuri.ToArray();
        }

        public Donator[] GetAllDonatori()
        {
            IEnumerable<Donator> donatori = RepoDonator.FindAll();
            Console.WriteLine("GetAlldonatori server");
            return donatori.ToArray();
        }

        public void notifyClients(Donatie donatie)
        {
            foreach (String username in loggedClients.Keys)
            {
                Console.WriteLine("Notifying " + username);
                ITeledonObserver client = loggedClients[username];
                Task.Run(() => client.newDonationAdded(donatie));
                Console.WriteLine("Notifed " + username);
            }
        }

        public void addDonation(CazCaritabil caz, Donator donator, int suma)
        {
            Console.WriteLine("Server makeDonation1");
            string nume, prenume, adresa, telefon;
            nume = donator.Nume;
            prenume = donator.Prenume;
            adresa = donator.Adresa;
            telefon = donator.Telefon;
            donator = RepoDonator.FindByNume(nume, prenume);
            //make sure we have the IDs for donator and caz
            if (donator == null)
            {
                RepoDonator.Save(new Donator(adresa, telefon, nume, prenume));
                donator = RepoDonator.FindByNume(nume, prenume);
            }
            Console.WriteLine("Server makeDonation2");
            caz = RepoCaz.findByName(caz.Nume);
            Donatie donatie = new Donatie(caz, donator, suma);
            RepoDonatie.Save(donatie);
            Console.WriteLine("Server makeDonation3");
            caz.SumaAdunata = caz.SumaAdunata + suma;
            updateCaz(caz);
            Console.WriteLine("Server makeDonation4");
            notifyClients(donatie);

        }
    }
}

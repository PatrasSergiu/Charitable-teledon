package teledon.network.rpcprotocol;

import teledon.model.CazCaritabil;
import teledon.model.Donatie;
import teledon.model.Donator;
import teledon.model.Voluntar;
import teledon.network.dto.DTOutils;
import teledon.network.dto.DonatieDTO;
import teledon.network.dto.VoluntarDTO;
import teledon.services.ITeledonObserver;
import teledon.services.ITeledonServices;
import teledon.services.TeledonException;

import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TeledonServicesRpcProxy  implements ITeledonServices {
    private String host;
    private int port;

    private ITeledonObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public TeledonServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }


    private void sendRequest(Request request) throws TeledonException{
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new TeledonException("Error sending object "+e);
        }

    }

    private Response readResponse() throws TeledonException {
        Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initializeConnection() throws TeledonException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){

        Thread nou=new Thread(()->{
            System.out.println(response.type());
            if (response.type()== ResponseType.NEW_DONATIE){
                Donatie donatie = (Donatie) response.data();
                try {
                    System.out.println("ajunge in rpcproxy");
                    client.newDonationAdded(donatie);
                } catch (TeledonException | RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        nou.start();

    }



    private boolean isUpdate(Response response){

        return response.type() == ResponseType.NEW_DONATIE;
    }

    @Override
    public void login(Voluntar voluntar, ITeledonObserver client) throws TeledonException {
        initializeConnection();
        VoluntarDTO voluntarDTO = DTOutils.getDTO(voluntar);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(voluntarDTO).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new TeledonException(err);
        }
    }

    @Override
    public void logout(Voluntar voluntar, ITeledonObserver client) throws TeledonException {

    }



    @Override
    public void newDonation(Donatie donatie) throws TeledonException {
        System.out.println("Proxy in newDonation...");
        DonatieDTO dto = new DonatieDTO(donatie.getDonator(), donatie.getCaz(), donatie.getSuma());
        Request req = new Request.Builder().type(RequestType.NEW_DONATIE).data(dto).build();

        sendRequest(req);

        Response response = readResponse();

        System.out.println("Response newDonation " + response);
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TeledonException(err);
        }
        if(response.type() == ResponseType.OK) {
            System.out.println("succes adaugare donatie");
        }
    }

    @Override
    public Iterable<CazCaritabil> getAllCazuri() throws TeledonException {
        System.out.println("Proxy in getAllCazuri");
        Request req = new Request.Builder().type(RequestType.GET_ALL_CAZURI).build();
        sendRequest(req);

        Response response = readResponse();
        System.out.println("Response getAllCazuri " + response);
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TeledonException(err);
        }
        Iterable<CazCaritabil> fr = (Iterable<CazCaritabil>) response.data();
//        fr.forEach(x->
//        {
//            System.out.println(x.getNume());
//        });
        return fr;
    }
    @Override
    public Iterable<Donator> getAllDonatori() throws TeledonException {
        System.out.println("Proxy in getAllDonatori");
        Request req = new Request.Builder().type(RequestType.GET_ALL_DONATORI).build();
        sendRequest(req);

        Response response = readResponse();
        System.out.println("Response getAllDonatori " + response);
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TeledonException(err);
        }
        Iterable<Donator> fr = (Iterable<Donator>) response.data();
//        fr.forEach(x->
//        {
//            System.out.println(x.getNume());
//        });
        return fr;
    }


    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}

package teledon.network.protobuffprotocol;

import teledon.model.CazCaritabil;
import teledon.model.Donatie;
import teledon.model.Donator;
import teledon.model.Voluntar;
import teledon.services.ITeledonObserver;
import teledon.services.ITeledonServices;
import teledon.services.TeledonException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoTeledonProxy implements ITeledonServices {

    private String host;
    private int port;
    private ITeledonObserver client;
    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<TeledonProtobufs.TeledonResponse> qresponses;
    private volatile boolean finished;

    public ProtoTeledonProxy(String host, int port) {
        this.host = host;
        this.port = port;

        qresponses = new LinkedBlockingQueue<TeledonProtobufs.TeledonResponse>();

    }

    @Override
    public void login(Voluntar voluntar, ITeledonObserver client) throws TeledonException {
        initializeConnection();

        sendRequest(ProtoUtils.createLoginRequest(voluntar));
        TeledonProtobufs.TeledonResponse  response = readResponse();
        if (response.getType()== TeledonProtobufs.TeledonResponse.Type.ERROR){
            String err=ProtoUtils.getError(response);
            closeConnection();
            throw new TeledonException(err);
        }
        else{
            this.client=client;


        }
    }

    @Override
    public void logout(Voluntar voluntar, ITeledonObserver client) throws TeledonException {

        sendRequest(ProtoUtils.createLogoutRequest(voluntar));
        TeledonProtobufs.TeledonResponse response = readResponse();
        closeConnection();

        if (response.getType() == TeledonProtobufs.TeledonResponse.Type.ERROR) {
            String err = ProtoUtils.getError(response);
            throw new TeledonException(err);
        }


    }

    @Override
    public Iterable<Donator> getAllDonatori() throws TeledonException {
        sendRequest(ProtoUtils.createGetAllDonatoriRequest());
        TeledonProtobufs.TeledonResponse response = readResponse();
        if (response.getType() == TeledonProtobufs.TeledonResponse.Type.ERROR) {
            String errorText=ProtoUtils.getError(response);
            throw new TeledonException(errorText);
        }

        List<Donator> donatori = Arrays.asList(ProtoUtils.getAllDonatori(response));

        return donatori;
    }

    @Override
    public void newDonation(Donatie donatie) throws TeledonException {
        sendRequest(ProtoUtils.createNewDonationRequest(donatie));
        TeledonProtobufs.TeledonResponse response = readResponse();
        if (response.getType() ==  TeledonProtobufs.TeledonResponse.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            throw new TeledonException(errorText);
        }
    }

    @Override
    public Iterable<CazCaritabil> getAllCazuri() throws TeledonException {
        sendRequest(ProtoUtils.createGetAllCazuriRequest());
        TeledonProtobufs.TeledonResponse response = readResponse();
        if (response.getType() == TeledonProtobufs.TeledonResponse.Type.ERROR) {
            String errorText=ProtoUtils.getError(response);
            throw new TeledonException(errorText);
        }

        List<CazCaritabil> caz = Arrays.asList(ProtoUtils.getAllCazuri(response));
        return caz;

    }

    private TeledonProtobufs.TeledonResponse readResponse() throws TeledonException {
        TeledonProtobufs.TeledonResponse response = null;
        try {
            response = qresponses.take();
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }
        return response;
    }

    private void sendRequest(TeledonProtobufs.TeledonRequest request) throws TeledonException {
        try {
            request.writeDelimitedTo(output);
            output.flush(); //OBIECTUL AJUNGE PE PARTEA CEALALTA
        } catch (IOException var3) {
            throw new TeledonException("Error sending object " + var3);
        }
    }

    private void closeConnection() {
        this.finished = true;
        try {
            this.input.close();
            this.output.close();
            this.connection.close();
            this.client = null;
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    private void initializeConnection() throws TeledonException{
        try {
            connection=new Socket(host,port);
            output=connection.getOutputStream();
            //output.flush();
            input=connection.getInputStream();
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private boolean isUpdate(TeledonProtobufs.TeledonResponse.Type response){

        switch (response){

            case NEW_DONATIE: return true;

        }
        return false;
    }

    private void handleUpdate(TeledonProtobufs.TeledonResponse response){

        Thread nou=new Thread(()->{
            Donatie message = ProtoUtils.getDonatie(response);
            try {
                System.out.println("ajunge in rpcproxy");
                client.newDonationAdded(message);
            } catch (TeledonException | RemoteException e) {
                e.printStackTrace();
            }

        });
        nou.start();




    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    TeledonProtobufs.TeledonResponse response = TeledonProtobufs.TeledonResponse.parseDelimitedFrom(input);
                    System.out.println("response received "+response);

                    if (isUpdate(response.getType())){
                        handleUpdate(response);
                    }else{
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}

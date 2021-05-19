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
import java.util.List;

public class ProtoTeledonWorker implements Runnable, ITeledonObserver {

    private ITeledonServices server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;
    public ProtoTeledonWorker(ITeledonServices server,Socket connection)
    {
        this.server=server;
        this.connection=connection;
        try{
            output=connection.getOutputStream() ;//new ObjectOutputStream(connection.getOutputStream());
            input=connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TeledonProtobufs.TeledonResponse handleRequest(TeledonProtobufs.TeledonRequest request){
        TeledonProtobufs.TeledonResponse response = null;

        switch (request.getType()) {
            case LOGIN: {
                System.out.println("Login request ...");

                Voluntar angajatOficiu = ProtoUtils.getVoluntar(request);
                try {
                    server.login(angajatOficiu, this);
                    return ProtoUtils.createOkResponse();
                } catch (TeledonException e) {
                    connected = false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case GET_ALL_DONATORI:{
                System.out.println("GetAllDonatori Request ...");
                try {
                    List<Donator> donatori = (List<Donator>) server.getAllDonatori();
                    return  ProtoUtils.createGetAllDonatoriResponse(donatori);

                } catch ( TeledonException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }

            case GET_ALL_CAZURI: {
                System.out.println("GetAllCazuri Request ...");
                try {
                    List<CazCaritabil> cazuri = (List<CazCaritabil>) server.getAllCazuri();
                    return  ProtoUtils.createGetAllCazuriResponse(cazuri);

                } catch ( TeledonException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
        }

        return response;

    }

    private void sendResponse(TeledonProtobufs.TeledonResponse response) throws IOException{
        System.out.println("sending response "+response);
        response.writeDelimitedTo(output);
        output.flush();
    }

    @Override
    public void run() {
        while(connected){
            try {
                //Object request=input.readObject();
                //Response response=handleRequest((Request)request);
                TeledonProtobufs.TeledonRequest request = TeledonProtobufs.TeledonRequest.parseDelimitedFrom(input);
                TeledonProtobufs.TeledonResponse response = handleRequest(request);

                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    @Override
    public void newDonationAdded(Donatie donatie) throws TeledonException, RemoteException {

    }
}

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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDate;

public class TeledonClientRpcReflectionWorker implements ITeledonObserver,Runnable {
    private ITeledonServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public TeledonClientRpcReflectionWorker(ITeledonServices server, Socket connection)
    {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
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

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }
    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output){
            output.writeObject(response);
            output.flush();
        }

    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();





    private Response handleLOGIN(Request request)
    {
        System.out.println("LOGIN REQUEST..."+request.type());
        VoluntarDTO udto=(VoluntarDTO)request.data();
        Voluntar user= DTOutils.getFromDTO(udto);

        try {
            server.login(user, this);
            return okResponse;
        } catch (TeledonException e) {
            connected=false;

            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_ALL_CAZURI(Request request){
        System.out.println("GET_ALL_CAZURI Request ...");
        System.out.println(Thread.currentThread());
        try {
            Iterable<CazCaritabil> cazuri = server.getAllCazuri();
            for (CazCaritabil caz : cazuri) {
                System.out.println(caz.getNume());
            }
            return new Response.Builder().type(ResponseType.GET_ALL_CAZURI).data(cazuri).build();
        } catch (TeledonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleNEW_DONATIE(Request request) {
        System.out.println("NEW_DONATIE handler.. ");
        System.out.println(Thread.currentThread());
        System.out.println(request.data());
        DonatieDTO dto = (DonatieDTO) request.data();
        Donatie donatie = DTOutils.getFromDTO(dto);
        try {
            server.newDonation(donatie);
            return okResponse;
        }
        catch (TeledonException ex) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(ex.getMessage()).build();
        }

    }

    private Response handleGET_ALL_DONATORI(Request request) {
        System.out.println("GET_ALL_DONATORI Request ...");
        System.out.println(Thread.currentThread());
        try {
            Iterable<Donator> donatori = server.getAllDonatori();
            for (Donator donator: donatori) {
                System.out.println(donator.getNume());
            }
            return new Response.Builder().type(ResponseType.GET_ALL_DONATORI).data(donatori).build();
        } catch (TeledonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    @Override
    public void newDonationAdded(Donatie donatie) throws TeledonException {
        System.out.println("Reflection worker new donatie");
        System.out.println(Thread.currentThread());
        Response response = new Response.Builder().type(ResponseType.NEW_DONATIE).data(donatie).build();
        try{

            sendResponse(response);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

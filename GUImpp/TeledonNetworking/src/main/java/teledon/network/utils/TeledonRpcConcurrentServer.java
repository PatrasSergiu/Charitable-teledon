package teledon.network.utils;

import teledon.network.rpcprotocol.TeledonClientRpcReflectionWorker;
import teledon.services.ITeledonServices;

import java.net.Socket;

public class TeledonRpcConcurrentServer extends  AbstractConcurrentServer {
    private ITeledonServices teledonService;
    public TeledonRpcConcurrentServer(int port, ITeledonServices teledonServer) {
        super(port);
        this.teledonService = teledonServer;
        System.out.println("teledon - teledonRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        TeledonClientRpcReflectionWorker worker = new TeledonClientRpcReflectionWorker(teledonService, client);

        Thread tw = new Thread(worker);
        return tw;
    }
    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}

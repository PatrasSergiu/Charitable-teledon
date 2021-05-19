import teledon.network.utils.AbstractServer;
import teledon.network.utils.TeledonRpcConcurrentServer;
import teledon.persistence.repository.*;
import teledon.server.TeledonService;
import teledon.services.ITeledonServices;
import teledon.services.TeledonException;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartProtobuffServer {
    private static int defaultPort=55555;
    public static void main(String[] args) throws TeledonException {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/chatserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        IVoluntarRepo repoVoluntar = new VoluntarDBRepo(serverProps);
        IDonatorRepo repoDonator = new DonatorDBRepository(serverProps);
        ICazRepo repoCaz = new CazDBRepository(serverProps);
        IDonatieRepo repoDonatie = new DonatieDBRepository(serverProps);

        ITeledonServices chatServerImpl = new TeledonService(repoVoluntar, repoCaz, repoDonator, repoDonatie);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new TeledonRpcConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch ( ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}

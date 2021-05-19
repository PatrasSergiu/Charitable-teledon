import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import teledon.network.utils.AbstractServer;
import teledon.network.utils.TeledonRpcConcurrentServer;
import teledon.persistence.repository.*;
import teledon.server.TeledonService;
import teledon.services.ITeledonServices;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartRpcServer {

    private static int defaultPort=55555;
    public static void main(String[] args) {
//        Properties properties=new Properties();
//        try {
//            properties.load(StartRpcServer.class.getResourceAsStream("/teledonServer.properties"));
//            System.out.println("Server properties set. ");
//            properties.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find teledonServer.properties "+e);
//            return;
//        }
//            ICazRepo repoCaz = new CazDBRepository(properties);
//            IDonatorRepo repoDonator = new DonatorDBRepository(properties);
//            IVoluntarRepo repoVoluntar = new VoluntarDBRepo(properties);
//            IDonatieRepo repoDonatie = new DonatieDBRepository(properties);
//
//        ITeledonServices teledonSrvImpl = new TeledonService(repoVoluntar, repoCaz, repoDonator, repoDonatie);
//        int chatServerPort=defaultPort;
//        try {
//            chatServerPort = Integer.parseInt(properties.getProperty("chat.server.port"));
//        }catch (NumberFormatException nef){
//            System.err.println("Wrong  Port Number"+nef.getMessage());
//            System.err.println("Using default port "+defaultPort);
//        }
//        System.out.println("Starting server on port: "+chatServerPort);
//        AbstractServer server = new TeledonRpcConcurrentServer(chatServerPort, teledonSrvImpl);
//
//        try {
//            server.start();
//        } catch (ServerException e) {
//            System.err.println("Error starting the server" + e.getMessage());
//        }finally {
//
//            try {
//                server.stop();
//
//            }catch(ServerException e){
//                System.err.println("Error stopping server "+e.getMessage());
//            }
//        }
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    }

}

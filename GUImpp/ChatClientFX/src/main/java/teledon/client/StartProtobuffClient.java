package teledon.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import teledon.client.gui.Login;
import teledon.client.gui.MainWindow;
import teledon.network.protobuffprotocol.ProtoTeledonProxy;
import teledon.services.ITeledonServices;

import java.io.IOException;
import java.util.Properties;

public class StartProtobuffClient extends Application {
    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/chatclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }

        String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;
        try {
            serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        //adresa la care trebe sa se conectezr


        ITeledonServices server = new ProtoTeledonProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("login.fxml"));
        Parent root=loader.load();


        Login ctrl =
                loader.<Login>getController();
        ctrl.setServer(server);




        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("mainWindow.fxml"));
        Parent croot=cloader.load();


        MainWindow mainCtrl =
                cloader.<MainWindow>getController();

        mainCtrl.setServer(server);



        ctrl.setMainController(mainCtrl);
        ctrl.setParent(croot);
        primaryStage.setTitle("Teledon caritabil");
        primaryStage.setScene(new Scene(root, 294, 357));

        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}


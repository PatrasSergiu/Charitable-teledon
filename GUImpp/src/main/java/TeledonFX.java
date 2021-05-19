import ctrl.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import services.TeledonService;
import teledon.persistence.repository.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TeledonFX extends Application{

    private TeledonService srvTeledon;
    private Properties properties;

    public TeledonFX() {

    }

    public void start(Stage primaryStage) throws Exception {
        try {
            properties = new Properties();
            try {
                properties.load(new FileReader("bd.config"));
            }
            catch(IOException e ){
                System.out.println("Cannot find bd.config " + e);
            }
            ICazRepo repoCaz = new CazDBRepository(properties);
            IDonatorRepo repoDonator = new DonatorDBRepository(properties);
            IVoluntarRepo repoVoluntar = new VoluntarDBRepo(properties);
            IDonatieRepo repoDonatie = new DonatieDBRepository(properties);

            srvTeledon = new TeledonService(repoVoluntar, repoCaz, repoDonator, repoDonatie);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Login loginController = loader.getController();
            loginController.setService(srvTeledon);

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
//            Parent root = loader.load();
//            MainWindow ctrl = loader.getController();
//            ctrl.setService(srvTeledon);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Teledon caritabil");
            loginController.setStage(primaryStage);
            primaryStage.show();
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app "+e);
            alert.showAndWait();
        }
    }




}

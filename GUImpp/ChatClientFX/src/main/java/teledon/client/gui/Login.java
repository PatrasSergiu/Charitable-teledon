package teledon.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import teledon.model.Voluntar;
import teledon.services.ITeledonServices;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Login {

    private ITeledonServices server;
    Stage userPageStage = new Stage();
    Parent mainTeledonParent;
    Voluntar crtVoluntar;
    private MainWindow mainCtrl;


    @FXML
    private Stage logInStage;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;


    public void setServer(ITeledonServices s){
        server=s;
    }

    public void setParent(Parent p){
        mainTeledonParent=p;
    }

    public void setStage(Stage stage) {
        this.logInStage = stage;
    }

    public void setUser(Voluntar user) {
        this.crtVoluntar = user;
    }

    public void setMainController(MainWindow mainController) {
        this.mainCtrl = mainController;
    }

    public static void showWarning(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MPP chat");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }

//    public void openVolunteerPage(Voluntar voluntar) throws Exception {
//        FXMLLoader loader=new FXMLLoader();
//        loader.setLocation(getClass().getResource("/mainWindow.fxml"));
//        Pane root;
//        root = loader.load();
//
//        userPageStage.setScene(new Scene(root));
//        userPageStage.setTitle("Buna ziua " + voluntar.getUsername());
//        logInStage.close();
//        MainWindow cl = loader.getController();
//        cl.setService(srvTeledon);
//        cl.setStage(userPageStage);
//        userPageStage.show();
//
//
//    }



    public void logInMethod(ActionEvent actionEvent) throws Exception {
        String username, password;
        username = usernameTextField.getText();
        password = passwordTextField.getText();
        System.out.println(username + "  " + password);

        if(username.isEmpty() || password.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Log in failed");
            errorAlert.setContentText("Campurile nu au voie sa fie goale.");
            errorAlert.showAndWait();
            usernameTextField.clear();
            passwordTextField.clear();
        }
        try {
            Voluntar voluntar = new Voluntar(username, password);
            server.login(voluntar, mainCtrl);
            crtVoluntar = voluntar;
            usernameTextField.clear();
            passwordTextField.clear();
            Stage stage=new Stage();
            stage.setTitle("Chat Window for " + crtVoluntar.getUsername());
            stage.setScene(new Scene(mainTeledonParent));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    //mainCtrl.logout();
                    System.exit(0);
                }
            });
            stage.show();
            mainCtrl.setLoggedVoluntar(crtVoluntar);
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }
        catch(Exception e) {
            showWarning("Logare esuata", e.getMessage());
            usernameTextField.clear();
            passwordTextField.clear();
        }
    }

    public String hash(String pass)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes(),0,pass.length());
            String z = new BigInteger(1,md.digest()).toString(16);
            System.out.println(z);
            return z;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }



}

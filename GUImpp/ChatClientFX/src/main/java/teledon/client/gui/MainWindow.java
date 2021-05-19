package teledon.client.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import teledon.model.CazCaritabil;
import teledon.model.Donatie;
import teledon.model.Donator;
import teledon.model.Voluntar;
import teledon.services.ITeledonObserver;
import teledon.services.ITeledonServices;
import teledon.services.TeledonException;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainWindow extends UnicastRemoteObject implements Initializable, ITeledonObserver, Serializable {

    public MainWindow() throws RemoteException {

    }
    private ITeledonServices server;
    private Voluntar loggedVoluntar;

    @FXML
    Stage mainPageStage;
    Stage loginStage = new Stage();


    @FXML
    private TableView<CazCaritabil> cazuriTableView;

    @FXML
    private TableView<Donator> donatorTableView;
    @FXML
    private TableColumn<CazCaritabil, String> columnCaz;
    @FXML
    private TableColumn<Donator, String> columnNume;
    @FXML
    private TableColumn<Donator, String> columnTelefon;
    @FXML
    private TableColumn<Donator, String> columnAdresa;
    @FXML
    private TableColumn<CazCaritabil, String> columnSumaAdunata;

    @FXML
    private TextField searchTextField;
    @FXML
    private TextField descriereTextBox;
    @FXML
    TextField sumaTextBox;
    @FXML
    private TextField numeTextBox;
    @FXML
    private TextField prenumeTextBox;
    @FXML
    private TextField telefonTextBox;
    @FXML
    private TextField adresaTextBox;


    ObservableList<CazCaritabil> modelCaz = FXCollections.observableArrayList();
    ObservableList<Donator> modelDonator = FXCollections.observableArrayList();

    public void setLoggedVoluntar(Voluntar voluntar) {
        this.loggedVoluntar = voluntar;

        List<CazCaritabil> list = new ArrayList<>();
        List<Donator> donatorList = new ArrayList<>();
        try {
            list = StreamSupport
                    .stream(server.getAllCazuri().spliterator(), false)
                    .collect(Collectors.toList());

            modelCaz.setAll(list);

            donatorList = StreamSupport
                    .stream(server.getAllDonatori().spliterator(), false)
                    .collect(Collectors.toList());
            System.out.println("pai na cam atat");

            modelDonator.setAll(donatorList);

            initialize();

        }
        catch (TeledonException te) {
            System.out.println("Eroare get all cazuri initial");
        }


    }
    public void setStage(Stage stage) {
        this.mainPageStage = stage;
    }
    public void setServer(ITeledonServices s){
        server=s;
    }


    public static void showWarning(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MPP chat");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }

    @FXML
    public void initialize() {



        System.out.println("initialize");
        columnCaz.setCellValueFactory(new PropertyValueFactory<CazCaritabil, String>("Nume"));
        columnSumaAdunata.setCellValueFactory(new PropertyValueFactory<CazCaritabil, String>("sumaString"));
        cazuriTableView.setItems(modelCaz);

        columnNume.setCellValueFactory(new PropertyValueFactory<Donator, String>("NumeComplet"));
        columnAdresa.setCellValueFactory(new PropertyValueFactory<Donator, String>("Adresa"));
        columnTelefon.setCellValueFactory(new PropertyValueFactory<Donator, String>("Telefon"));
        donatorTableView.setItems(modelDonator);

    }

//    public void setService(TeledonService service) {
//        this.srvTeledon = service;
//
//        cazuriTableView.setItems(modelCaz);
//        columnCaz.setCellValueFactory(new PropertyValueFactory<CazCaritabil, String>("Nume"));
//        columnSumaAdunata.setCellValueFactory(new PropertyValueFactory<CazCaritabil, String>("sumaString"));
//
//        donatorTableView.setItems(modelDonator);
//        columnNume.setCellValueFactory(new PropertyValueFactory<Donator, String>("NumeComplet"));
//        columnAdresa.setCellValueFactory(new PropertyValueFactory<Donator, String>("Adresa"));
//        columnTelefon.setCellValueFactory(new PropertyValueFactory<Donator, String>("Telefon"));
//
//        modelDonator.setAll(srvTeledon.getAllDonatori());
//        modelCaz.setAll(srvTeledon.getAll());
//
//
//        searchTextField.setOnAction(e-> {
//            String search = searchTextField.getText();
//            List<Donator> donatori = StreamSupport
//                    .stream(srvTeledon.getAllDonatori().spliterator(), false)
//                    .collect(Collectors.toList());
//
//            List<Donator> rez = new ArrayList<>();
//            donatori.forEach(u -> {
//                if(u.getNumeComplet().toLowerCase().contains(search.toLowerCase()))
//                    rez.add(u);
//            });
//
//            modelDonator.setAll(rez);
//
//        });
//
//    }
//
//
//
    public void addCaz(ActionEvent actionEvent) {
//        String descriere = descriereTextBox.getText();
//        descriereTextBox.clear();
//        int suma;
//        if (sumaTextBox.getText().isEmpty())
//            suma = 0;
//        else
//            suma = Integer.parseInt(sumaTextBox.getText());
//        sumaTextBox.clear();
//        srvTeledon.addCaz(descriere, suma);
//        modelCaz.setAll(srvTeledon.getAll());
//
   }
//
    public void deleteCaz(ActionEvent actionEvent) {
//        CazCaritabil caz = cazuriTableView.getSelectionModel().getSelectedItem();
//        try {
//            try {
//                srvTeledon.deleteCaz(caz.getId());
//            } catch (Exception exception) {
//
//            }
//
//            Alert succesAlert = new Alert(Alert.AlertType.CONFIRMATION);
//            succesAlert.setHeaderText("Succes!");
//            succesAlert.setContentText("Cazul a fost sters.");
//            succesAlert.showAndWait();
//        }
//            catch(Exception ex){
//                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//                errorAlert.setHeaderText("e bai");
//                errorAlert.setContentText(ex.getMessage());
//                errorAlert.showAndWait();
//            }
//        modelCaz.setAll(srvTeledon.getAll());
        }

    public void addDonator(ActionEvent actionEvent) {
//        String nume,prenume,adresa,telefon;
//        nume = numeTextBox.getText();
//        prenume = prenumeTextBox.getText();
//        adresa = adresaTextBox.getText();
//        telefon = telefonTextBox.getText();
//        srvTeledon.addDonator(nume, prenume, adresa, telefon);
//
//        modelDonator.setAll(srvTeledon.getAllDonatori());
//
     }
//
    public void deleteDonator(ActionEvent actionEvent) {
//        Donator don = donatorTableView.getSelectionModel().getSelectedItem();
//        try {
//            try {
//                srvTeledon.deleteDonator(don.getId());
//            } catch (Exception exception) {
//
//            }
//
//            Alert succesAlert = new Alert(Alert.AlertType.CONFIRMATION);
//            succesAlert.setHeaderText("Succes!");
//            succesAlert.setContentText("Donatorul a fost sters.");
//            succesAlert.showAndWait();
//        }
//        catch(Exception ex){
//            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//            errorAlert.setHeaderText("e bai");
//            errorAlert.setContentText(ex.getMessage());
//            errorAlert.showAndWait();
//        }
//        modelDonator.setAll(srvTeledon.getAllDonatori());
    }
//
    public void addDonatie(ActionEvent actionEvent) {
        CazCaritabil caz = cazuriTableView.getSelectionModel().getSelectedItem();
        if(caz == null) {
            showWarning("e bai", "Trebuie selectat un caz pentru care se doneaza.");
            return;
        }
        String nume, prenume, telefon,adresa;
        int suma = 0;
        nume = numeTextBox.getText();
        prenume = prenumeTextBox.getText();
        telefon = telefonTextBox.getText();
        adresa = adresaTextBox.getText();
        try {
            suma = Integer.parseInt(sumaTextBox.getText());
        }
        catch (Exception e) {
            showWarning("e bai", "Suma trebuie sa fie un numar intreg.");
            return;
        }
        if(suma <= 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("e bai");
            errorAlert.setContentText("Ba nu fi nesimtit");
            errorAlert.showAndWait();
        }

        try {
            Donator donator = new Donator(adresa, telefon, nume, prenume);
            server.newDonation(new Donatie(donator, caz, suma));
        }
        catch (TeledonException ex) {
            showWarning("e bai", ex.getMessage());
        }
        //        Donator donator = srvTeledon.findDonatorByNume(nume, prenume);
//        if(donator == null){
//            srvTeledon.addDonator(nume, prenume, adresa, telefon);
//            donator = srvTeledon.findDonatorByNume(nume, prenume);
//            modelDonator.setAll(srvTeledon.getAllDonatori());
//        }
//
//        srvTeledon.addDonatie(donator, caz, suma);
//        caz.setSumaAdunata(caz.getSumaAdunata() + suma);
//        srvTeledon.updateCaz(caz);
//
//
//        sumaTextBox.clear();
//        modelCaz.setAll(srvTeledon.getAll());
    }
//
    public void logOiutMethod(ActionEvent actionEvent) throws IOException {
        try{
            server.logout(loggedVoluntar, this);
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (TeledonException e){
            showWarning("e bai", e.getMessage());
        }
    }
//
    public void getCurrent(MouseEvent mouseEvent) {
        Donator donator = donatorTableView.getSelectionModel().getSelectedItem();
        if(donator == null)
            return;
        //System.out.println(donator);
        numeTextBox.setText(donator.getNume());
        prenumeTextBox.setText(donator.getPrenume());
        adresaTextBox.setText(donator.getAdresa());
        telefonTextBox.setText(donator.getTelefon());
//
    }
//
    public void fastSearch(KeyEvent keyEvent) {
        String search = searchTextField.getText();
        List<Donator> rez = new ArrayList<>();
        try {
            List<Donator> donatori = StreamSupport
                    .stream(server.getAllDonatori().spliterator(), false)
                    .collect(Collectors.toList());


            donatori.forEach(u -> {
                if (u.getNumeComplet().toLowerCase().contains(search.toLowerCase()))
                    rez.add(u);
            });
        }
        catch (TeledonException ex) {
            System.out.println(ex.getMessage());
        }

        modelDonator.setAll(rez);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @Override
    public void newDonationAdded(Donatie donatie) throws TeledonException {
        System.out.println("Ajunge in client new donation");
        //platform runLater
        try {
            List<CazCaritabil> list = new ArrayList<>();
            List<Donator> donatorList = new ArrayList<>();
            list = StreamSupport
                    .stream(server.getAllCazuri().spliterator(), false)
                    .collect(Collectors.toList());

            modelCaz.setAll(list);

            donatorList = StreamSupport
                    .stream(server.getAllDonatori().spliterator(), false)
                    .collect(Collectors.toList());
            System.out.println("pai na cam atat");

            modelDonator.setAll(donatorList);
        }
        catch(TeledonException ex) {
            showWarning("e bai" ,ex.getMessage());
        }

        Platform.runLater(() -> {
            try {
                List<CazCaritabil> list = new ArrayList<>();
                List<Donator> donatorList = new ArrayList<>();
                list = StreamSupport
                        .stream(server.getAllCazuri().spliterator(), false)
                        .collect(Collectors.toList());

                modelCaz.setAll(list);

                donatorList = StreamSupport
                        .stream(server.getAllDonatori().spliterator(), false)
                        .collect(Collectors.toList());
                System.out.println("pai na cam atat");

                modelDonator.setAll(donatorList);
            }
            catch(TeledonException e) {
                showWarning("e big bai", e.getMessage());
            }
        });

    }
}


package sample.menu;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Controller;
import sample.Functions;
import sample.House;
import sample.SmallCardOfHouse;
import sample.search.SearchController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController extends Controller implements Initializable {

    @FXML
    private Button myaccount;

    @FXML
    private Button addhouse;

    @FXML
    private Button home;

    @FXML
    private Button logout;

    @FXML
    private ListView<House> listview;

    @FXML
    private MenuButton destinationMenu;

    private List<String> Houses;

    private ObservableList<House> houseObservableList;

    @FXML
    public void openBooking(ActionEvent event){
        Stage stage = (Stage) listview.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../booking/booking.fxml"));
        Functions.changeScene(stage,getUser(),loader,"HousinGO");
    }

    @FXML
    void add_House_Event(ActionEvent event) {
        Stage stage = (Stage) addhouse.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../addHouse/addHouse.fxml"));
        Functions.changeScene(stage,getUser(),loader,"Add House");
    }

    @FXML
    public void showHouses() {
        houseObservableList = FXCollections.observableArrayList();
        House domcek= null;
        try{
            BufferedReader br  = new BufferedReader(new FileReader("listOfHouses"));
            String line = br.readLine();
            while (line != null){
                String []udaje = line.split(";");
                if (udaje[1].equals(Functions.dostatDestinaciu())){
                   if (Integer.parseInt(udaje[3])>=Functions.getCapacity()){
                       domcek = new House(udaje[0],udaje[1],udaje[2],Integer.parseInt(udaje[3]),Double.parseDouble(udaje[4]),udaje[5],udaje[6]);
                   }

                }
                line = br.readLine();
                if (domcek!=null){
                    houseObservableList.add(domcek);
                }
                domcek = null;
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        listview.setItems(houseObservableList);
        listview.setCellFactory(houselistView -> new SmallCardOfHouse());
    }

    @FXML
    void home_Event(ActionEvent event) {
        Stage stage = (Stage) home.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../search/search.fxml"));
        Functions.changeScene(stage,getUser(),loader,"Search your holiday");
    }

    @FXML
    void log_out_Event(ActionEvent event) {
        Stage stage = (Stage) logout.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../login/sample.fxml"));
        Functions.changeScene(stage,getUser(),loader,"HousinGO");
    }

    @FXML
    void my_Account_Event(ActionEvent event) {
        Stage stage = (Stage) myaccount.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
        Functions.changeScene(stage,getUser(),loader,"My Account");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(()->{
            myaccount.setText(getUser().getUsername());
            showHouses();
            destinationMenu.setText(Functions.dostatDestinaciu());
        });
    }
}

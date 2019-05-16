package sample.addHouse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.AlertHelper;
import sample.Controller;
import sample.Functions;
import sample.House;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddHouseController extends Controller {

    @FXML
    private Button backToMenu;

    @FXML
    private Button addHouse;

    @FXML
    private TextField houseName;

    @FXML
    private ChoiceBox houseLocality;

    @FXML
    private TextField houseUrl;

    @FXML
    private TextField housePersons;

    @FXML
    private TextField housePrice;

    @FXML
    private TextField info;

    @FXML
    private Button urlofimage;

    @FXML
    void addHouse_buttonEvent(ActionEvent event) {
        Window owner = addHouse.getScene().getWindow();
        if (houseName.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter house name");
            return;
        }
        if (houseLocality.getValue()==null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter house locality");
            return;
        }
        if (houseUrl.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter url of image");
            return;
        }
        if (housePersons.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter number of persons");
            return;
        }
        if (housePrice.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter price of house per 1 night");
            return;
        }
        if (info.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter info about house");
            return;
        }
        String name_of_the_house = houseName.getText().trim();
        String locality_of_house = houseLocality.getValue().toString();
        String url_of_house = houseUrl.getText().trim();
        int persons = Integer.parseInt(housePersons.getText().trim());
        double price = Double.parseDouble(housePrice.getText().trim());
        String info_of_house = info.getText().trim();
        String mail = getUser().getMail();
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter("listOfHouses",true));
            url_of_house = url_of_house.split("HousinGO/")[1];
            String house1 = (name_of_the_house + ";" + locality_of_house + ";" + url_of_house + ";" + persons + ";" +
                    price + ";" + info_of_house + ";" + mail+";"+false);
            bw.write(house1);
            bw.newLine();
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Stage stage = (Stage) addHouse.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
        Functions.changeScene(stage,getUser(),loader,"My Account");
    }


    @FXML
    void back_button_Event(ActionEvent event) {
        Stage stage = (Stage) backToMenu.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
        Functions.changeScene(stage,getUser(),loader,"HousinGO");
    }

    @FXML
    void urlOfImage(ActionEvent event){
        FileChooser saveAs = new FileChooser();
        File outputFile = saveAs.showOpenDialog(null);
        if(outputFile==null) return;
        javafx.scene.image.Image image = new Image(outputFile.toURI().toString());
        houseUrl.setText(image.getUrl());
    }

}

package sample.search;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SearchController extends Controller implements Initializable {


    @FXML
    private Button search;

    @FXML
    private Spinner numberPersons;

    @FXML
    private ChoiceBox destination;

    @FXML
    private DatePicker checkinDate;

    @FXML
    private DatePicker checkoutDate;

    @FXML
    private Button backk;

    private void initSpinner() {
        numberPersons.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30)
        );
    }

    @FXML
    protected void searchHousesEvent(ActionEvent event) {
        Window owner = search.getScene().getWindow();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        if (checkinDate.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter check in date.");
            return;
        }
        if (checkoutDate.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter check out date.");
            return;
        }
        if ((checkoutDate.getValue().isBefore(checkinDate.getValue()))) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter correct dates.");
            return;
        }
        if (checkinDate.getValue().isBefore(localDate)) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter correct dates.");
            return;
        }
        if ((int) numberPersons.getValue() == 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter number of persons.");
            return;
        }
        if (destination.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter destination.");
            return;
        }
        if (checkinDate.getValue().equals(checkoutDate.getValue())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter different dates.");
            return;
        } else {
            Destination des = new Destination();
            des.setCheck_in_date(checkinDate.getValue());
            des.setCheck_out_date(checkoutDate.getValue());
            des.setDestinationName((String) destination.getValue());
            Functions.poslatDestinaciu(des.getDestinationName());
            Functions.setCapacity((int) (numberPersons.getValue()));
            Functions.setCheckInDate(checkinDate.getValue());
            Functions.setCheckOutDate(checkoutDate.getValue());
            System.out.println((int) (numberPersons.getValue()));
            Stage stage = (Stage) search.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../menu/menu.fxml"));
            Functions.changeScene(stage, getUser(), loader, "HousinGO");

        }
    }

    @FXML
    void back_my_Account_Event(ActionEvent event) {
        Stage stage = (Stage) backk.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
        Functions.changeScene(stage, getUser(), loader, "My Account");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSpinner();
        checkinDate.setValue(LocalDate.now());
        destination.setValue("Kosice");
    }
}





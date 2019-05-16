package sample.booking;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookingController extends Controller implements Initializable {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button back;

    @FXML
    private Button book;

    @FXML
    private Label name;

    @FXML
    private Label persons;

    @FXML
    private Label info;

    @FXML
    private Label locations;

    @FXML
    private Label contact;

    @FXML
    private Label price;

    @FXML
    private ImageView obrazokDomu;

    private static House house;

    private static void setHouse() {
        house = Functions.getHouse();
    }

    private int iPersons;
    private String sName, sContact, sLocality, sInfo;
    private double dPrice;

    private void setText() {
        File file = new File(house.getUrl());
        Image image = new Image(file.toURI().toString());
        obrazokDomu.setImage(image);

        iPersons = house.getCapacity();
        sName = house.getHouseName();
        sContact = house.getMail();
        dPrice = house.getPrice();
        sLocality = house.getLocality();
        sInfo = house.getInfo();
        persons.setText(iPersons + " persons");
        name.setText(sName);
        contact.setText("contact: " + sContact);
        price.setText("total: " + calculatePrice(Functions.getCheckInDate(), Functions.getCheckOutDate(), dPrice) + " €");
        locations.setText("locality: " + sLocality);
        name.setText(house.getHouseName());
        info.setText("info: " + sInfo);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            setHouse();
            setText();
        });
    }

    @FXML
    public void backToMenu() {
        Stage stage = (Stage) back.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../menu/menu.fxml"));
        Functions.changeScene(stage, getUser(), loader, "HousinGO");
    }

    @FXML
    public void booking() {

        String fName = firstName.getText().trim();
        String lName = lastName.getText().trim();
        String phone = phoneNumber.getText().trim();
        String userName = username.getText().trim();
        String pass = password.getText().trim();


        Window window = book.getScene().getWindow();

        if (firstName.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your first name");
            return;
        } else if (lastName.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your last name");
            return;
        } else if (phoneNumber.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your phone number");
            return;
        } else if (username.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your username");
            return;
        } else if (password.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your password");
            return;
        } else if (!userName.equals(getUser().getUsername()) || !pass.equals(getUser().getPass())) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Bad login entry");
            return;
        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader("listOfHouses"));
                BufferedWriter bw = new BufferedWriter(new FileWriter("listOfBooking", true));
                BufferedWriter bw2 = new BufferedWriter(new FileWriter("helpList"));
                String line = br.readLine();
                while (line != null) {
                    String[] data = line.split(";");
                    if (data[0].equals(house.getHouseName()) && data[5].equals(house.getInfo()) && iPersons == house.getCapacity()
                    ) {
                        bw.write(fName + ";" + lName + ";" + phone + ";" + Functions.getCheckInDate() + ";" + Functions.getCheckOutDate() + ";" +
                                getUser().getMail() + ";" + sName + ";" + sInfo + ";" + iPersons + ";"
                                + calculatePrice(Functions.getCheckInDate(), Functions.getCheckOutDate(), dPrice));
                        bw.newLine();
                        String lineBack = data[0] + ";" + data[1] + ";" + data[2] + ";" + data[3] + ";" + data[4] + ";" + data[5] + ";" + data[6] +
                                ";" + true;
                        bw2.write(lineBack);
                        bw2.newLine();
                    } else {
                        String lineBack = data[0] + ";" + data[1] + ";" + data[2] + ";" + data[3] + ";" + data[4] + ";" + data[5] + ";" + data[6] +
                                ";" + data[7];
                        bw2.write(lineBack);
                        bw2.newLine();
                    }
                    line = br.readLine();
                }
                br.close();
                bw.close();
                bw2.close();
                Functions.presypUdajov("listOfHouses");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) book.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
            Functions.changeScene(stage, getUser(), loader, "HousinGO");
        }

    }

    public Double calculatePrice(LocalDate checkInDate, LocalDate checkOutDate, Double price) {
        double calculatedPrice = 0;
        String inDate = checkInDate.toString();
        String outDate = checkOutDate.toString();
        String[] arrayOutDate = outDate.split("-");
        String[] arrayinDate = inDate.split("-");

        int outDay = Integer.parseInt(arrayOutDate[2]);
        int inDay = Integer.parseInt(arrayinDate[2]);
        int outMonth = Integer.parseInt(arrayOutDate[1]);
        int inMonth = Integer.parseInt(arrayinDate[1]);
        int count = 0;

        if (outMonth == inMonth) {
            calculatedPrice = (outDay - inDay) * price;
        } else if (outMonth > inMonth) {
            if (inMonth == 4 || inMonth == 6 || inMonth == 9 || inMonth == 11) {
                count = 30 - inDay;
                count += outDay;
                calculatedPrice = count * price;
            } else if (inMonth == 2) {
                count = 28 - inDay;
                count += outDay;
                calculatedPrice = count * price;
            } else {
                count = 31 - inDay;
                count += outDay;
                calculatedPrice = count * price;
            }
        }
        if (vipUser()) {
            calculatedPrice = (calculatedPrice / 100.00) * 90.00;
        }
        return calculatedPrice;
    }

    public boolean vipUser() {
         boolean x = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader("listOfHouses"));
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(";");
                if (data[6].equals(getUser().getMail())) {
                    x = true;
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }
}

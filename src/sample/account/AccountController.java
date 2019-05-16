package sample.account;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Controller;
import sample.Functions;

import javax.swing.text.html.ImageView;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountController extends Controller implements Initializable {

    @FXML
    private Text username_account;

    @FXML
    private Text email_account;

    @FXML
    private Button logout;

    @FXML
    private Button addhouseButton;

    @FXML
    private Button search;

    @FXML
    private ListView listview;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem deleteMyHouse;

    @FXML
    private MenuItem discardBooking;

    @FXML
    private ListView listview1;

    private List<String> Booking;

    private List<String> Houses;

    private BufferedReader br;
    private BufferedWriter bw;

    @FXML
    void add_House_Event(ActionEvent event) {
        Stage stage = (Stage) addhouseButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../addHouse/addHouse.fxml"));
        Functions.changeScene(stage, getUser(), loader, "Add House");
    }

    @FXML
    void asearch_Event(ActionEvent event) {
        Stage stage = (Stage) search.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../search/search.fxml"));
        Functions.changeScene(stage, getUser(), loader, "Search");
    }

    @FXML
    void log_out_Event(ActionEvent event) {
        Stage stage = (Stage) logout.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../login/sample.fxml"));
        Functions.changeScene(stage, getUser(), loader, "HousinGO");
    }

    @FXML
    protected void showMyHouses() {
        Houses = new ArrayList<>();
        String mojDom = "";
        try {
            br = new BufferedReader(new FileReader("listOfHouses"));
            String line = br.readLine();
            while (line != null) {
                String[] udaje = line.split(";");
                if (udaje[6].equals(getUser().getMail())) {
                    mojDom += udaje[0] + " → " + udaje[1] + " → " + udaje[5];
                }
                line = br.readLine();
                if (!mojDom.equals("")) {
                    Houses.add(mojDom);
                }
                mojDom = "";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listview.setItems(FXCollections.observableList(Houses));
    }

    private void showMyBooking() {
        Booking = new ArrayList<>();
        String myBooking = "";
        try {
            br = new BufferedReader(new FileReader("listOfBooking"));
            String line = br.readLine();
            while (line != null) {
                String[] udaje = line.split(";");
                if (udaje[5].equals(getUser().getMail())) {
                    myBooking += udaje[6] + " → " + udaje[3] + " → " + udaje[4] + " → " + udaje[7] + " → " + udaje[9];
                }
                line = br.readLine();
                if (!myBooking.equals("")) {
                    Booking.add(myBooking);
                }
                myBooking = "";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listview1.setItems(FXCollections.observableList(Booking));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            username_account.setText(getUser().getUsername());
            email_account.setText(getUser().getMail());
            showMyHouses();
            checkBookingByDate();
            showMyBooking();
            setupContextMenu();
        });
    }

    protected void setupContextMenu() {
        deleteMyHouse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String dom = (String) listview.getSelectionModel().getSelectedItem();
                dom += " → " + getUser().getMail();
                deleteHouse(dom);
            }
        });

        discardBooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String booking = (String) listview1.getSelectionModel().getSelectedItem();
                deleteBooking(booking);
            }
        });
    }

    private void deleteHouse(String dom) {
        String[] udajeDomu = dom.split("→");
        for (int i = 0; i < 4; i++) {
            udajeDomu[i] = udajeDomu[i].trim();
        }
        try {
            br = new BufferedReader(new FileReader("listOfHouses"));
            bw = new BufferedWriter(new FileWriter("helpList"));
            String line = br.readLine();
            while (line != null) {
                String[] udaje = line.split(";");
                if (udaje[0].equals(udajeDomu[0]) && udaje[1].equals(udajeDomu[1]) && udaje[5].equals(udajeDomu[2])
                        && udaje[6].equals(udajeDomu[3])) {
                    for (int i = 0; i < 7; i++) {
                        udaje[i] = udaje[i].replace(udaje[i], " ");
                    }
                } else {
                    String back = udaje[0] + ";" + udaje[1] + ";" + udaje[2] + ";" + udaje[3] + ";" + udaje[4] + ";"
                            + udaje[5] + ";" + udaje[6];
                    bw.write(back);
                    bw.newLine();
                }
                line = br.readLine();
            }
            br.close();
            bw.close();
            Functions.presypUdajov("listOfHouses");
            Stage stage = (Stage) listview.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
            Functions.changeScene(stage, getUser(), loader, "My Account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteBooking(String booking) {
        String[] data = booking.split("→");
        for (int i = 0; i < 4; i++) {
            data[i] = data[i].trim();
        }
        try {
            br = new BufferedReader(new FileReader("listOfBooking"));
            bw = new BufferedWriter(new FileWriter("helpList"));
            String line = br.readLine();
            while (line != null) {
                String[] udaje = line.split(";");
                if (udaje[6].equals(data[0]) && udaje[3].equals(data[1]) && udaje[4].equals(data[2])
                        && udaje[7].equals(data[3])) {
                    for (int i = 0; i < 10; i++) {
                        udaje[i] = udaje[i].replace(udaje[i], " ");
                    }
                } else {
                    String back = udaje[0] + ";" + udaje[1] + ";" + udaje[2] + ";" + udaje[3] + ";" + udaje[4] + ";"
                            + udaje[5] + ";" + udaje[6] + ";" + udaje[7] + ";" + udaje[8] + ";" + udaje[9];
                    bw.write(back);
                    bw.newLine();
                }
                line = br.readLine();
            }
            bw.close();
            br.close();
            Functions.presypUdajov("listOfBooking");

            br = new BufferedReader(new FileReader("listOfHouses"));
            bw = new BufferedWriter(new FileWriter("helpList"));
            String line2 = br.readLine();
            while (line2 != null) {
                String[] udaje = line2.split(";");
                if (data[0].equals(udaje[0]) && data[3].equals(udaje[5])) {
                    String back = udaje[0] + ";" + udaje[1] + ";" + udaje[2] + ";" + udaje[3] + ";" + udaje[4] + ";"
                            + udaje[5] + ";" + udaje[6] + ";" + false;
                    bw.write(back);
                    bw.newLine();
                } else {
                    String back = udaje[0] + ";" + udaje[1] + ";" + udaje[2] + ";" + udaje[3] + ";" + udaje[4] + ";"
                            + udaje[5] + ";" + udaje[6] + ";" + udaje[7];
                    bw.write(back);
                    bw.newLine();
                }
                line2 = br.readLine();
            }
            br.close();
            bw.close();
            Functions.presypUdajov("listOfHouses");

            Stage stage = (Stage) listview1.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
            Functions.changeScene(stage, getUser(), loader, "My Account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkBookingByDate() {
        String houseForDelete = null;
        LocalDate nowLocalDate = LocalDate.now();
        String nowDate = String.valueOf(nowLocalDate);
        String[] nowDates = nowDate.split("-");
        try {
            br = new BufferedReader(new FileReader("listOfBooking"));
            bw = new BufferedWriter(new FileWriter("helpList"));
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(";");
                String endDate = data[4];
                String[] dates = endDate.split("-");
                if (!(Integer.parseInt(nowDates[0]) > Integer.parseInt(dates[0]) || Integer.parseInt(nowDates[1]) >= Integer.parseInt(dates[1])
                        && Integer.parseInt(nowDates[2]) > Integer.parseInt(dates[2]) || Integer.parseInt(nowDates[1]) > Integer.parseInt(dates[1])
                        && Integer.parseInt(nowDates[2]) < Integer.parseInt(dates[2]))) {
                    bw.write(line);
                    bw.newLine();
                }else {
                    houseForDelete = line;
                }
                line = br.readLine();
            }
            bw.close();
            br.close();

            Functions.presypUdajov("listOfBooking");

            if (houseForDelete != null) {
                String data[] = houseForDelete.split(";");
                BufferedReader br1 = new BufferedReader(new FileReader("listOfHouses"));
                BufferedWriter bw1 = new BufferedWriter(new FileWriter("helpList"));
                String lineHouse = br1.readLine();
                while (lineHouse != null){
                    String datahouse[] = lineHouse.split(";");
                    if (datahouse[0].equals(data[6]) && datahouse[3].equals(data[8]) && datahouse[4].equals(data[9])
                        && datahouse[5].equals(data[7])){
                        String newLine = datahouse[0] + ";" + datahouse[1] +";"+datahouse[2] + ";" + datahouse[3] +";"+datahouse[4] + ";" + datahouse[5] +
                              ";"+  datahouse[6] + ";" + false ;
                        bw1.write(newLine);
                    }else {
                        bw1.write(lineHouse);
                    }
                    bw1.newLine();
                    lineHouse = br1.readLine();
                }
                bw1.close();
                br1.close();
                Functions.presypUdajov("listOfHouses");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        //dorobenie na dome odkedy dokedy bude obsadeny
    }

}

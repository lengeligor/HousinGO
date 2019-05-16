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

import java.io.*;
import java.net.URL;
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
    private Button home;

    @FXML
    private ListView listview;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem deleteMyHouse;

    @FXML
    private ListView listview1;

    private List<String> Houses;

    @FXML
    void add_House_Event(ActionEvent event) {
        Stage stage = (Stage) addhouseButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../addHouse/addHouse.fxml"));
        Functions.changeScene(stage,getUser(),loader,"Add House");
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
    public void showMyHouses() {
        Houses = new ArrayList<>();
        String mojDom = "";
        try{
            BufferedReader br  = new BufferedReader(new FileReader("listOfHouses"));
            String line = br.readLine();
            while (line != null){
                String []udaje = line.split(";");
                if (udaje[6].equals(getUser().getMail())){
                    mojDom += udaje[0] + " # " + udaje[1] + " # " + udaje[5];
                }
                line = br.readLine();
                if (!mojDom.equals("")){
                    Houses.add(mojDom);
                }
                mojDom ="";
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        listview.setItems(FXCollections.observableList(Houses));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()->{
            username_account.setText(getUser().getUsername());
            email_account.setText(getUser().getMail());
            showMyHouses();
            setupContextMenu();
        });
    }

    public void setupContextMenu() {
        deleteMyHouse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String dom = (String) listview.getSelectionModel().getSelectedItem();
                dom += " # " + getUser().getMail();
                deleteHouse(dom);
            }
        });
    }

    public void deleteHouse(String dom){
        String[] udajeDomu = dom.split("#");
        for (int i = 0 ; i < 4 ; i++ ) {
            udajeDomu[i] = udajeDomu[i].trim();
        }
        try{
            BufferedReader br  = new BufferedReader(new FileReader("listOfHouses"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("helpList"));
            String line = br.readLine();
            while (line != null){
                String []udaje = line.split(";");
                if (udaje[0].equals(udajeDomu[0]) && udaje[1].equals(udajeDomu[1]) && udaje[5].equals(udajeDomu[2])
                    && udaje[6].equals(udajeDomu[3])){
                    for (int i=0; i < 7 ; i++){
                        System.out.print(udaje[i]+"\t");
                        udaje[i] = udaje[i].replace(udaje[i]," ");
                    }
                }
                else {
                    String back = udaje[0] + ";" + udaje[1] + ";" + udaje[2] + ";" + udaje[3] + ";" + udaje[4] + ";"
                            + udaje[5] + ";" +udaje[6];
                    bw.write(back);
                    bw.newLine();
                }
                line = br.readLine();
            }
            br.close();
            bw.close();
            presypUdajov();
            Stage stage = (Stage) listview.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
            Functions.changeScene(stage,getUser(),loader,"My Account");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void presypUdajov(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("helpList"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("listOfHouses"));
            String line = br.readLine();
            while(line != null){
                bw.write(line);
                bw.newLine();
                line = br.readLine();
            }
            br.close();
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

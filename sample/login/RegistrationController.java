package sample.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.AlertHelper;
import sample.search.SearchController;
import sample.User;

import java.io.*;

public class RegistrationController {
    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField reenterPass;

    @FXML
    private Button signUp;

    @FXML
    private TextField inputLogin;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Button login;

    private User user;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        Window owner = signUp.getScene().getWindow();
        if (username.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        if (email.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email");
            return;
        }
        if (pass.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }
        if (pass.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please reenter a password");
            return;
        }
        if(!pass.getText().trim().equals(reenterPass.getText().trim())){
           AlertHelper.showAlert(Alert.AlertType.ERROR,owner,"Form Error","Passwords are not equals");
        }
        else {
           try {
               BufferedWriter bw = new BufferedWriter(new FileWriter("RegistrationFile", true));
               String addUser = (username.getText().trim() + ";" + pass.getText().trim() + ";" + email.getText().trim());
               bw.write(addUser);
               bw.newLine();
               bw.close();
               user = new User(username.getText(),pass.getText(),email.getText(),false);
               openNewWindow();
           }catch (IOException e){
               e.printStackTrace();

           }
        }

    }

    @FXML
    protected void loginActionEvent(){
        Window window = login.getScene().getWindow();
        if (inputLogin.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your login");
            return;
        }
        if (inputPassword.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your password");
            return;
        }
        else{
            try{
                BufferedReader br = new BufferedReader(new FileReader("RegistrationFile"));
                String line = br.readLine();
                boolean wrUser = false;
                while(line != null){
                    System.out.println(line);
                    String []udaje = line.split(";");
                    if (udaje[0].equals(inputLogin.getText())&&udaje[1].equals(inputPassword.getText())){
                        wrUser = true;
                        user = new User(inputLogin.getText(),inputPassword.getText(),udaje[2],false);
                        openNewWindow();
                        return;
                    }
                    line = br.readLine();
                }
                if(!wrUser) {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Login Error", "Something went wrong");
                }
                br.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void openNewWindow(){
        try {
            Stage stage = (Stage) login.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../search/search.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            SearchController controller =loader.getController();
            stage.getIcons().add(new javafx.scene.image.Image("sample/img/icon.png"));
            stage.setTitle("Search Holiday");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Nepodarilo sa otvoriť súbor!!!");
        }
    }


}
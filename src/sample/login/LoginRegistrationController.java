package sample.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.AlertHelper;
import sample.Controller;
import sample.Functions;
import sample.User;

import java.io.*;

public class LoginRegistrationController extends Controller {

    @FXML
    private TextField inputLogin;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private PasswordField reenterPass;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private Button login;

    @FXML
    private Button signUp;

    @FXML
    void handleSubmitButtonAction(ActionEvent event) {
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
                User user = new User(username.getText().trim(),pass.getText().trim(),email.getText().trim(),false);
                bw.write(addUser);
                bw.newLine();
                bw.close();
                Stage stage = (Stage) signUp.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
                Functions.changeScene(stage,user,loader,"My account");
            }catch (IOException e){
                e.printStackTrace();

            }
        }

    }

    @FXML
    void loginActionEvent(ActionEvent event) {
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
                    String []udaje = line.split(";");
                    if (udaje[0].equals(inputLogin.getText())&&udaje[1].equals(inputPassword.getText())){
                        wrUser = true;
                        User user = new User(inputLogin.getText(),inputPassword.getText(),udaje[2],false);
                        br.close();
                        Stage stage = (Stage) login.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../account/account.fxml"));
                        Functions.changeScene(stage,user,loader,"My account");
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
}

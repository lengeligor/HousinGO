package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Registration extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login/sample.fxml"));
        primaryStage.setTitle("HousinGO");
        primaryStage.getIcons().add(new javafx.scene.image.Image("sample/img/icon.png"));
        primaryStage.setScene(new Scene(root, 700, 460));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
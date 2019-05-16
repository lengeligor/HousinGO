package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Functions {

    public static String destinaciaLocal="";
    public static int localcapacity;


    public static void changeScene(Stage stage,User user, FXMLLoader fxmlLoader, String title){
        try {
            Parent root = fxmlLoader.load();
            Controller controller = fxmlLoader.getController();
            controller.setUser(user);
            stage.setTitle(title);
            stage.getIcons().add(new javafx.scene.image.Image("sample/img/icon.png"));
            stage.setScene(new Scene(root, 700, 460));
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Nepodarilo sa otvorit scenu!!");
        }
    }

    public static void poslatDestinaciu(String destinacia){
        destinaciaLocal = destinacia;
    }

    public static String dostatDestinaciu(){
        return destinaciaLocal;
    }

    public static void setCapacity(int capacity){
        localcapacity=capacity;
    }

    public static int getCapacity(){
        return localcapacity;
    }

}

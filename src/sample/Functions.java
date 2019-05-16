package sample;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;

public class Functions {

    private static String destinaciaLocal="";
    private static int localcapacity;
    private static LocalDate checkInDateLocal;
    private static LocalDate checkOutDateLocal;

    private static House selectedHouse;

    public static void changeScene(Stage stage,User user, FXMLLoader fxmlLoader, String title){
        try {
            Parent root = fxmlLoader.load();
            Controller controller = fxmlLoader.getController();
            controller.setUser(user);
            stage.setTitle(title);
            stage.getIcons().add(new javafx.scene.image.Image("sample/img/icon.png"));
            stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
            stage.setMinWidth(720);
            stage.setMinHeight(480);
            stage.setResizable(true);
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

    public static void setHouse(House house){selectedHouse = house;}

    public static House getHouse(){return selectedHouse;}

    public static void setCheckInDate(LocalDate checkInDate){checkInDateLocal = checkInDate;}

    public static LocalDate getCheckInDate(){return checkInDateLocal;}

    public static void setCheckOutDate(LocalDate checkOutDate){checkOutDateLocal = checkOutDate;}

    public static LocalDate getCheckOutDate(){return checkOutDateLocal;}

    public static void presypUdajov(String subor){
        try{
            BufferedReader br = new BufferedReader(new FileReader("helpList"));
            BufferedWriter bw = new BufferedWriter(new FileWriter(subor));
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

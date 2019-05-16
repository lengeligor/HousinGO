package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class SmallCardOfHouse extends ListCell<House> {

        @FXML
        private AnchorPane root;

        @FXML
        private ImageView obrazokDomu;

        @FXML
        private Label nazovDomu;

        @FXML
        private Label popisDomu;

        @FXML
        private Label majitel;
        @FXML
        private Label kapacita;

        @FXML
        private Label cena;

        @FXML
        private Label lokalita;


        private FXMLLoader mLLoader;

        @Override
        protected void updateItem(House house, boolean empty) {
            super.updateItem(house, empty);

            if(empty || house == null) {

                setText(null);
                setGraphic(null);

            } else {
                if (mLLoader == null) {
                    mLLoader = new FXMLLoader(getClass().getResource("cardOfHouse.fxml"));
                    mLLoader.setController(this);

                    try {
                        mLLoader.load();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                File file = new File(house.getUrl());
                Image image = new Image(file.toURI().toString());
                obrazokDomu.setImage(image);

                nazovDomu.setText(house.getHouseName());
                popisDomu.setText("Info: "+house.getInfo());
                majitel.setText("Contact: "+house.getMail());
                cena.setText(String.valueOf(house.getPrice())+"€/night");
                kapacita.setText(String.valueOf(house.getCapacity())+" people");
                lokalita.setText("Locality: "+house.getLocality());



                setText(null);
                setGraphic(root);
            }
        }

}

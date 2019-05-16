package sample;

import javafx.scene.control.Menu;
import sample.menu.MenuController;
import sample.search.SearchController;

import java.time.LocalDate;

public class Destination  extends SearchController {

    private String destinationName;
    private LocalDate check_in_date;
    private LocalDate check_out_date;

    public Destination(){}

    public Destination(String destinationName,LocalDate check_in_date,LocalDate check_out_date){
        this.destinationName = destinationName;
        this.check_in_date = check_in_date;
        this.check_out_date =check_out_date;
    }



    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public LocalDate getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(LocalDate check_in_date) {
        this.check_in_date = check_in_date;
    }

    public LocalDate getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(LocalDate check_out_date) {
        this.check_out_date = check_out_date;
    }
}

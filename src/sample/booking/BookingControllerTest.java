package sample.booking;

import org.junit.jupiter.api.Test;
import sample.Functions;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingControllerTest {

    BookingController controller= new BookingController();
    Functions functions;

    @Test
    void calculatePrice() {
        functions = new Functions();
        LocalDate datestart = LocalDate.of(2019, 5, 17);
        LocalDate dateend = LocalDate.of(2019, 5, 20);
        assertEquals(controller.calculatePrice(datestart, dateend, 200.0), 600.0);
    }
}
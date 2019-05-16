package test;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchControllerTest {

    boolean isLess;

    @Test
    void zistimDatumik(){
        LocalDate checkIn = LocalDate.of(2019,5,18);
        LocalDate checkOut = LocalDate.of(2019,5,19);

        if(checkIn.isBefore(checkOut)){
            isLess=true;
        }
        assertEquals(true,isLess);

    }
}
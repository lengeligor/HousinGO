package test;

import org.junit.jupiter.api.Test;
import sample.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    User user;

    @Test
    void dataUser(){
        user = new User("kacicka","123","katka1@spse-po.sk");
        String name = user.getUsername();
        String pass = user.getPass();
        String mail = user.getMail();

        assertEquals("kacicka",name);
        assertEquals("123",pass);
        assertEquals("katka1@spse-po.sk",mail);

    }
}
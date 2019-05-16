package sample;


public class Controller {

    private User user = new User();

    void setUser(User user) {
        this.user = user;
    }

    protected User getUser() {
        return user;
    }



}
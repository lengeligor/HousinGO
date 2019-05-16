package sample;

public class User {

    private String username;
    private String pass;
    private String mail;

    public User(){}

    public User (String username, String pass, String mail){
        this.username = username;
        this.pass = pass;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}

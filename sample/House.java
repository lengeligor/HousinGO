package sample;

public class House {

    private String houseName;
    private String locality;
    private String url;
    private int capacity;
    private double price;
    private String info;
    private String mail;


    public House() {
    }

    public House(String houseName, String locality, String url, int capacity,double price, String info, String mail) {
        this.houseName = houseName;
        this.locality = locality;
        this.url = url;
        this.capacity = capacity;
        this.price = price;
        this.info = info;
        this.mail=mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

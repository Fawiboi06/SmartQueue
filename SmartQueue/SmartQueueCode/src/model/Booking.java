package model;

public class Booking {

    private String date;
    private String username;

    public Booking(String date, String username) {
        this.date = date;
        this.username = username;
    }
    public String getDate() {
        return date;
    }
    public String getUsername() {
        return username;
    }
}

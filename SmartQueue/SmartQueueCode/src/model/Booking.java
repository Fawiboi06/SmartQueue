package model;

public class Booking {

    private String date;
    private String time;
    private String username;

    public Booking(String date, String time, String username) {
        this.date = date;
        this.username = username;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString(){
        return date + "" + time + "" + " - " + username;
    }
}

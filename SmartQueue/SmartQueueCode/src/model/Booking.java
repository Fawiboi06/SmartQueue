package model;

public class Booking {

    private final String date;
    private final String time;
    private final String username;
    private final String fullName;
    private final String phoneNumber;
    private final String email;



    public Booking(String date, String time, String username, String fullName, String phoneNumber, String email) {
        this.date = date;
        this.username = username;
        this.time = time;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public String getFullName() {return fullName;}

    public String getPhoneNumber() {return phoneNumber;}

    public String getEmail() {return email;}

    @Override
    public String toString() {
        return "Name: " + fullName +
                " | Username: " + username +
                " | Date: " + date +
                " | Time: " + time;
    }
}




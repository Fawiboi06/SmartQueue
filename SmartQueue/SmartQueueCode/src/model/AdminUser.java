package model;

public class AdminUser extends User {

    public AdminUser(String username, String password) {
        super(username, password, "admin");
    }
    public void viewAllBookings () {
        System.out.println("Admin viewing all bookings");
    }
}

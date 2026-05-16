package model;

public class AdminUser extends User {

    public AdminUser(String username, String password, String fullName, String phoneNumber, String email) {
        super(username, password, "Admin", fullName, phoneNumber, email);
    }
}
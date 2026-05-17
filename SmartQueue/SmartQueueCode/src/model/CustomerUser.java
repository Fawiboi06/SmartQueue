package model;

public class CustomerUser extends User {

    public CustomerUser(String username, String password, String fullName, String phoneNumber, String email) {
        super(username, password, "Customer", fullName, phoneNumber, email);
    }
}
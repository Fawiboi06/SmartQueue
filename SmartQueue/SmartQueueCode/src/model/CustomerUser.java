package model;

public class CustomerUser extends User {

    public CustomerUser(String username, String password) {
        super(username, password, "Customer");
    }

    public void bookTime() {
        System.out.println("Customer booking a time");
    }
}
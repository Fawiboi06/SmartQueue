package model;

public class User {
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String phoneNumber;
    private String email;

    public User(String username, String password, String role, String fullName, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    public String getRole() {
        return role;
    }

    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase(role);
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}
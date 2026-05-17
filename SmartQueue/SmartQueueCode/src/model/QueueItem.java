package model;
public class QueueItem {
    private final String username;
    private final String fullName;
    private final String phoneNumber;
    private final String email;


    public QueueItem(String username, String fullName, String phoneNumber, String email) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
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
        return fullName + " (" + username + ")";
    }
}

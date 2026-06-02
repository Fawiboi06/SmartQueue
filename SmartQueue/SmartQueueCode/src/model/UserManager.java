package model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();

        // Testkonton tills databas finns
        users.add(new AdminUser("admin", "admin123", "System Admin", "000-000", "admin@smartqueue.se"));
        users.add(new CustomerUser("kund", "123456", "Test Kund", "070-0000000", "kund@test.se"));
    }

    public boolean registerUser(String username, String password, String role,
                                String fullName, String phoneNumber, String email) {

        if (!isValidRegistration(username, password, role, fullName, phoneNumber, email)) {
            return false;
        }

        if (findUser(username) != null) {
            return false;
        }

        if ("Admin".equalsIgnoreCase(role)) {
            users.add(new AdminUser(
                    username.trim(),
                    password,
                    fullName.trim(),
                    phoneNumber.trim(),
                    email.trim()
            ));
        } else {
            users.add(new CustomerUser(
                    username.trim(),
                    password,
                    fullName.trim(),
                    phoneNumber.trim(),
                    email.trim()
            ));
        }

        return true;
    }

    private boolean isValidRegistration(String username, String password, String role,
                                        String fullName, String phoneNumber, String email) {

        if (isBlank(username) || isBlank(password) || isBlank(role)
                || isBlank(fullName) || isBlank(phoneNumber) || isBlank(email)) {
            return false;
        }

        if (username.trim().length() < 3) {
            return false;
        }

        if (password.length() < 6) {
            return false;
        }

        if (!isValidPhone(phoneNumber)) {
            return false;
        }

        if (!isValidEmail(email)) {
            return false;
        }

        return true;
    }

    public boolean updateContactInfo(String username, String phoneNumber, String email) {
        User user = findUser(username);

        if (user == null) {
            return false;
        }

        if (!isValidPhone(phoneNumber)) {
            return false;
        }

        if (!isValidEmail(email)) {
            return false;
        }

        user.setPhoneNumber(phoneNumber.trim());
        user.setEmail(email.trim());

        return true;
    }

    public boolean isValidCustomerBookingInfo(String username, String fullName, String phoneNumber, String email) {
        if (isBlank(username) || isBlank(fullName) || isBlank(phoneNumber) || isBlank(email)) {
            return false;
        }

        if (username.trim().length() < 3) {
            return false;
        }

        if (!isValidPhone(phoneNumber)) {
            return false;
        }

        if (!isValidEmail(email)) {
            return false;
        }

        User existingUser = findUser(username);

        if (existingUser != null && existingUser.isAdmin()) {
            return false;
        }

        return true;
    }

    public boolean isValidPhone(String phoneNumber) {
        if (isBlank(phoneNumber)) {
            return false;
        }

        return phoneNumber.matches("[0-9+\\- ]+");
    }

    public boolean isValidEmail(String email) {
        if (isBlank(email)) {
            return false;
        }

        return email.contains("@");
    }

    public User login(String username, String password) {
        User user = findUser(username);

        if (user != null && user.checkPassword(password)) {
            return user;
        }

        return null;
    }

    public User findUser(String username) {
        if (isBlank(username)) {
            return null;
        }

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username.trim())) {
                return user;
            }
        }

        return null;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    private boolean isBlank(String text) {
        return text == null || text.isBlank();
    }
}
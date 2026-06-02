package model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();

        // Testkonton tills databas finns
        users.add(new AdminUser("admin", "admin", "System Admin", "000-000", "admin@smartqueue.se"));
        users.add(new CustomerUser("kund", "123", "Test Kund", "070-0000000", "kund@test.se"));
    }

    public boolean registerUser(String username, String password, String role,
                                String fullName, String phoneNumber, String email) {

        if (isBlank(username) || isBlank(password) || isBlank(role)
                || isBlank(fullName) || isBlank(phoneNumber) || isBlank(email)) {
            return false;
        }

        if (username.trim().length() < 3) return false;
        if (password.length() < 6) return false;

        if (findUser(username) != null) {
            return false;
        }

        if (!phoneNumber.matches("[0-9+\\- ]+")) {
            return false;
        }

        if (!email.contains("@")) {
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
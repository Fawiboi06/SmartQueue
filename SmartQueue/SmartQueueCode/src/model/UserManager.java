package model;

import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {


    public boolean registerUser(String username, String password, String role,
                                String fullName, String phoneNumber, String email) {


        if (!isValidRegistration(username, password, role,
                fullName, phoneNumber, email)) {
            return false;
        }


        if (findUser(username) != null) {
            return false;
        }


        String sql = "INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, fullName.trim());
            stmt.setString(5, phoneNumber.trim());
            stmt.setString(6, email.trim());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

        if (!isValidPhone(phoneNumber)) {
            return false;
        }

        if (!isValidEmail(email)) {
            return false;
        }

        String sql = "UPDATE customers SET phone = ?, email = ? WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phoneNumber.trim());
            stmt.setString(2, email.trim());
            stmt.setString(3, username);

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean isValidCustomerBookingInfo(String username, String fullName,
                                              String phoneNumber, String email) {

        if (isBlank(username) || isBlank(fullName)
                || isBlank(phoneNumber) || isBlank(email)) {
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

        String sql = "SELECT * FROM customers WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String role = rs.getString("role");
                String fullName = rs.getString("full_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                if ("Admin".equalsIgnoreCase(role)) {
                    return new AdminUser(username, password,
                            fullName, phone, email);

                } else {
                    return new CustomerUser(username, password,
                            fullName, phone, email);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public User findUser(String username) {

        if (isBlank(username)) {
            return null;
        }

        String sql = "SELECT * FROM customers WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String password = rs.getString("password");
                String role = rs.getString("role");
                String fullName = rs.getString("full_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                if ("Admin".equalsIgnoreCase(role)) {
                    return new AdminUser(username, password,
                            fullName, phone, email);

                } else {
                    return new CustomerUser(username, password,
                            fullName, phone, email);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<User> getUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM customers";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String fullName = rs.getString("full_name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                if ("Admin".equalsIgnoreCase(role)) {

                    users.add(new AdminUser(
                            username,
                            password,
                            fullName,
                            phone,
                            email
                    ));

                } else {

                    users.add(new CustomerUser(
                            username,
                            password,
                            fullName,
                            phone,
                            email
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public String getRegistrationError(String username, String password, String role,
                                       String fullName, String phoneNumber, String email) {

        if (isBlank(username)) {
            return "Username must be filled in.";
        }

        if (username.trim().length() < 3) {
            return "Username must be at least 3 characters.";
        }

        if (findUser(username) != null) {
            return "Username is already taken.";
        }

        if (isBlank(password)) {
            return "Password must be filled in.";
        }

        if (password.length() < 6) {
            return "Password must be at least 6 characters.";
        }

        if (isBlank(role)) {
            return "Role must be selected.";
        }

        if (!role.equalsIgnoreCase("Customer") && !role.equalsIgnoreCase("Admin")) {
            return "Role must be Customer or Admin.";
        }

        if (isBlank(fullName)) {
            return "Full name must be filled in.";
        }

        if (isBlank(phoneNumber)) {
            return "Phone number must be filled in.";
        }

        if (!isValidPhone(phoneNumber)) {
            return "Phone number may only contain numbers, +, - and spaces.";
        }

        if (isBlank(email)) {
            return "Email must be filled in.";
        }

        if (!isValidEmail(email)) {
            return "Email must contain @.";
        }

        return null;
    }


    public String getCustomerBookingInfoError(String username, String fullName,
                                              String phoneNumber, String email) {

        if (isBlank(username)) {
            return "Customer username must be filled in.";
        }

        if (username.trim().length() < 3) {
            return "Customer username must be at least 3 characters.";
        }

        if (isBlank(fullName)) {
            return "Customer full name must be filled in.";
        }

        if (isBlank(phoneNumber)) {
            return "Customer phone number must be filled in.";
        }

        if (!isValidPhone(phoneNumber)) {
            return "Customer phone number may only contain numbers, +, - and spaces.";
        }

        if (isBlank(email)) {
            return "Customer email must be filled in.";
        }

        if (!isValidEmail(email)) {
            return "Customer email must contain @.";
        }

        User existingUser = findUser(username);

        if (existingUser != null && existingUser.isAdmin()) {
            return "Admin usernames cannot be used as customer bookings.";
        }

        return null;
    }


    public String getContactInfoError(String phoneNumber, String email) {

        if (isBlank(phoneNumber)) {
            return "Phone number must be filled in.";
        }

        if (!isValidPhone(phoneNumber)) {
            return "Phone number may only contain numbers, +, - and spaces.";
        }

        if (isBlank(email)) {
            return "Email must be filled in.";
        }

        if (!isValidEmail(email)) {
            return "Email must contain @.";
        }

        return null;
    }

    private boolean isBlank(String text) {
        return text == null || text.isBlank();
    }
}
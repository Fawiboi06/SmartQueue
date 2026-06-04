package model;

import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {

    public boolean addBooking(Booking booking) {

        if (!isValidBooking(booking)) {
            return false;
        }

        if (hasBookingAt(booking.getDate(), booking.getTime())) {
            return false;
        }

        String sql = "INSERT INTO bookings(date, time, username, full_name, phone, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, booking.getDate());
            stmt.setString(2, booking.getTime());
            stmt.setString(3, booking.getUsername());
            stmt.setString(4, booking.getFullName());
            stmt.setString(5, booking.getPhoneNumber());
            stmt.setString(6, booking.getEmail());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean hasBookingAt(String date, String time) {
        return getBookingAt(date, time) != null;
    }


    public Booking getBookingAt(String date, String time) {

        String sql = "SELECT * FROM bookings WHERE date = ? AND time = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);
            stmt.setString(2, time);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Booking(
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Booking> getBookingsForDate(String date) {

        List<Booking> list = new ArrayList<>();

        String sql = "SELECT * FROM bookings WHERE date = ? ORDER BY time";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                list.add(new Booking(
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public List<Booking> getBookingsForUser(String username) {

        List<Booking> list = new ArrayList<>();

        String sql = "SELECT * FROM bookings WHERE username = ? ORDER BY date, time";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                list.add(new Booking(
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public List<Booking> getBookings() {

        List<Booking> list = new ArrayList<>();

        String sql = "SELECT * FROM bookings ORDER BY date, time";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                list.add(new Booking(
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public Booking getBookingByNumberForUser(int number, String username) {

        List<Booking> userBookings = getBookingsForUser(username);

        if (number < 1 || number > userBookings.size()) {
            return null;
        }

        return userBookings.get(number - 1);
    }


    public Booking getBookingByNumberForAdmin(int number) {

        List<Booking> allBookings = getBookings();

        if (number < 1 || number > allBookings.size()) {
            return null;
        }

        return allBookings.get(number - 1);
    }


    public boolean removeBooking(Booking booking) {

        if (booking == null) {
            return false;
        }

        String sql = "DELETE FROM bookings WHERE date = ? AND time = ? AND username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, booking.getDate());
            stmt.setString(2, booking.getTime());
            stmt.setString(3, booking.getUsername());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean isEmpty() {
        return getBookings().isEmpty();
    }


    private boolean isValidBooking(Booking booking) {

        if (booking == null) {
            return false;
        }

        if (isBlank(booking.getDate()) ||
                isBlank(booking.getTime()) ||
                isBlank(booking.getUsername()) ||
                isBlank(booking.getFullName())) {
            return false;
        }

        return booking.getUsername().trim().length() >= 2;
    }


    private boolean isBlank(String text) {
        return text == null || text.isBlank();
    }
}


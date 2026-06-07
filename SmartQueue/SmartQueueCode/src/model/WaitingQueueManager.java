package model;

import database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WaitingQueueManager {

    public boolean addToWaitingQueue(String date, String time, User user) {

        if (user == null) return false;

        return addToWaitingQueue(
                date,
                time,
                user.getUsername(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getEmail()
        );
    }

    public boolean addToWaitingQueue(String date, String time,
                                     String username, String fullName,
                                     String phoneNumber, String email) {

        if (isBlank(date) || isBlank(time) || isBlank(username)
                || isBlank(fullName) || isBlank(phoneNumber) || isBlank(email)) {
            return false;
        }

        if (isUserInQueue(date, time, username)) {
            return false;
        }

        String sql = """
                INSERT INTO waiting_queue(date, time, username, full_name, phone, email, position)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int position = getQueueSize(date, time) + 1;

            stmt.setString(1, date);
            stmt.setString(2, time);
            stmt.setString(3, username);
            stmt.setString(4, fullName);
            stmt.setString(5, phoneNumber);
            stmt.setString(6, email);
            stmt.setInt(7, position);

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<QueueItem> getQueueForTime(String date, String time) {

        List<QueueItem> list = new ArrayList<>();

        String sql = """
                SELECT *
                FROM waiting_queue
                WHERE date = ? AND time = ?
                ORDER BY position
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);
            stmt.setString(2, time);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new QueueItem(
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

    public int getQueuePosition(String date, String time, String username) {

        String sql = """
                SELECT position
                FROM waiting_queue
                WHERE date = ? AND time = ? AND username = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);
            stmt.setString(2, time);
            stmt.setString(3, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("position");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean isUserInQueue(String date, String time, String username) {
        return getQueuePosition(date, time, username) != -1;
    }

    public QueueItem getNextInQueue(String date, String time) {

        String selectSql = """
                SELECT *
                FROM waiting_queue
                WHERE date = ? AND time = ?
                ORDER BY position
                LIMIT 1
                """;

        String deleteSql = """
                DELETE FROM waiting_queue
                WHERE date = ? AND time = ? AND username = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSql)) {

            stmt.setString(1, date);
            stmt.setString(2, time);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                QueueItem item = new QueueItem(
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );

                try (PreparedStatement del = conn.prepareStatement(deleteSql)) {
                    del.setString(1, date);
                    del.setString(2, time);
                    del.setString(3, item.getUsername());
                    del.executeUpdate();
                }

                updatePositions(date, time);

                return item;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void updatePositions(String date, String time) {

        String sql = """
                SELECT username
                FROM waiting_queue
                WHERE date = ? AND time = ?
                ORDER BY position
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);
            stmt.setString(2, time);

            ResultSet rs = stmt.executeQuery();

            int pos = 1;

            while (rs.next()) {

                try (PreparedStatement update = conn.prepareStatement("""
                        UPDATE waiting_queue
                        SET position = ?
                        WHERE date = ? AND time = ? AND username = ?
                        """)) {

                    update.setInt(1, pos);
                    update.setString(2, date);
                    update.setString(3, time);
                    update.setString(4, rs.getString("username"));

                    update.executeUpdate();
                }

                pos++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getQueueSize(String date, String time) {

        String sql = """
                SELECT COUNT(*)
                FROM waiting_queue
                WHERE date = ? AND time = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date);
            stmt.setString(2, time);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
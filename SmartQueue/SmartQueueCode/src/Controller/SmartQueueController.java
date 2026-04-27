package controller;

import View.DayBookingView;
import View.GUImainBody;
import View.LoginView;
import model.Booking;
import model.BookingManager;
import java.time.YearMonth;

import javax.swing.*;

public class SmartQueueController {

    private LoginView loginView;
    private GUImainBody mainView;
    private DayBookingView dayBookingView;
    private String selectedDate;

    private final BookingManager bookingManager;
    private final YearMonth currentMonth = YearMonth.now();
    private String username;


    public SmartQueueController() {
        bookingManager = new BookingManager();
        showLoginView();
    }

    private void showLoginView() {
        loginView = new LoginView();
        loginView.getLoginButton().addActionListener(e -> login());
        loginView.setVisible(true);
    }

    private void login() {
        String enteredUsername = loginView.getUsername();
        String enteredPassword = loginView.getPassword();

        if (enteredUsername.isBlank() || enteredPassword.isBlank()) {
            JOptionPane.showMessageDialog(loginView, "Please enter username and password.");
            return;
        }

        this.username = enteredUsername;

        JOptionPane.showMessageDialog(loginView, "Login successful");
        loginView.dispose();
        showMainView();
    }

    private void showMainView() {
        mainView = new GUImainBody();

        for(JButton dayButton : mainView.getDayButtons()){
            dayButton.addActionListener(e -> {
                JButton source = (JButton) e.getSource();
                int selectedDay = Integer.parseInt(source.getText());
                openDayBookingView(selectedDay);
            });
        }

        mainView.getCloseButton().addActionListener(e -> System.exit(0));
        mainView.getBackButton().addActionListener(e -> {
            mainView.dispose();
            showLoginView();
        });

        mainView.setVisible(true);
    }

    private void openDayBookingView(int day) {
        selectedDate = currentMonth + "-" + String.format("%02d", day);
        mainView.setVisible(false);
        dayBookingView = new DayBookingView(day);

        dayBookingView.getBackButton().addActionListener(e -> {
            dayBookingView.dispose();
            mainView.setVisible(true);
        });

        dayBookingView.getBokaButton().addActionListener(e -> {
            String date = currentMonth + "-" + String.format("%02d", day);
            String time = dayBookingView.getSelectedTime();
            addBooking(date,time);
            JOptionPane.showMessageDialog(dayBookingView, "Booking created:" + date + "at" + time);
            dayBookingView.resetTime();
        });

        dayBookingView.getSeeMoreButton().addActionListener(e -> showBookings());
        dayBookingView.setVisible(true);
    }

    private void addBooking(String date,String time) {
        if (time == null){
            JOptionPane.showMessageDialog(dayBookingView,"Please select a time.");
            return;
        }

        for(Booking b:bookingManager.getBookings()) {
            if (b.getDate().equals(date) && b.getTime().equals(time)) {
                JOptionPane.showMessageDialog(dayBookingView, "This time is already booked. Please choose another.");
                return;
            }
        }

        Booking booking = new Booking(date, time, username);
        bookingManager.addBooking(booking);
        System.out.println("Booking added for " + date + "at" + time);
    }

    private void showBookings() {
        if (bookingManager.isEmpty()) {
            JOptionPane.showMessageDialog(dayBookingView, "No bookings available yet.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        boolean found = false;

        for(Booking booking : bookingManager.getBookings()) {
            if (booking.getDate().equals(selectedDate)) {

                found = true;

                builder.append(booking.getDate())
                        .append("| Time: ")
                        .append(booking.getTime())
                        .append(" |Name: ")
                        .append(booking.getUsername())
                        .append("\n");
            }
        }

        if(!found) {
            JOptionPane.showMessageDialog(dayBookingView, "No bookings for selected day.");
            return;
        }

        JOptionPane.showMessageDialog(dayBookingView, builder.toString(), "Bokningar",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

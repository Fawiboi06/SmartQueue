package Controller;

import View.DayBookingView;
import View.GUImainBody;
import View.LoginView;
import model.Booking;
import model.BookingManager;

import javax.swing.*;
import java.util.ArrayList;

public class SmartQueueController {

    private LoginView loginView;
    private GUImainBody mainView;
    private DayBookingView dayBookingView;

    private final BookingManager bookingManager;
    private String username;
    private String time;



    public SmartQueueController() {
        bookingManager = new BookingManager();
        showLoginView();
    }

    private void showLoginView(){
        loginView = new LoginView();
        loginView.getLoginButton().addActionListener(e -> login());
        loginView.setVisible(true);
    }
    private void login() {
        String enteredUsername = loginView.getUsername();
        String enteredPassword = loginView.getPassword();

        if(enteredUsername.isBlank() || enteredPassword.isBlank()) {
            JOptionPane.showMessageDialog(loginView, "Fyll i användarnamn och lösenord.");
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
        mainView.setVisible(false);
        dayBookingView = new DayBookingView(day);

        dayBookingView.getBackButton().addActionListener(e -> {
            dayBookingView.dispose();
            mainView.setVisible(true);
        });
        dayBookingView.getBokaButton().addActionListener(e -> {
            String date="2026-04-" + String.format("%02d", day);
            String time=dayBookingView.getSelectedTime();
            addBooking(date,time);
            JOptionPane.showMessageDialog(dayBookingView, "Bokning skapad.");
        });
        dayBookingView.getSeeMoreButton().addActionListener(e -> showBookings());
        dayBookingView.setVisible(true);
    }

    private void addBooking(String date,String time) {
        Booking booking = new Booking(date, time, username);
        bookingManager.addBooking(booking);
        System.out.println("Booking added for " + date+ "at" + time);
    }

    private void showBookings() {
        if(bookingManager.isEmpty()) {
            JOptionPane.showMessageDialog(dayBookingView, "Inga bokningar finns ännu.");
            return;
        }
        String selectedDate= "2026-04-02";

        StringBuilder builder = new StringBuilder();
        for(Booking booking : bookingManager.getBookings()) {
            if (booking.getDate().equals(selectedDate)) {
                
                builder.append(booking.getDate())
                        .append(" - ")
                        .append(booking.getTime())
                        .append(" - ")
                        .append(booking.getUsername())
                        .append("\n");
            }
        }
        JOptionPane.showMessageDialog(dayBookingView, builder.toString(), "Bokningar",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

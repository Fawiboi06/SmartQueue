package controller;

import View.DayBookingView;
import View.GUImainBody;
import View.LoginView;
import model.Booking;
import model.BookingManager;
import java.time.YearMonth;
import model.QueueManager;
import model.QueueItem;

import javax.swing.*;

public class SmartQueueController {

    private LoginView loginView;
    private GUImainBody mainView;
    private DayBookingView dayBookingView;
    private String selectedDate;

    private final BookingManager bookingManager;
    private final YearMonth currentMonth = YearMonth.now();
    private final QueueManager queueManager;
    private String username;


    public SmartQueueController() {
        bookingManager = new BookingManager();
        queueManager = new QueueManager();
        showLoginView();
    }

    private void showLoginView() {
        loginView = new LoginView();
        loginView.getLoginButton().addActionListener(e -> login());
        loginView.getRegisterButton().addActionListener(e -> register());
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

    private void register() {
        String enteredUsername = loginView.getUsername();
        String enteredPassword = loginView.getPassword();
        String selectedRole = loginView.getSelectedRole();

        if (enteredUsername.isBlank() || enteredPassword.isBlank()) {
            JOptionPane.showMessageDialog(loginView, "Please enter username and password.");
            return;
        }

        JOptionPane.showMessageDialog(loginView,
                "Registered as " + selectedRole + ": " + enteredUsername);
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

        mainView.getAddQueueButton().addActionListener(e -> addToQueue());
        mainView.getCompleteQueueButton().addActionListener(e -> completeQueueItem());

        mainView.getCloseButton().addActionListener(e -> System.exit(0));
        mainView.getBackButton().addActionListener(e -> {
            mainView.dispose();
            showLoginView();
        });

        mainView.setVisible(true);
        mainView.getViewBookingButton().addActionListener(e -> updateMainBookingList());
        mainView.getDeleteBookingButton().addActionListener(e -> deleteBooking());
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

            boolean created = addBooking(date, time);

            if (created) {
                JOptionPane.showMessageDialog(dayBookingView, "Booking created: " + date + " at " + time);
                updateMainBookingList();
                dayBookingView.resetTime();
            }
        });

        dayBookingView.getSeeMoreButton().addActionListener(e -> showBookings());
        dayBookingView.setVisible(true);
    }

    private boolean addBooking(String date,String time) {
        if (username == null || username.isBlank()) {
            JOptionPane.showMessageDialog(dayBookingView, "Username is missing.");
            return false;
        }

        if (date == null || date.isBlank()) {
            JOptionPane.showMessageDialog(dayBookingView, "Date is missing.");
            return false;
        }

        if (time == null){
            JOptionPane.showMessageDialog(dayBookingView,"Please select a time.");
            return false;
        }

        Booking booking = new Booking(date, time, username);
        boolean created = bookingManager.addBooking(booking);

        if(!created) {
            JOptionPane.showMessageDialog(dayBookingView, "This time is already booked or the booking is invalid");
            return false;
        }

        System.out.println("Booking added for " + date + " at " + time);
        return true;
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

    private void updateMainBookingList() {
        if (bookingManager.isEmpty()) {
            mainView.updateBookingList("No bookings yet.");
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (Booking booking : bookingManager.getBookings()) {
            builder.append("Name: ")
                    .append(booking.getUsername())
                    .append(" | Date: ")
                    .append(booking.getDate())
                    .append(" | Time: ")
                    .append(booking.getTime())
                    .append("\n");
        }

        mainView.updateBookingList(builder.toString());
    }

    private void deleteBooking() {
        if (bookingManager.isEmpty()) {
            JOptionPane.showMessageDialog(mainView, "No bookings to delete.");
            return;
        }

        Booking bookingToDelete = bookingManager.getBookings().get(0);

        boolean deleted = bookingManager.removeBooking(bookingToDelete);

        if (deleted) {
            JOptionPane.showMessageDialog(mainView, "Booking deleted.");
            updateMainBookingList();
        } else {
            JOptionPane.showMessageDialog(mainView, "Could not delete booking.");
        }
    }

    private void addToQueue() {
        String name = mainView.getQueueName();

        boolean added = queueManager.addCustomer(name);

        if (!added) {
            JOptionPane.showMessageDialog(mainView, "Please enter a customer name.");
            return;
        }

        mainView.clearQueueName();
        updateQueueView();

        JOptionPane.showMessageDialog(mainView, "Customer added to queue.");
    }

    private void completeQueueItem(){
        QueueItem completedCustomer = queueManager.completeNextCustomer();

        if(completedCustomer == null){
            JOptionPane.showMessageDialog(mainView, "Queue is already empty.");
            return;
        }

        updateQueueView();
        JOptionPane.showMessageDialog(mainView, "Completed customer: " + completedCustomer.getCustomerName());
    }

    private void updateQueueView(){
        mainView.updateQueueArea(queueManager.getQueueInfo());
    }
}

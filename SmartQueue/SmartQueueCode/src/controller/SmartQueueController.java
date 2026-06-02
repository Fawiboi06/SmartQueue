package controller;

import View.DayBookingView;
import View.GUImainBody;
import View.LoginView;
import model.Booking;
import model.BookingManager;
import model.QueueItem;
import model.User;
import model.UserManager;
import model.WaitingQueueManager;
import View.RegisterView;

import javax.swing.*;
import java.time.YearMonth;
import java.util.List;

public class SmartQueueController {

    private RegisterView registerView;
    private LoginView loginView;
    private GUImainBody mainView;
    private DayBookingView dayBookingView;

    private final BookingManager bookingManager;
    private final UserManager userManager;
    private final WaitingQueueManager waitingQueueManager;

    private User loggedInUser;
    private YearMonth currentMonth = YearMonth.now();
    private String selectedDate;
    private final YearMonth minimumMonth = YearMonth.now();

    public SmartQueueController() {
        bookingManager = new BookingManager();
        userManager = new UserManager();
        waitingQueueManager = new WaitingQueueManager();

        showLoginView();
    }

    private void showLoginView() {
        loginView = new LoginView();

        loginView.getLoginButton().addActionListener(e -> login());
        loginView.getRegisterButton().addActionListener(e -> register());

        loginView.setVisible(true);
    }

    private void login() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        loggedInUser = userManager.login(username, password);

        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(loginView, "Wrong username or password.");
            return;
        }

        JOptionPane.showMessageDialog(loginView, "Login successful as " + loggedInUser.getRole());

        loginView.dispose();
        showMainView();
    }

    private void register() {
        registerView = new RegisterView(loginView);

        registerView.getRegisterButton().addActionListener(e -> createAccount());
        registerView.getCancelButton().addActionListener(e -> registerView.dispose());

        registerView.setVisible(true);
    }

    private void showMainView() {
        mainView = new GUImainBody(loggedInUser.getUsername(), loggedInUser.getRole());

        mainView.setAdminMode(loggedInUser.isAdmin());

        mainView.getViewBookingButton().addActionListener(e -> updateMainBookingList());
        mainView.getDeleteBookingButton().addActionListener(e -> deleteBooking());
        mainView.getShowPersonInfoButton().addActionListener(e -> showPersonInfo());
        mainView.getShowQueuesButton().addActionListener(e -> showWaitingQueues());

        mainView.getBackButton().addActionListener(e -> {
            mainView.dispose();
            loggedInUser = null;
            showLoginView();
        });

        mainView.getCloseButton().addActionListener(e -> System.exit(0));

        mainView.getPreviousButton().addActionListener(e -> {
            YearMonth previousMonth = currentMonth.minusMonths(1);

            if (previousMonth.isBefore(minimumMonth)) {
                JOptionPane.showMessageDialog(mainView, "You cannot go back to previous months.");
                return;
            }

            currentMonth = previousMonth;
            refreshCalendar();
        });

        mainView.getNextButton().addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            refreshCalendar();
        });

        refreshCalendar();

        mainView.setVisible(true);
    }

    private void refreshCalendar() {
        mainView.updateMonth(currentMonth);
        addDayButtonListeners();

        mainView.getPreviousButton().setEnabled(!currentMonth.equals(minimumMonth));
    }

    private void addDayButtonListeners() {
        for (JButton dayButton : mainView.getDayButtons()) {
            dayButton.addActionListener(e -> {
                JButton source = (JButton) e.getSource();
                int selectedDay = Integer.parseInt(source.getText());
                openDayBookingView(selectedDay);
            });
        }
    }

    private void openDayBookingView(int day) {
        selectedDate = currentMonth + "-" + String.format("%02d", day);

        mainView.setVisible(false);

        dayBookingView = new DayBookingView(day, currentMonth);
        dayBookingView.updateTimes(bookingManager.getBookingsForDate(selectedDate));

        dayBookingView.getBookButton().addActionListener(e -> bookOrJoinQueue());

        dayBookingView.getShowBookingsButton().addActionListener(e -> showBookingsForSelectedDay());

        dayBookingView.getShowQueueButton().addActionListener(e -> showQueueForSelectedTime());

        dayBookingView.getBackButton().addActionListener(e -> {
            dayBookingView.dispose();
            mainView.setVisible(true);
            updateMainBookingList();
        });

        dayBookingView.setVisible(true);
    }

    private void bookOrJoinQueue() {
        String time = dayBookingView.getSelectedTime();

        if (time == null || time.isBlank()) {
            JOptionPane.showMessageDialog(dayBookingView, "Please select a time.");
            return;
        }

        Booking existingBooking = bookingManager.getBookingAt(selectedDate, time);

        if (existingBooking == null) {
            Booking newBooking = new Booking(
                    selectedDate,
                    time,
                    loggedInUser.getUsername(),
                    loggedInUser.getFullName(),
                    loggedInUser.getPhoneNumber(),
                    loggedInUser.getEmail()
            );

            boolean created = bookingManager.addBooking(newBooking);

            if (!created) {
                JOptionPane.showMessageDialog(dayBookingView, "Could not create booking.");
                return;
            }

            JOptionPane.showMessageDialog(dayBookingView, "Booking created: " + selectedDate + " at " + time);
        } else {
            if (existingBooking.getUsername().equalsIgnoreCase(loggedInUser.getUsername())) {
                JOptionPane.showMessageDialog(dayBookingView, "You already have this booking.");
                return;
            }

            boolean added = waitingQueueManager.addToWaitingQueue(selectedDate, time, loggedInUser);

            if (!added) {
                int position = waitingQueueManager.getQueuePosition(selectedDate, time, loggedInUser.getUsername());

                if (position != -1) {
                    JOptionPane.showMessageDialog(dayBookingView, "You are already in queue. Your position is: " + position);
                } else {
                    JOptionPane.showMessageDialog(dayBookingView, "Could not add you to the queue.");
                }

                return;
            }

            int position = waitingQueueManager.getQueuePosition(selectedDate, time, loggedInUser.getUsername());

            JOptionPane.showMessageDialog(
                    dayBookingView,
                    "This time is already booked.\nYou have been added to the waiting queue.\nYour position is: " + position
            );
        }

        dayBookingView.updateTimes(bookingManager.getBookingsForDate(selectedDate));
        updateMainBookingList();
    }

    private void showBookingsForSelectedDay() {
        List<Booking> bookings = bookingManager.getBookingsForDate(selectedDate);

        if (bookings.isEmpty()) {
            JOptionPane.showMessageDialog(dayBookingView, "No bookings for selected day.");
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (Booking booking : bookings) {
            if (!loggedInUser.isAdmin() && !booking.getUsername().equalsIgnoreCase(loggedInUser.getUsername())) {
                continue;
            }

            builder.append(booking.getDate())
                    .append(" | Time: ")
                    .append(booking.getTime())
                    .append(" | Name: ")
                    .append(booking.getFullName());

            if (loggedInUser.isAdmin()) {
                builder.append(" | Username: ")
                        .append(booking.getUsername())
                        .append(" | Phone: ")
                        .append(booking.getPhoneNumber())
                        .append(" | Email: ")
                        .append(booking.getEmail());
            }

            builder.append("\n");
        }

        if (builder.length() == 0) {
            JOptionPane.showMessageDialog(dayBookingView, "No bookings to show for your user.");
            return;
        }

        JOptionPane.showMessageDialog(dayBookingView, builder.toString(), "Bookings", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showQueueForSelectedTime() {
        String time = dayBookingView.getSelectedTime();

        String info = waitingQueueManager.getQueueInfoForTime(selectedDate, time, loggedInUser.isAdmin());

        JOptionPane.showMessageDialog(dayBookingView, info, "Waiting queue", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateMainBookingList() {
        if (bookingManager.isEmpty()) {
            mainView.updateBookingList("No bookings yet.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        int index = 1;

        if (loggedInUser.isAdmin()) {
            for (Booking booking : bookingManager.getBookings()) {
                builder.append(index)
                        .append(". ")
                        .append(booking.getDate())
                        .append(" | ")
                        .append(booking.getTime())
                        .append(" | ")
                        .append(booking.getFullName())
                        .append(" (")
                        .append(booking.getUsername())
                        .append(")")
                        .append(" | Phone: ")
                        .append(booking.getPhoneNumber())
                        .append(" | Email: ")
                        .append(booking.getEmail())
                        .append("\n");

                index++;
            }
        } else {
            for (Booking booking : bookingManager.getBookingsForUser(loggedInUser.getUsername())) {
                builder.append(index)
                        .append(". ")
                        .append(booking.getDate())
                        .append(" | ")
                        .append(booking.getTime())
                        .append(" | ")
                        .append(booking.getFullName())
                        .append("\n");

                index++;
            }

            builder.append("\nYour waiting queues:\n");
            builder.append(waitingQueueManager.getUserQueueInfo(loggedInUser.getUsername()));
        }

        if (builder.length() == 0) {
            mainView.updateBookingList("No bookings to show.");
            return;
        }

        mainView.updateBookingList(builder.toString());
    }

    private void deleteBooking() {
        if (bookingManager.isEmpty()) {
            JOptionPane.showMessageDialog(mainView, "No bookings to delete.");
            return;
        }

        String message;

        if (loggedInUser.isAdmin()) {
            message = "Enter booking number to delete:";
        } else {
            message = "Enter your booking number to delete:";
        }

        String input = JOptionPane.showInputDialog(mainView, message);

        if (input == null || input.isBlank()) {
            return;
        }

        try {
            int number = Integer.parseInt(input);

            Booking bookingToDelete;

            if (loggedInUser.isAdmin()) {
                bookingToDelete = bookingManager.getBookingByNumberForAdmin(number);
            } else {
                bookingToDelete = bookingManager.getBookingByNumberForUser(number, loggedInUser.getUsername());
            }

            if (bookingToDelete == null) {
                JOptionPane.showMessageDialog(mainView, "Invalid booking number.");
                return;
            }

            boolean deleted = bookingManager.removeBooking(bookingToDelete);

            if (!deleted) {
                JOptionPane.showMessageDialog(mainView, "Could not delete booking.");
                return;
            }

            promoteNextCustomerIfPossible(bookingToDelete);

            JOptionPane.showMessageDialog(mainView, "Booking deleted.");
            updateMainBookingList();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainView, "Please enter a valid number.");
        }
    }

    private void promoteNextCustomerIfPossible(Booking deletedBooking) {
        QueueItem nextCustomer = waitingQueueManager.getNextInQueue(
                deletedBooking.getDate(),
                deletedBooking.getTime()
        );

        if (nextCustomer == null) {
            return;
        }

        Booking newBooking = new Booking(
                deletedBooking.getDate(),
                deletedBooking.getTime(),
                nextCustomer.getUsername(),
                nextCustomer.getFullName(),
                nextCustomer.getPhoneNumber(),
                nextCustomer.getEmail()
        );

        bookingManager.addBooking(newBooking);

        JOptionPane.showMessageDialog(
                mainView,
                nextCustomer.getFullName() + " was next in queue and has now received the booking:\n" +
                        deletedBooking.getDate() + " at " + deletedBooking.getTime()
        );
    }

    private void showPersonInfo() {
        if (!loggedInUser.isAdmin()) {
            JOptionPane.showMessageDialog(mainView, "Only admin can see person info.");
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (User user : userManager.getUsers()) {
            builder.append("Username: ")
                    .append(user.getUsername())
                    .append(" | Name: ")
                    .append(user.getFullName())
                    .append(" | Phone: ")
                    .append(user.getPhoneNumber())
                    .append(" | Email: ")
                    .append(user.getEmail())
                    .append(" | Role: ")
                    .append(user.getRole())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(mainView, builder.toString(), "Person info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showWaitingQueues() {
        String info;

        if (loggedInUser.isAdmin()) {
            info = waitingQueueManager.getAllQueuesInfo(true);
        } else {
            info = waitingQueueManager.getUserQueueInfo(loggedInUser.getUsername());
        }

        mainView.updateQueueArea(info);
    }
    private void createAccount() {
        String username = registerView.getUsername();
        String password = registerView.getPassword();
        String fullName = registerView.getFullName();
        String phone = registerView.getPhone();
        String email = registerView.getEmail();
        String role = registerView.getSelectedRole();

        boolean registered = userManager.registerUser(
                username,
                password,
                role,
                fullName,
                phone,
                email
        );

        if (!registered) {
            JOptionPane.showMessageDialog(
                    registerView,
                    "Could not register. Make sure:\n" +
                            "- Username is at least 3 characters\n" +
                            "- Password is at least 6 characters\n" +
                            "- Email contains @\n" +
                            "- Phone number contains only numbers\n" +
                            "- All fields are filled in\n" +
                            "- Username is not already taken"            );
            return;
        }

        JOptionPane.showMessageDialog(registerView, "Account created. You can now login.");
        registerView.dispose();
    }
}
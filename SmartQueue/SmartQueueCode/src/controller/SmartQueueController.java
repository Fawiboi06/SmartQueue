package controller;

import View.DayBookingView;
import View.Dialog;
import View.GUImainBody;
import View.ListView;
import View.LoginView;
import View.RegisterView;
import model.Booking;
import model.BookingManager;
import model.QueueItem;
import model.User;
import model.UserManager;
import model.WaitingQueueManager;

import javax.swing.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmartQueueController {

    private RegisterView registerView;
    private LoginView loginView;
    private GUImainBody mainView;
    private DayBookingView dayBookingView;
    private ListView listView;

    private final BookingManager bookingManager;
    private final UserManager userManager;
    private final WaitingQueueManager waitingQueueManager;

    private User loggedInUser;
    private YearMonth currentMonth = YearMonth.now();
    private String selectedDate;
    private final YearMonth minimumMonth = YearMonth.now();

    private static final String[] TIMES = {
            "08:00", "08:30",
            "09:00", "09:30",
            "10:00", "10:30",
            "11:00", "11:30",
            "12:00", "12:30",
            "13:00", "13:30",
            "14:00", "14:30",
            "15:00", "15:30",
            "16:00"
    };

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
            Dialog.showError(loginView, "Wrong username or password.");
            return;
        }

        Dialog.showSuccess(loginView, "Login successful as " + loggedInUser.getRole());

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

        mainView.getViewBookingButton().addActionListener(e -> showBookingsWindow());
        mainView.getDeleteBookingButton().addActionListener(e -> showBookingsWindow());

        mainView.getShowPersonInfoButton().addActionListener(e -> showPersonInfo());
        mainView.getShowQueuesButton().addActionListener(e -> showWaitingQueuesWindow());

        mainView.getBackButton().addActionListener(e -> {
            mainView.dispose();
            loggedInUser = null;
            showLoginView();
        });

        mainView.getCloseButton().addActionListener(e -> System.exit(0));

        mainView.getPreviousButton().addActionListener(e -> {
            YearMonth previousMonth = currentMonth.minusMonths(1);

            if (previousMonth.isBefore(minimumMonth)) {
                Dialog.showInfo(mainView, "Calendar", "You cannot go back to previous months.");
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
        dayBookingView.updateTimes(createTimeRowsForSelectedDate());

        dayBookingView.getBookButton().addActionListener(e -> bookOrJoinQueue());
        dayBookingView.getShowBookingsButton().addActionListener(e -> showBookingsForSelectedDay());
        dayBookingView.getShowQueueButton().addActionListener(e -> showQueueForSelectedTime());

        dayBookingView.getBackButton().addActionListener(e -> {
            dayBookingView.dispose();
            mainView.setVisible(true);
        });

        dayBookingView.setVisible(true);
    }

    private List<String> createTimeRowsForSelectedDate() {
        List<String> rows = new ArrayList<>();
        List<Booking> bookings = bookingManager.getBookingsForDate(selectedDate);

        for (String time : TIMES) {
            Booking booked = null;

            for (Booking booking : bookings) {
                if (booking.getTime().equals(time)) {
                    booked = booking;
                    break;
                }
            }

            if (booked == null) {
                rows.add(time + " - Available");
            } else {
                rows.add(time + " - Booked by " + booked.getFullName());
            }
        }

        return rows;
    }

    private void bookOrJoinQueue() {
        String time = dayBookingView.getSelectedTime();

        if (time == null || time.isBlank()) {
            Dialog.showError(dayBookingView, "Please select a time.");
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
                Dialog.showError(dayBookingView, "Could not create booking.");
                return;
            }

            Dialog.showSuccess(dayBookingView, "Booking created: " + selectedDate + " at " + time);
        } else {
            if (existingBooking.getUsername().equalsIgnoreCase(loggedInUser.getUsername())) {
                Dialog.showInfo(dayBookingView, "Booking", "You already have this booking.");
                return;
            }

            boolean added = waitingQueueManager.addToWaitingQueue(selectedDate, time, loggedInUser);

            if (!added) {
                int position = waitingQueueManager.getQueuePosition(
                        selectedDate,
                        time,
                        loggedInUser.getUsername()
                );

                if (position != -1) {
                    Dialog.showInfo(
                            dayBookingView,
                            "Waiting queue",
                            "You are already in queue. Your position is: " + position
                    );
                } else {
                    Dialog.showError(dayBookingView, "Could not add you to the queue.");
                }

                return;
            }

            int position = waitingQueueManager.getQueuePosition(
                    selectedDate,
                    time,
                    loggedInUser.getUsername()
            );

            Dialog.showInfo(
                    dayBookingView,
                    "Waiting queue",
                    "This time is already booked.\nYou have been added to the waiting queue.\nYour position is: " + position
            );
        }

        dayBookingView.updateTimes(createTimeRowsForSelectedDate());
    }

    private void showBookingsWindow() {
        if (bookingManager.isEmpty()) {
            Dialog.showInfo(mainView, "Bookings", "No bookings yet.");
            return;
        }

        String[][] rows = createBookingTableRows();

        if (rows.length == 0) {
            Dialog.showInfo(mainView, "Bookings", "No bookings to show.");
            return;
        }

        String[] columns;

        if (loggedInUser.isAdmin()) {
            columns = new String[]{"No", "Date", "Time", "Name", "Username", "Phone", "Email"};
        } else {
            columns = new String[]{"No", "Date", "Time", "Name"};
        }

        listView = new ListView(
                mainView,
                "Bookings",
                "Search for date, time, name, username, phone or email. Select a row to delete a booking.",
                columns,
                rows,
                true
        );

        listView.getDeleteButton().addActionListener(e -> deleteSelectedBookingFromWindow());
        listView.getCloseButton().addActionListener(e -> listView.dispose());

        listView.setVisible(true);
    }

    private String[][] createBookingTableRows() {
        List<String[]> rows = new ArrayList<>();

        int index = 1;

        if (loggedInUser.isAdmin()) {
            List<Booking> allBookings = bookingManager.getBookings();

            for (Booking booking : allBookings) {
                String[] row = {
                        String.valueOf(index),
                        booking.getDate(),
                        booking.getTime(),
                        booking.getFullName(),
                        booking.getUsername(),
                        booking.getPhoneNumber(),
                        booking.getEmail()
                };

                rows.add(row);
                index++;
            }
        } else {
            List<Booking> userBookings = bookingManager.getBookingsForUser(loggedInUser.getUsername());

            for (Booking booking : userBookings) {
                String[] row = {
                        String.valueOf(index),
                        booking.getDate(),
                        booking.getTime(),
                        booking.getFullName()
                };

                rows.add(row);
                index++;
            }
        }

        String[][] result = new String[rows.size()][];

        for (int i = 0; i < rows.size(); i++) {
            result[i] = rows.get(i);
        }

        return result;
    }

    private void deleteSelectedBookingFromWindow() {
        int selectedNumber = listView.getSelectedNumber();

        if (selectedNumber == -1) {
            Dialog.showError(listView, "Please select a booking first.");
            return;
        }

        Booking bookingToDelete;

        if (loggedInUser.isAdmin()) {
            bookingToDelete = bookingManager.getBookingByNumberForAdmin(selectedNumber);
        } else {
            bookingToDelete = bookingManager.getBookingByNumberForUser(
                    selectedNumber,
                    loggedInUser.getUsername()
            );
        }

        if (bookingToDelete == null) {
            Dialog.showError(listView, "Could not find selected booking.");
            return;
        }

        boolean deleted = bookingManager.removeBooking(bookingToDelete);

        if (!deleted) {
            Dialog.showError(listView, "Could not delete booking.");
            return;
        }

        promoteNextCustomerIfPossible(bookingToDelete);

        Dialog.showSuccess(listView, "Booking deleted.");

        listView.dispose();
        showBookingsWindow();

        if (dayBookingView != null && dayBookingView.isDisplayable()) {
            dayBookingView.updateTimes(createTimeRowsForSelectedDate());
        }
    }

    private void showWaitingQueuesWindow() {
        String[][] rows = createWaitingQueueTableRows();

        if (rows.length == 0) {
            Dialog.showInfo(mainView, "Waiting queues", "No waiting queues to show.");
            return;
        }

        String[] columns;

        if (loggedInUser.isAdmin()) {
            columns = new String[]{"No", "Date", "Time", "Position", "Name", "Username", "Phone", "Email"};
        } else {
            columns = new String[]{"No", "Date", "Time", "Position", "Name"};
        }

        listView = new ListView(
                mainView,
                "Waiting queues",
                "Search for date, time, position, name or username.",
                columns,
                rows,
                false
        );

        listView.getCloseButton().addActionListener(e -> listView.dispose());

        listView.setVisible(true);
    }

    private String[][] createWaitingQueueTableRows() {
        List<String[]> rows = new ArrayList<>();
        Map<String, List<QueueItem>> queues = waitingQueueManager.getWaitingQueuesSnapshot();

        int rowNumber = 1;

        for (String key : queues.keySet()) {
            String[] dateAndTime = key.split(" ");

            String date = dateAndTime[0];
            String time = "";

            if (dateAndTime.length > 1) {
                time = dateAndTime[1];
            }

            List<QueueItem> queueItems = queues.get(key);

            int position = 1;

            for (QueueItem item : queueItems) {
                if (!loggedInUser.isAdmin() &&
                        !item.getUsername().equalsIgnoreCase(loggedInUser.getUsername())) {
                    position++;
                    continue;
                }

                if (loggedInUser.isAdmin()) {
                    String[] row = {
                            String.valueOf(rowNumber),
                            date,
                            time,
                            String.valueOf(position),
                            item.getFullName(),
                            item.getUsername(),
                            item.getPhoneNumber(),
                            item.getEmail()
                    };

                    rows.add(row);
                } else {
                    String[] row = {
                            String.valueOf(rowNumber),
                            date,
                            time,
                            String.valueOf(position),
                            item.getFullName()
                    };

                    rows.add(row);
                }

                rowNumber++;
                position++;
            }
        }

        String[][] result = new String[rows.size()][];

        for (int i = 0; i < rows.size(); i++) {
            result[i] = rows.get(i);
        }

        return result;
    }

    private void showBookingsForSelectedDay() {
        List<Booking> bookings = bookingManager.getBookingsForDate(selectedDate);

        if (bookings.isEmpty()) {
            Dialog.showInfo(dayBookingView, "Bookings", "No bookings for selected day.");
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (Booking booking : bookings) {
            if (!loggedInUser.isAdmin() &&
                    !booking.getUsername().equalsIgnoreCase(loggedInUser.getUsername())) {
                continue;
            }

            builder.append("Date: ")
                    .append(booking.getDate())
                    .append("\n")
                    .append("Time: ")
                    .append(booking.getTime())
                    .append("\n")
                    .append("Name: ")
                    .append(booking.getFullName())
                    .append("\n");

            if (loggedInUser.isAdmin()) {
                builder.append("Username: ")
                        .append(booking.getUsername())
                        .append("\n")
                        .append("Phone: ")
                        .append(booking.getPhoneNumber())
                        .append("\n")
                        .append("Email: ")
                        .append(booking.getEmail())
                        .append("\n");
            }

            builder.append("\n");
        }

        if (builder.length() == 0) {
            Dialog.showInfo(dayBookingView, "Bookings", "No bookings to show for your user.");
            return;
        }

        Dialog.showInfo(dayBookingView, "Bookings", builder.toString());
    }

    private void showQueueForSelectedTime() {
        String time = dayBookingView.getSelectedTime();

        if (time == null || time.isBlank()) {
            Dialog.showError(dayBookingView, "Please select a time.");
            return;
        }

        String info = waitingQueueManager.getQueueInfoForTime(
                selectedDate,
                time,
                loggedInUser.isAdmin()
        );

        Dialog.showInfo(dayBookingView, "Waiting queue", info);
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

        Dialog.showInfo(
                mainView,
                "Waiting queue",
                nextCustomer.getFullName() + " was next in queue and has now received the booking:\n" +
                        deletedBooking.getDate() + " at " + deletedBooking.getTime()
        );
    }

    private void showPersonInfo() {
        if (!loggedInUser.isAdmin()) {
            Dialog.showError(mainView, "Only admin can see person info.");
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

        Dialog.showInfo(mainView, "Person info", builder.toString());
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
            Dialog.showError(
                    registerView,
                    "Could not register.\nCheck all fields, email format or username already exists."
            );
            return;
        }

        Dialog.showSuccess(registerView, "Account created. You can now login.");
        registerView.dispose();
    }
}
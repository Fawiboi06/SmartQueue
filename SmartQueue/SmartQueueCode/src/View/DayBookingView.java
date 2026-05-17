package View;

import model.Booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.YearMonth;
import java.util.List;

public class DayBookingView extends JFrame {

    private JButton bookButton;
    private JButton showBookingsButton;
    private JButton showQueueButton;
    private JButton backButton;

    private JList<String> timeList;
    private DefaultListModel<String> timeListModel;

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

    public DayBookingView(int day, YearMonth month) {
        setTitle("Bookings for selected day " + day);
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(
                "Chosen day: " + month + "-" + String.format("%02d", day),
                SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));

        // Vänster panel - klickbara tider
        JPanel bookingPanel = new JPanel(new BorderLayout(10, 10));
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Times"));

        timeListModel = new DefaultListModel<>();
        timeList = new JList<>(timeListModel);
        timeList.setFont(new Font("Arial", Font.PLAIN, 20));
        timeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(timeList);
        bookingPanel.add(scrollPane, BorderLayout.CENTER);

        // Höger panel - info
        JPanel infoPanel = new JPanel(new BorderLayout(10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));

        JTextArea infoArea = new JTextArea(
                "How it works:\n" +
                        "- Click on a time in the left panel.\n" +
                        "- If the time is free, a booking is created.\n" +
                        "- If the time is booked, you are placed in queue.\n" +
                        "- If the booked customer cancels, the next customer gets the time."
        );

        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        centerPanel.add(bookingPanel);
        centerPanel.add(infoPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        bookButton = new JButton("Book / Join queue");
        showBookingsButton = new JButton("Show bookings");
        showQueueButton = new JButton("Show queue");
        backButton = new JButton("Return");

        bottomPanel.add(bookButton);
        bottomPanel.add(showBookingsButton);
        bottomPanel.add(showQueueButton);
        bottomPanel.add(backButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public void updateTimes(List<Booking> bookings) {
        timeListModel.clear();

        for (String time : TIMES) {
            Booking booked = null;

            for (Booking booking : bookings) {
                if (booking.getTime().equals(time)) {
                    booked = booking;
                    break;
                }
            }

            if (booked == null) {
                timeListModel.addElement(time + " - Available");
            } else {
                timeListModel.addElement(time + " - Booked by " + booked.getFullName());
            }
        }
    }

    public JButton getBookButton() {
        return bookButton;
    }

    public JButton getShowBookingsButton() {
        return showBookingsButton;
    }

    public JButton getShowQueueButton() {
        return showQueueButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public String getSelectedTime() {
        String selectedValue = timeList.getSelectedValue();

        if (selectedValue == null || selectedValue.isBlank()) {
            return null;
        }

        // Tar bara själva tiden före " - "
        return selectedValue.split(" - ")[0];
    }

    public void resetTime() {
        timeList.clearSelection();
    }
}
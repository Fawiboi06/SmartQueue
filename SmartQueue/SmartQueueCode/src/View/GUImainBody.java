package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUImainBody extends JFrame {

    private JLabel monthLabel;
    private JButton previousButton;
    private JButton nextButton;
    private JButton bookingButton;
    private JButton viewBookingButton;
    private JButton backButton;
    private JButton closeButton;
    private JTextArea infoArea;
    private JButton deleteBookingButton;
    private List<JButton> dayButtons;

    public GUImainBody() {
        dayButtons = new ArrayList<>();

        setTitle("SmartQueue");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Welcome to SmartQueue", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));

        JPanel calendarPanel = new JPanel(new BorderLayout(10, 10));
        calendarPanel.setBorder(BorderFactory.createTitledBorder("Kalender"));

        JPanel topCalenderPanel = new JPanel(new BorderLayout(10, 0));
        previousButton = new JButton("<");
        monthLabel = new JLabel("April 2026", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton = new JButton(">");

        topCalenderPanel.add(previousButton, BorderLayout.WEST);
        topCalenderPanel.add(monthLabel, BorderLayout.CENTER);
        topCalenderPanel.add(nextButton, BorderLayout.EAST);

        calendarPanel.add(topCalenderPanel, BorderLayout.NORTH);

        JPanel daysPanel = new JPanel(new GridLayout(6, 7, 8, 8));

        String[] daysName = {"mån", "tis", "ons", "tors", "fre", "lör", "sön"};
        for (String dayName : daysName) {
            JLabel dayLabel = new JLabel(dayName, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(dayLabel);
        }
        for (int i = 1; i <= 30; i++) {
            JButton dayButton = new JButton(String.valueOf(i));
            dayButtons.add(dayButton);
            daysPanel.add(dayButton);
        }
        for (int i = 0; i < 5; i++) {
            JLabel emptyLabel = new JLabel("");
            daysPanel.add(emptyLabel);
        }

        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Valt datum"));

        JLabel selectedDateLabel = new JLabel("Date: --/--/----");
        selectedDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        selectedDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bookingInfoLabel = new JLabel("Bokning: Ingen vald");
        bookingInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoArea = new JTextArea("No bookings yet.");
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setPreferredSize(new Dimension(250, 200));

        bookingButton = new JButton("Boka tid");
        bookingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewBookingButton = new JButton("See bookings");
        viewBookingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteBookingButton = new JButton("Delete booking");
        deleteBookingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(selectedDateLabel);
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(bookingInfoLabel);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(scrollPane);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(bookingButton);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(viewBookingButton);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(deleteBookingButton);

        centerPanel.add(calendarPanel, BorderLayout.CENTER);
        centerPanel.add(infoPanel, BorderLayout.EAST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Return");
        closeButton = new JButton("Close");

        bottomPanel.add(backButton);
        bottomPanel.add(closeButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public List<JButton> getDayButtons() {
        return dayButtons;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getBookingButton() {
        return bookingButton;
    }

    public JButton getViewBookingButton() {
        return viewBookingButton;
    }

    public JButton getPreviousButton() {
        return previousButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JLabel getMonthLabel() {
        return monthLabel;
    }

    public void updateBookingList(String text) {
        infoArea.setText(text);
    }

    public JButton getDeleteBookingButton() {
        return deleteBookingButton;
    }
}


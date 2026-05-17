package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class GUImainBody extends JFrame {

    private JTextArea bookingArea;
    private JTextArea queueArea;
    private JLabel monthLabel;
    private JButton previousButton;
    private JButton nextButton;
    private JButton viewBookingButton;
    private JButton deleteBookingButton;
    private JButton showPersonInfoButton;
    private JButton showQueuesButton;
    private JButton backButton;
    private JButton closeButton;
    private JPanel daysPanel;
    private List<JButton> dayButtons;

    public GUImainBody(String username, String role) {
        dayButtons = new ArrayList<>();

        setTitle("SmartQueue");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Welcome to SmartQueue - " + username + " (" + role + ")", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));

        JPanel calendarPanel = new JPanel(new BorderLayout(10, 10));
        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));

        JPanel topCalendarPanel = new JPanel(new BorderLayout(10, 0));

        previousButton = new JButton("<");
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton = new JButton(">");

        topCalendarPanel.add(previousButton, BorderLayout.WEST);
        topCalendarPanel.add(monthLabel, BorderLayout.CENTER);
        topCalendarPanel.add(nextButton, BorderLayout.EAST);

        calendarPanel.add(topCalendarPanel, BorderLayout.NORTH);

        daysPanel = new JPanel(new GridLayout(0, 7, 8, 8));
        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createTitledBorder("Information"));

        bookingArea = new JTextArea("No bookings shown.");
        bookingArea.setEditable(false);
        bookingArea.setLineWrap(true);
        bookingArea.setWrapStyleWord(true);
        bookingArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane bookingScrollPane = new JScrollPane(bookingArea);
        bookingScrollPane.setPreferredSize(new Dimension(300, 230));

        viewBookingButton = new JButton("Show bookings");
        viewBookingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        deleteBookingButton = new JButton("Delete booking");
        deleteBookingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        showPersonInfoButton = new JButton("Show person info");
        showPersonInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        showQueuesButton = new JButton("Show waiting queues");
        showQueuesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        queueArea = new JTextArea("No waiting queues shown.");
        queueArea.setEditable(false);
        queueArea.setLineWrap(true);
        queueArea.setWrapStyleWord(true);
        queueArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane queueScrollPane = new JScrollPane(queueArea);
        queueScrollPane.setPreferredSize(new Dimension(300, 170));

        sidePanel.add(bookingScrollPane);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(viewBookingButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(deleteBookingButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(showPersonInfoButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(showQueuesButton);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(queueScrollPane);

        centerPanel.add(calendarPanel, BorderLayout.CENTER);
        centerPanel.add(sidePanel, BorderLayout.EAST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        backButton = new JButton("Return");
        closeButton = new JButton("Close");

        bottomPanel.add(backButton);
        bottomPanel.add(closeButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public void updateMonth(YearMonth month) {
        monthLabel.setText(month.getMonth() + " " + month.getYear());
        rebuildCalendar(month);
    }

    private void rebuildCalendar(YearMonth month) {
        daysPanel.removeAll();
        dayButtons.clear();

        String[] dayNames = {"mån", "tis", "ons", "tors", "fre", "lör", "sön"};

        for (String dayName : dayNames) {
            JLabel dayLabel = new JLabel(dayName, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(dayLabel);
        }

        int firstDayColumn = month.atDay(1).getDayOfWeek().getValue();

        for (int i = 1; i < firstDayColumn; i++) {
            daysPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButtons.add(dayButton);
            daysPanel.add(dayButton);
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    public List<JButton> getDayButtons() {
        return dayButtons;
    }

    public JButton getPreviousButton() {
        return previousButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getViewBookingButton() {
        return viewBookingButton;
    }

    public JButton getDeleteBookingButton() {
        return deleteBookingButton;
    }

    public JButton getShowPersonInfoButton() {
        return showPersonInfoButton;
    }

    public JButton getShowQueuesButton() {
        return showQueuesButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public void updateBookingList(String text) {
        bookingArea.setText(text);
    }

    public void updateQueueArea(String text) {
        queueArea.setText(text);
    }

    public void setAdminMode(boolean admin) {
        showPersonInfoButton.setVisible(admin);
    }
}
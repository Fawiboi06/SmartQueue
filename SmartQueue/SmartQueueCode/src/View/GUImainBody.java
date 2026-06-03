package View;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class GUImainBody extends JFrame {

    private JTextArea queueArea;
    private JLabel monthLabel;
    private JLabel bookingInfoLabel;

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
        Style.applyGlobalStyle();

        dayButtons = new ArrayList<>();

        setTitle("SmartQueue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 720));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Style.BACKGROUND);

        root.add(createHeader(username, role), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createHeader(String username, String role) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Style.DARK);
        header.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));

        JLabel titleLabel = new JLabel("SmartQueue");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Logged in as " + username + " (" + role + ")");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        userLabel.setForeground(new Color(203, 213, 225));

        JPanel leftHeader = new JPanel();
        leftHeader.setOpaque(false);
        leftHeader.setLayout(new BoxLayout(leftHeader, BoxLayout.Y_AXIS));
        leftHeader.add(titleLabel);
        leftHeader.add(Box.createVerticalStrut(4));
        leftHeader.add(userLabel);

        backButton = Style.secondaryButton("Log out");
        closeButton = Style.dangerButton("Close");

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightHeader.setOpaque(false);
        rightHeader.add(backButton);
        rightHeader.add(closeButton);

        header.add(leftHeader, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {
        JPanel content = new JPanel(new BorderLayout(18, 18));
        content.setBackground(Style.BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel calendarCard = createCalendarPanel();
        JPanel infoPanel = createInfoPanel();

        content.add(calendarCard, BorderLayout.CENTER);
        content.add(infoPanel, BorderLayout.EAST);

        return content;
    }

    private JPanel createCalendarPanel() {
        JPanel calendarCard = Style.cardLayout(new BorderLayout(15, 15));

        JPanel topCalendarPanel = new JPanel(new BorderLayout(15, 0));
        topCalendarPanel.setBackground(Color.WHITE);

        previousButton = Style.secondaryButton("<");
        nextButton = Style.secondaryButton(">");

        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        monthLabel.setForeground(Style.DARK);

        topCalendarPanel.add(previousButton, BorderLayout.WEST);
        topCalendarPanel.add(monthLabel, BorderLayout.CENTER);
        topCalendarPanel.add(nextButton, BorderLayout.EAST);

        daysPanel = new JPanel(new GridLayout(0, 7, 10, 10));
        daysPanel.setBackground(Color.WHITE);

        calendarCard.add(topCalendarPanel, BorderLayout.NORTH);
        calendarCard.add(daysPanel, BorderLayout.CENTER);

        return calendarCard;
    }

    private JPanel createInfoPanel() {
        JPanel sidePanel = new JPanel(new GridLayout(2, 1, 0, 18));
        sidePanel.setPreferredSize(new Dimension(430, 0));
        sidePanel.setBackground(Style.BACKGROUND);

        sidePanel.add(createBookingCard());
        sidePanel.add(createQueueCard());

        return sidePanel;
    }

    private JPanel createBookingCard() {
        JPanel bookingCard = Style.cardLayout(new BorderLayout(12, 12));

        JLabel bookingTitle = Style.subtitle("Bookings");

        bookingInfoLabel = new JLabel(
                "<html>Use <b>Show bookings</b> to open a clear overview of all bookings.</html>"
        );
        bookingInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        bookingInfoLabel.setForeground(Style.MUTED);

        JPanel textPanel = new JPanel(new BorderLayout(0, 10));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(bookingTitle, BorderLayout.NORTH);
        textPanel.add(bookingInfoLabel, BorderLayout.CENTER);

        JPanel bookingButtons = new JPanel(new GridLayout(2, 1, 0, 12));
        bookingButtons.setBackground(Color.WHITE);

        viewBookingButton = Style.primaryButton("Show bookings");
        deleteBookingButton = Style.dangerButton("Delete booking");

        bookingButtons.add(viewBookingButton);
        bookingButtons.add(deleteBookingButton);

        bookingCard.add(textPanel, BorderLayout.CENTER);
        bookingCard.add(bookingButtons, BorderLayout.SOUTH);

        return bookingCard;
    }

    private JPanel createQueueCard() {
        JPanel queueCard = Style.cardLayout(new BorderLayout(10, 10));

        JLabel queueTitle = Style.subtitle("Waiting queues");

        queueArea = new JTextArea("No waiting queues shown.");
        Style.styleTextArea(queueArea);

        JScrollPane queueScrollPane = new JScrollPane(queueArea);
        queueScrollPane.setBorder(BorderFactory.createLineBorder(Style.BORDER));
        queueScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel queueButtons = new JPanel(new GridLayout(2, 1, 0, 10));
        queueButtons.setBackground(Color.WHITE);

        showPersonInfoButton = Style.secondaryButton("Show person info");
        showQueuesButton = Style.primaryButton("Show waiting queues");

        queueButtons.add(showPersonInfoButton);
        queueButtons.add(showQueuesButton);

        queueCard.add(queueTitle, BorderLayout.NORTH);
        queueCard.add(queueScrollPane, BorderLayout.CENTER);
        queueCard.add(queueButtons, BorderLayout.SOUTH);

        return queueCard;
    }

    public void updateMonth(YearMonth month) {
        monthLabel.setText(formatMonth(month));
        rebuildCalendar(month);
    }

    private String formatMonth(YearMonth month) {
        String name = month.getMonth().toString().toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name + " " + month.getYear();
    }

    private void rebuildCalendar(YearMonth month) {
        daysPanel.removeAll();
        dayButtons.clear();

        String[] dayNames = {"Mån", "Tis", "Ons", "Tor", "Fre", "Lör", "Sön"};

        for (String dayName : dayNames) {
            JLabel dayLabel = new JLabel(dayName, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
            dayLabel.setForeground(Style.MUTED);
            dayLabel.setOpaque(true);
            dayLabel.setBackground(new Color(248, 250, 252));
            dayLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            daysPanel.add(dayLabel);
        }

        int firstDayColumn = month.atDay(1).getDayOfWeek().getValue();

        for (int i = 1; i < firstDayColumn; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.WHITE);
            daysPanel.add(emptyPanel);
        }

        LocalDate today = LocalDate.now();

        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            JButton dayButton = new JButton(String.valueOf(day));

            dayButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
            dayButton.setFocusPainted(false);
            dayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            dayButton.setOpaque(true);

            LocalDate buttonDate = month.atDay(day);

            if (buttonDate.equals(today)) {
                dayButton.setBackground(Style.PRIMARY);
                dayButton.setForeground(Color.WHITE);
                dayButton.setBorder(BorderFactory.createLineBorder(Style.PRIMARY_DARK, 2));
            } else {
                dayButton.setBackground(new Color(248, 250, 252));
                dayButton.setForeground(Style.TEXT);
                dayButton.setBorder(BorderFactory.createLineBorder(Style.BORDER));
            }

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
        bookingInfoLabel.setText("<html>Bookings updated. Press <b>Show bookings</b> to view them.</html>");
    }

    public void updateQueueArea(String text) {
        queueArea.setText(text);
    }

    public void setAdminMode(boolean admin) {
        showPersonInfoButton.setVisible(true);

        if (admin) {
            showPersonInfoButton.setText("Show person info");
        } else {
            showPersonInfoButton.setText("My profile");
        }

        revalidate();
        repaint();
    }
}
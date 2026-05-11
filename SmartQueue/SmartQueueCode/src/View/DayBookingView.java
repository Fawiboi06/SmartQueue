package View;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DayBookingView extends JFrame {

    private JButton bokaButton;
    private JButton seeMoreButton;
    private JButton backButton;
    private JComboBox<String>timeBox;
    private JTextArea bookingArea;

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

    public DayBookingView(int day){
        setTitle("Bookings for selected day " + day);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Choosen day: 2026-04" + String.format("%02d",day),SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));

        JPanel bookingPanel = new JPanel(new BorderLayout(10, 10));
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Bookings"));

        bookingArea = new JTextArea();
        bookingArea.setEditable(false);
        bookingArea.setFont(new Font("Arial", Font.PLAIN, 14));
        updateTimes(new java.util.ArrayList<>());

        bookingPanel.add(new JScrollPane(bookingArea), BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new BorderLayout(10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));

        JTextArea infoArea = new JTextArea(

                        "- info\n" +
                        "- bokningar\n" +
                        "- tider\n" +
                        "- Info"
        );
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        centerPanel.add(bookingPanel);
        centerPanel.add(infoPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        timeBox = new JComboBox<>(TIMES);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        bokaButton = new JButton("Book");
        seeMoreButton = new JButton("Show bookings");
        backButton = new JButton("Return");

        bottomPanel.add(timeBox);
        bottomPanel.add(bokaButton);
        bottomPanel.add(seeMoreButton);
        bottomPanel.add(backButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public void updateTimes(java.util.List<model.Booking> bookings) {
        String[] times = TIMES;
        StringBuilder builder = new StringBuilder();

        for (String time : times) {
            boolean isBooked = false;

            for (model.Booking b : bookings) {
                if (b.getTime().equals(time)) {
                    isBooked = true;
                    break;
                }
            }

            if (isBooked) {
                builder.append(time).append(" - Bokad\n");
            } else {
                builder.append(time).append(" - Ledig\n");
            }
        }

        bookingArea.setText(builder.toString());
    }

    public JButton getBokaButton() {
        return bokaButton;
    }

    public JButton getSeeMoreButton() {
        return seeMoreButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public String getSelectedTime(){
        return(String) timeBox.getSelectedItem();
    }
    public void resetTime(){
        timeBox.setSelectedIndex(0);
    }
}

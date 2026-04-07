package View;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DayBookingView extends JFrame {

    public DayBookingView(GUImainBody mainWindow, int day){
        setTitle("Bokningar för dag " + day);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Dag " + day + " - Bokningar", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));

        JPanel bookingPanel = new JPanel(new BorderLayout(10, 10));
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Bokningar"));

        JTextArea bookingArea = new JTextArea(
                "08:00 - Ledig\n" +
                        "09:00 - Ledig\n" +
                        "10:00 - Bokad\n" +
                        "11:00 - Ledig\n" +
                        "12:00 - Ledig"
        );
        bookingArea.setEditable(false);
        bookingArea.setFont(new Font("Arial", Font.PLAIN, 14));

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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton bokaButton = new JButton("Boka");
        JButton seeMoreButton = new JButton("Se mer");
        JButton backButton = new JButton("Tillbaka");

        backButton.addActionListener(e -> {
            mainWindow.setVisible(true);
            dispose();
        });

        bottomPanel.add(bokaButton);
        bottomPanel.add(seeMoreButton);
        bottomPanel.add(backButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }
}

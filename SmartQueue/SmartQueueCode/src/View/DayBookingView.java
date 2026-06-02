package View;

import javax.swing.*;
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

    public DayBookingView(int day, YearMonth month) {
        Style.applyGlobalStyle();

        setTitle("Bookings for selected day");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 650));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Style.BACKGROUND);

        root.add(createHeader(day, month), BorderLayout.NORTH);
        root.add(createContent(), BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createHeader(int day, YearMonth month) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Style.DARK);
        header.setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));

        JLabel title = new JLabel("Selected day");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        JLabel dateLabel = new JLabel(month + "-" + String.format("%02d", day));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateLabel.setForeground(new Color(203, 213, 225));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(dateLabel);

        backButton = Style.secondaryButton("Return");

        header.add(textPanel, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(18, 18));
        content.setBackground(Style.BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel timeCard = createTimeCard();
        JPanel infoCard = createInfoCard();

        content.add(timeCard, BorderLayout.CENTER);
        content.add(infoCard, BorderLayout.EAST);

        return content;
    }

    private JPanel createTimeCard() {
        JPanel card = Style.cardLayout(new BorderLayout(12, 12));

        JLabel title = Style.subtitle("Available times");
        JLabel helpText = Style.smallText("Select a time and press Book / Join queue.");

        JPanel top = new JPanel();
        top.setBackground(Color.WHITE);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(title);
        top.add(Box.createVerticalStrut(4));
        top.add(helpText);

        timeListModel = new DefaultListModel<>();
        timeList = new JList<>(timeListModel);

        timeList.setFont(new Font("Segoe UI", Font.BOLD, 18));
        timeList.setFixedCellHeight(70);
        timeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Viktigt: bakgrunden mellan lådorna
        timeList.setBackground(Color.WHITE);
        timeList.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        timeList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus
            ) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

                JLabel label = new JLabel(String.valueOf(value));
                label.setFont(new Font("Segoe UI", Font.BOLD, 18));
                label.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
                label.setOpaque(true);

                String text = String.valueOf(value);

                if (isSelected) {
                    label.setBackground(Style.PRIMARY);
                    label.setForeground(Color.WHITE);
                    label.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Style.PRIMARY_DARK, 2),
                            BorderFactory.createEmptyBorder(14, 18, 14, 18)
                    ));
                } else {
                    if (text.contains("Available")) {
                        label.setBackground(new Color(240, 253, 244));
                        label.setForeground(new Color(22, 101, 52));
                        label.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(187, 247, 208), 1),
                                BorderFactory.createEmptyBorder(14, 18, 14, 18)
                        ));
                    } else {
                        label.setBackground(new Color(254, 242, 242));
                        label.setForeground(new Color(153, 27, 27));
                        label.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(254, 202, 202), 1),
                                BorderFactory.createEmptyBorder(14, 18, 14, 18)
                        ));
                    }
                }

                panel.setBackground(Color.WHITE);
                panel.add(label, BorderLayout.CENTER);

                return panel;
            }
        });

        JScrollPane scrollPane = new JScrollPane(timeList);
        scrollPane.setBorder(BorderFactory.createLineBorder(Style.BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 12, 0));
        buttonPanel.setBackground(Color.WHITE);

        bookButton = Style.primaryButton("Book / Join queue");
        showBookingsButton = Style.secondaryButton("Show bookings");
        showQueueButton = Style.secondaryButton("Show queue");

        buttonPanel.add(bookButton);
        buttonPanel.add(showBookingsButton);
        buttonPanel.add(showQueueButton);

        card.add(top, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createInfoCard() {
        JPanel card = Style.cardLayout(new BorderLayout(12, 12));
        card.setPreferredSize(new Dimension(360, 0));

        JLabel title = Style.subtitle("How it works");

        JTextArea infoArea = new JTextArea(
                "1. Choose a time from the list.\n\n" +
                        "2. If the time is available, a booking is created.\n\n" +
                        "3. If the time is already booked, the user is placed in the waiting queue.\n\n" +
                        "4. If a booking is deleted, the first person in the queue receives the time."
        );

        Style.styleTextArea(infoArea);

        card.add(title, BorderLayout.NORTH);
        card.add(infoArea, BorderLayout.CENTER);

        return card;
    }

    public void updateTimes(List<String> timeRows) {
        timeListModel.clear();

        for (String row : timeRows) {
            timeListModel.addElement(row);
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

        return selectedValue.split(" - ")[0];
    }

    public void resetTime() {
        timeList.clearSelection();
    }
}
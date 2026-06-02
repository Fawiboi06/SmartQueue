package View;

import javax.swing.*;
import java.awt.*;

public class CustomerBookingView extends JDialog {

    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField phoneField;
    private JTextField emailField;

    private JButton confirmButton;
    private JButton cancelButton;

    public CustomerBookingView(JFrame parent, String date, String time, boolean bookedTime) {
        super(parent, "Customer booking", true);

        Style.applyGlobalStyle();

        setSize(660, 650);
        setMinimumSize(new Dimension(620, 600));
        setResizable(false);
        setLocationRelativeTo(parent);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Style.BACKGROUND);

        JPanel card = Style.cardLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(540, 560));

        JLabel titleLabel = Style.title("Customer booking");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel dateLabel = Style.smallText(date + " at " + time);
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        String statusText;
        if (bookedTime) {
            statusText = "This time is booked. Customer will be added to waiting queue.";
        } else {
            statusText = "Admin books this time for a customer.";
        }

        JLabel statusLabel = Style.smallText(statusText);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel requirementsPanel = createRequirementsPanel();

        usernameField = new JTextField();
        fullNameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        styleField(usernameField);
        styleField(fullNameField);
        styleField(phoneField);
        styleField(emailField);

        confirmButton = Style.primaryButton("Confirm");
        cancelButton = Style.secondaryButton("Cancel");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 2, 0);
        card.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 3, 0);
        card.add(dateLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 12, 0);
        card.add(statusLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 35, 14, 35);
        card.add(requirementsPanel, gbc);

        addField(card, gbc, 4, "Customer username", usernameField);
        addField(card, gbc, 6, "Full name", fullNameField);
        addField(card, gbc, 8, "Phone number", phoneField);
        addField(card, gbc, 10, "Email", emailField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 12;
        gbc.insets = new Insets(8, 35, 0, 35);
        card.add(buttonPanel, gbc);

        root.add(card);
        setContentPane(root);
    }

    private JPanel createRequirementsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.BORDER),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel title = new JLabel("Customer information requirements");
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(Style.DARK);

        JLabel text = new JLabel(
                "<html>" +
                        "Username minimum: 3 characters<br>" +
                        "Phone may contain numbers, +, - and spaces<br>" +
                        "Email must contain @<br>" +
                        "All fields must be filled in<br>" +
                        "Admin usernames cannot be used as customer bookings" +
                        "</html>"
        );

        text.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        text.setForeground(Style.TEXT);

        panel.add(title, BorderLayout.NORTH);
        panel.add(text, BorderLayout.CENTER);

        return panel;
    }

    private void addField(JPanel card, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridy = row;
        gbc.insets = new Insets(0, 35, 2, 35);
        card.add(Style.fieldLabel(labelText), gbc);

        gbc.gridy = row + 1;
        gbc.insets = new Insets(0, 35, 7, 35);
        card.add(field, gbc);
    }

    private void styleField(JTextField field) {
        field.setFont(Style.NORMAL_FONT);
        field.setForeground(Style.TEXT);
        field.setCaretColor(Style.TEXT);
        field.setPreferredSize(new Dimension(420, 34));
        field.setMinimumSize(new Dimension(420, 34));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.BORDER),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
    }

    public String getCustomerUsername() {
        return usernameField.getText();
    }

    public String getCustomerFullName() {
        return fullNameField.getText();
    }

    public String getCustomerPhone() {
        return phoneField.getText();
    }

    public String getCustomerEmail() {
        return emailField.getText();
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}

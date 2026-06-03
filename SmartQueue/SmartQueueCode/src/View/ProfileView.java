package View;

import model.User;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JDialog {

    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField roleField;
    private JTextField phoneField;
    private JTextField emailField;

    private JButton saveButton;
    private JButton cancelButton;

    public ProfileView(JFrame parent, User user) {
        super(parent, "My profile", true);

        Style.applyGlobalStyle();

        setSize(620, 670);
        setMinimumSize(new Dimension(580, 630));
        setResizable(false);
        setLocationRelativeTo(parent);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Style.BACKGROUND);

        JPanel card = Style.cardLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(500, 590));

        JLabel titleLabel = new JLabel("My profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titleLabel.setForeground(Style.DARK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = Style.smallText("You can only update your phone number and email");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel infoPanel = createInfoPanel();

        usernameField = new JTextField(user.getUsername());
        fullNameField = new JTextField(user.getFullName());
        roleField = new JTextField(user.getRole());
        phoneField = new JTextField(user.getPhoneNumber());
        emailField = new JTextField(user.getEmail());

        styleField(usernameField);
        styleField(fullNameField);
        styleField(roleField);
        styleField(phoneField);
        styleField(emailField);

        setReadOnly(usernameField);
        setReadOnly(fullNameField);
        setReadOnly(roleField);

        saveButton = Style.primaryButton("Save changes");
        cancelButton = Style.secondaryButton("Cancel");

        saveButton.setPreferredSize(new Dimension(200, 46));
        cancelButton.setPreferredSize(new Dimension(200, 46));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 2, 0);
        card.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(subtitleLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 35, 12, 35);
        card.add(infoPanel, gbc);

        addField(card, gbc, 3, "Username", usernameField);
        addField(card, gbc, 5, "Full name", fullNameField);
        addField(card, gbc, 7, "Role", roleField);
        addField(card, gbc, 9, "Phone number", phoneField);
        addField(card, gbc, 11, "Email", emailField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 18, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 13;
        gbc.insets = new Insets(12, 35, 0, 35);
        card.add(buttonPanel, gbc);

        root.add(card);
        setContentPane(root);
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 3));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.BORDER),
                BorderFactory.createEmptyBorder(7, 12, 7, 12)
        ));

        JLabel title = new JLabel("Editable information");
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(Style.DARK);

        JLabel text = new JLabel(
                "<html>" +
                        "You can update phone number and email only.<br>" +
                        "Phone may contain numbers, +, - and spaces.<br>" +
                        "Email must contain @." +
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
        gbc.insets = new Insets(0, 35, 6, 35);
        card.add(field, gbc);
    }

    private void styleField(JTextField field) {
        field.setFont(Style.NORMAL_FONT);
        field.setForeground(Style.TEXT);
        field.setCaretColor(Style.TEXT);

        field.setPreferredSize(new Dimension(420, 32));
        field.setMinimumSize(new Dimension(420, 32));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.BORDER),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)
        ));
    }

    private void setReadOnly(JTextField field) {
        field.setEditable(false);
        field.setFocusable(false);
        field.setBackground(new Color(248, 250, 252));
        field.setForeground(new Color(100, 116, 139));
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}

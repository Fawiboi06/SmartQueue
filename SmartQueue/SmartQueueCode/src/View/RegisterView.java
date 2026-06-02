package View;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JDialog {

        private JTextField usernameField;
        private JPasswordField passwordField;
        private JTextField fullNameField;
        private JTextField phoneField;
        private JTextField emailField;
        private JComboBox<String> roleBox;
        private JButton registerButton;
        private JButton cancelButton;

        public RegisterView(JFrame parent) {
                super(parent, "Register", true);

                Style.applyGlobalStyle();

                setSize(780, 760);
                setMinimumSize(new Dimension(760, 720));
                setResizable(false);

                JPanel root = new JPanel(new GridBagLayout());
                root.setBackground(Style.BACKGROUND);

                JPanel card = Style.cardLayout(new GridBagLayout());
                card.setPreferredSize(new Dimension(620, 660));

                usernameField = new JTextField();
                passwordField = new JPasswordField();
                fullNameField = new JTextField();
                phoneField = new JTextField();
                emailField = new JTextField();
                roleBox = new JComboBox<>(new String[]{"Customer", "Admin"});

                styleRegisterField(usernameField);
                styleRegisterField(passwordField);
                styleRegisterField(fullNameField);
                styleRegisterField(phoneField);
                styleRegisterField(emailField);
                styleRegisterComboBox(roleBox);

                registerButton = Style.primaryButton("Create account");
                cancelButton = Style.secondaryButton("Cancel");

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1;

                JLabel titleLabel = Style.title("Create account");
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JLabel subtitleLabel = Style.smallText("Fill in your information below");
                subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 6, 0);
                card.add(titleLabel, gbc);

                gbc.gridy = 1;
                gbc.insets = new Insets(0, 0, 22, 0);
                card.add(subtitleLabel, gbc);

                addField(card, gbc, 2, "Username", usernameField);
                addField(card, gbc, 4, "Password", passwordField);
                addField(card, gbc, 6, "Full name", fullNameField);
                addField(card, gbc, 8, "Phone number", phoneField);
                addField(card, gbc, 10, "Email", emailField);

                gbc.gridy = 12;
                gbc.insets = new Insets(0, 35, 5, 35);
                card.add(Style.fieldLabel("Role"), gbc);

                gbc.gridy = 13;
                gbc.insets = new Insets(0, 35, 22, 35);
                card.add(roleBox, gbc);

                JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 18, 0));
                buttonPanel.setBackground(Color.WHITE);
                buttonPanel.add(registerButton);
                buttonPanel.add(cancelButton);

                gbc.gridy = 14;
                gbc.insets = new Insets(0, 35, 0, 35);
                card.add(buttonPanel, gbc);

                root.add(card);
                setContentPane(root);

                setLocationRelativeTo(null);
        }

        private void addField(JPanel card, GridBagConstraints gbc, int row, String labelText, JTextField field) {
                gbc.gridy = row;
                gbc.insets = new Insets(0, 35, 4, 35);
                card.add(Style.fieldLabel(labelText), gbc);

                gbc.gridy = row + 1;
                gbc.insets = new Insets(0, 35, 11, 35);
                card.add(field, gbc);
        }

        private void styleRegisterField(JTextField field) {
                field.setFont(Style.NORMAL_FONT);
                field.setForeground(Style.TEXT);
                field.setBackground(Color.WHITE);
                field.setCaretColor(Style.TEXT);

                field.setPreferredSize(new Dimension(500, 40));
                field.setMinimumSize(new Dimension(500, 40));

                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Style.BORDER, 1),
                        BorderFactory.createEmptyBorder(7, 12, 7, 12)
                ));
        }

        private void styleRegisterComboBox(JComboBox<String> comboBox) {
                comboBox.setFont(Style.NORMAL_FONT);
                comboBox.setForeground(Style.TEXT);
                comboBox.setBackground(Color.WHITE);

                comboBox.setPreferredSize(new Dimension(500, 40));
                comboBox.setMinimumSize(new Dimension(500, 40));
        }

        public String getUsername() {
                return usernameField.getText();
        }

        public String getPassword() {
                return new String(passwordField.getPassword());
        }

        public String getFullName() {
                return fullNameField.getText();
        }

        public String getPhone() {
                return phoneField.getText();
        }

        public String getEmail() {
                return emailField.getText();
        }

        public String getSelectedRole() {
                return (String) roleBox.getSelectedItem();
        }

        public JButton getRegisterButton() {
                return registerButton;
        }

        public JButton getCancelButton() {
                return cancelButton;
        }
}
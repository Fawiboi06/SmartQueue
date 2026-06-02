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

                setSize(760, 760);
                setMinimumSize(new Dimension(720, 720));
                setResizable(false);

                JPanel root = new JPanel(new GridBagLayout());
                root.setBackground(Style.BACKGROUND);

                JPanel card = Style.cardLayout(new GridBagLayout());
                card.setPreferredSize(new Dimension(600, 660));

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

                JPanel requirementsPanel = createRequirementsPanel();

                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 2, 0);
                card.add(titleLabel, gbc);

                gbc.gridy = 1;
                gbc.insets = new Insets(0, 0, 10, 0);
                card.add(subtitleLabel, gbc);

                gbc.gridy = 2;
                gbc.insets = new Insets(0, 35, 12, 35);
                card.add(requirementsPanel, gbc);

                addField(card, gbc, 3, "Username", usernameField);
                addField(card, gbc, 5, "Password", passwordField);
                addField(card, gbc, 7, "Full name", fullNameField);
                addField(card, gbc, 9, "Phone number", phoneField);
                addField(card, gbc, 11, "Email", emailField);

                gbc.gridy = 13;
                gbc.insets = new Insets(0, 35, 3, 35);
                card.add(Style.fieldLabel("Role"), gbc);

                gbc.gridy = 14;
                gbc.insets = new Insets(0, 35, 12, 35);
                card.add(roleBox, gbc);

                JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 18, 0));
                buttonPanel.setBackground(Color.WHITE);

                registerButton.setPreferredSize(new Dimension(220, 48));
                cancelButton.setPreferredSize(new Dimension(220, 48));

                buttonPanel.add(registerButton);
                buttonPanel.add(cancelButton);

                gbc.gridy = 15;
                gbc.insets = new Insets(0, 35, 0, 35);
                card.add(buttonPanel, gbc);

                root.add(card);
                setContentPane(root);

                setLocationRelativeTo(null);
        }

        private JPanel createRequirementsPanel() {
                JPanel panel = new JPanel(new BorderLayout(0, 4));
                panel.setBackground(new Color(248, 250, 252));
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Style.BORDER),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));

                JLabel title = new JLabel("Registration requirements");
                title.setFont(new Font("Segoe UI", Font.BOLD, 13));
                title.setForeground(Style.DARK);

                JLabel text = new JLabel(
                        "<html>" +
                                "Username minimum: 3 characters<br>" +
                                "Password minimum: 6 characters<br>" +
                                "Email must contain @<br>" +
                                "Phone number may contain numbers, +, - and spaces<br>" +
                                "All fields must be filled in and username must be unique" +
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

        private void styleRegisterField(JTextField field) {
                field.setFont(Style.NORMAL_FONT);
                field.setForeground(Style.TEXT);
                field.setBackground(Color.WHITE);
                field.setCaretColor(Style.TEXT);

                field.setPreferredSize(new Dimension(500, 34));
                field.setMinimumSize(new Dimension(500, 34));

                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Style.BORDER, 1),
                        BorderFactory.createEmptyBorder(5, 12, 5, 12)
                ));
        }

        private void styleRegisterComboBox(JComboBox<String> comboBox) {
                comboBox.setFont(Style.NORMAL_FONT);
                comboBox.setForeground(Style.TEXT);
                comboBox.setBackground(Color.WHITE);

                comboBox.setPreferredSize(new Dimension(500, 34));
                comboBox.setMinimumSize(new Dimension(500, 34));
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
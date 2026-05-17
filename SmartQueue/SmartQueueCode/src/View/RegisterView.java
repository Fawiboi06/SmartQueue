package View;
import javax.swing.*;
import java.awt.GridLayout;

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

                setSize(400, 330);
                setLocationRelativeTo(parent);

                JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                panel.add(new JLabel("Username:"));
                usernameField = new JTextField();
                panel.add(usernameField);

                panel.add(new JLabel("Password:"));
                passwordField = new JPasswordField();
                panel.add(passwordField);

                panel.add(new JLabel("Full name:"));
                fullNameField = new JTextField();
                panel.add(fullNameField);

                panel.add(new JLabel("Phone number:"));
                phoneField = new JTextField();
                panel.add(phoneField);

                panel.add(new JLabel("Email:"));
                emailField = new JTextField();
                panel.add(emailField);

                panel.add(new JLabel("Role:"));
                roleBox = new JComboBox<>(new String[]{"Customer", "Admin"});
                panel.add(roleBox);

                registerButton = new JButton("Create account");
                cancelButton = new JButton("Cancel");

                panel.add(registerButton);
                panel.add(cancelButton);

                add(panel);
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

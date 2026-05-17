package View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        setTitle("Login");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        panel.add(loginButton);
        panel.add(registerButton);

        JLabel testLabel = new JLabel("Test: admin/admin or kund/123");
        panel.add(testLabel);

        add(panel);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}
package View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        Style.applyGlobalStyle();

        setTitle("SmartQueue Login");
        setSize(900, 720);
        setMinimumSize(new Dimension(800, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Style.BACKGROUND);

        JPanel card = Style.cardLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(700, 600));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel titleLabel = Style.title("SmartQueue");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = Style.smallText("Login to manage bookings and queues");
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel requirementsPanel = createLoginRequirementsPanel();

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        Style.styleTextField(usernameField);
        Style.styleTextField(passwordField);

        loginButton = Style.primaryButton("Login");
        registerButton = Style.secondaryButton("Register");

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 8, 0);
        card.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 18, 0);
        card.add(subtitleLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 45, 22, 45);
        card.add(requirementsPanel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 45, 6, 45);
        card.add(Style.fieldLabel("Username"), gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 45, 20, 45);
        card.add(usernameField, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 45, 6, 45);
        card.add(Style.fieldLabel("Password"), gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 45, 28, 45);
        card.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 45, 24, 45);
        card.add(buttonPanel, gbc);

        //JLabel testLabel = Style.smallText("Test: admin/admin123 or kund/123456");
        //testLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 8;
        gbc.insets = new Insets(0, 45, 0, 45);
       // card.add(testLabel, gbc);

        root.add(card);
        setContentPane(root);
    }

    private JPanel createLoginRequirementsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.BORDER),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel title = new JLabel("Login requirements");
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(Style.DARK);

        JLabel text = new JLabel(
                "<html>" +
                        "Username minimum: 3 characters<br>" +
                        "Password minimum: 6 characters" +
                        "</html>"
        );

        text.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        text.setForeground(Style.TEXT);

        panel.add(title, BorderLayout.NORTH);
        panel.add(text, BorderLayout.CENTER);

        return panel;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}
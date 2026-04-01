package View;
import java.swing.*;
import java.awt.*;
public class loginview extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    public LoginView(){
    setTitle("Login");
    setSize(300,200);
    setDefaultCloseOperation(JFrame.Exit_on_close);
    setLocationRelativeTo(null);
    JPanel panel=new JPanel(new GridLayout(3,2,10,10));

    panel.add(new JLabel("Username:"));
    usernameField=new JTextField();
    panel.add(usernameField);

    panel.add(new JLabel("Password:"));
    usernameField=new JTextField();
    panel.add(passwordField);

    loginButton=new JButton("Login");
    panel.add(new JLabel());
    panel.add(loginButton);

    add(panel);
    }
}

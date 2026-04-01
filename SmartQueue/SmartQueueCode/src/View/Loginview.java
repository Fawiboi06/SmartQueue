package View;
import javax.swing.*;
import java.awt.*;
public class Loginview extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Loginview(){
        setTitle("Login");
        setSize(300,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    public String getUsername(){
        return usernameField.getText();
    }
    public String getPassword(){
        return new String(passwordField.getPassword());
    }
    public JButton getLoginButton(){
        return loginButton;
    }
}

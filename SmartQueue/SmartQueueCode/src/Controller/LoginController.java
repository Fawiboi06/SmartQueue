package Controller;
import View.LoginView;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class LoginController {

    private LoginView view;

    public LoginController() {
        view = new LoginView();
        initController();
        view.setVisible(true);
    }
    private void initController(){
        view.getLoginButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                login();
            }
        });
    }

   private void login(){
        String username=view.getUsername();
        String password=view.getPassword();

        JOptionPane.showMessageDialog(view,"Login succesful");
        view.dispose();
        new ControllerAll(username);
    }
}

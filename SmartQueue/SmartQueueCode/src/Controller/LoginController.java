package Controller;
Import View.LoginView;
import javax.swing.*;
public class LoginController {

    private LoginView view;

    public LoginController(){
       view=new LoginView();
       initController();
       view.setVisible(true);
   }
   private void login(){
        String username=view.getUsername();
        String password=view.getPassword();
        if(username.equals("admin") && password.equals("1234")){
            JOptionPane.showMessageDialog(view,"Login succesful");
            view.dispose();
                    new ControllerAll();
        }
        else{
            JOptionPane.showMessageDialog(view."Wrong login");
        }
    }
}

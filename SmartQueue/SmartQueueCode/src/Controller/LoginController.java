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
}

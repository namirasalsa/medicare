package telkomedics;
import telkomedics.controller.LoginController;
import telkomedics.view.Login;

public class Medicare {
    public static void main(String[] args) {
        try {
            Login login = new Login();
            new LoginController(login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

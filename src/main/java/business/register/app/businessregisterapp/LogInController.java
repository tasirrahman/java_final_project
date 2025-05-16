package business.register.app.businessregisterapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LogInController {

    @FXML
    private TextField loginEmail;

    @FXML
    private TextField loginPassword;

    @FXML
    private MenuButton loginType;

    @FXML
    void loginButton(ActionEvent event) {

    }

    @FXML
    void navigateToForgotPassword(MouseEvent event) {
        HelloApplication.changeScreen("forgot-password-screen");
    }

    @FXML
    void navigateToRegister(MouseEvent event) {
       HelloApplication.changeScreen("register-screen");
    }

}

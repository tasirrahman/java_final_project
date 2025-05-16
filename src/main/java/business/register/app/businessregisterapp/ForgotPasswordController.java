package business.register.app.businessregisterapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ForgotPasswordController {

    @FXML
    private TextField resetEmail;

    @FXML
    private TextField resetPassword;

    @FXML
    private MenuButton resetType;

    @FXML
    void navigateToLogin(MouseEvent event) {
    HelloApplication.changeScreen("login-screen");
    }

    @FXML
    void resetButton(ActionEvent event) {

    }

}

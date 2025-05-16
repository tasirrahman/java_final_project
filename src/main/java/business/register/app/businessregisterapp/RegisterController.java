package business.register.app.businessregisterapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegisterController {

    @FXML
    private TextField registerEmail;

    @FXML
    private TextField registerPassword;

    @FXML
    private MenuButton registerType;

    @FXML
    void navigateToLogin(MouseEvent event) {
    HelloApplication.changeScreen("login-screen");
    }

    @FXML
    void registerButton(ActionEvent event) {
    }

}

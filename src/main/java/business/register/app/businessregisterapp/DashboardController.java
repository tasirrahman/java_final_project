package business.register.app.businessregisterapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    void navigateToLogin(ActionEvent event) {
   HelloApplication.changeScreen("login-screen");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSession session = UserSession.getInstance();
        if (session != null) {
            welcomeLabel.setText(session.getRole()+": " + session.getEmail());
        }
    }
}

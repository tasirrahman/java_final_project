package business.register.app.businessregisterapp;
import javafx.animation.PauseTransition;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
           HelloApplication.changeScreen("login-screen");
        });
        delay.play();
    }
}

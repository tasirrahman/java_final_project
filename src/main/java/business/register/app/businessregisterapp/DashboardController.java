package business.register.app.businessregisterapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSession session = UserSession.getInstance();
        if (session != null) {
            welcomeLabel.setText(session.getRole()+": " + session.getEmail());
        }
    }

    @FXML
    private Spinner<?> authorizedAmount;

    @FXML
    private TextField businessAddress;

    @FXML
    private TextField businessName;

    @FXML
    private SplitMenuButton businessType;

    @FXML
    private Button businesssFiles;

    @FXML
    private TextField ownerAddress;

    @FXML
    private Button ownerImage;

    @FXML
    private TextField ownerNID;

    @FXML
    private TextField ownerName;

    @FXML
    private TextField ownerTIN;

    @FXML
    private TextField searchBusiness;

    @FXML
    private TableView<?> showSearchedBusinessResults;

    @FXML
    private Label welcomeLabel;

    @FXML
    void navigateToLogin(ActionEvent event) {
      HelloApplication.changeScreen("login-screen");
    }

    @FXML
    void registerBusinessButtion(ActionEvent event) {

    }

    @FXML
    void viewStatus(ActionEvent event) {
    HelloApplication.changeScreen("business-status-screen");
    }

}

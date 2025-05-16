package business.register.app.businessregisterapp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.util.regex.Pattern;

public class LogInController {
    @FXML
    private TextField loginEmail;
    @FXML
    private TextField loginPassword;
    @FXML
    private ChoiceBox<String> loginType;
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_USER = "User";
    @FXML
    public void initialize() {
        loginType.getItems().addAll(ROLE_ADMIN, ROLE_USER);
        loginType.setValue(ROLE_USER);
    }
    @FXML
    void loginButton(ActionEvent event) {
        String email = loginEmail.getText().trim();
        String password = loginPassword.getText().trim();
        String selectedRole = loginType.getValue();

        if (email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Email is required.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid email format.");
            return;
        }

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password is required.");
            return;
        }

        if (!isStrongPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password must be at least 6 characters and include both letters and numbers.");
            return;
        }

        if (selectedRole == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Login type is required.");
            return;
        }

        boolean authenticated = authenticate(email, password, selectedRole);

        if (authenticated) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + selectedRole + "!");
            if (selectedRole.equals(ROLE_ADMIN)) {
                HelloApplication.changeScreen("admin-dashboard-screen");
            } else {
                HelloApplication.changeScreen("dashboard-screen");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials or role.");
        }
    }
    @FXML
    void navigateToForgotPassword(MouseEvent event) {
        HelloApplication.changeScreen("forgot-password-screen");
    }
    @FXML
    void navigateToRegister(MouseEvent event) {
        HelloApplication.changeScreen("register-screen");
    }

    private boolean authenticate(String email, String password, String role) {
        return (role.equals(ROLE_ADMIN) && email.equals("admin@example.com") && password.equals("admin123")) ||
                (role.equals(ROLE_USER) && email.equals("user@example.com") && password.equals("user123"));
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 6 && password.matches(".*[A-Za-z].*") && password.matches(".*\\d.*");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

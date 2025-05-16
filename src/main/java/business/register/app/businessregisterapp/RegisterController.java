package business.register.app.businessregisterapp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.regex.Pattern;

public class RegisterController {
    @FXML
    private TextField registerEmail;
    @FXML
    private TextField registerPassword;
    @FXML
    private ChoiceBox<String> registerType;
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_USER = "User";

    @FXML
    public void initialize() {
        registerType.getItems().addAll(ROLE_ADMIN, ROLE_USER);
        registerType.setValue(ROLE_USER);
    }

    @FXML
    void registerButton(ActionEvent event) {
        String email = registerEmail.getText().trim();
        String password = registerPassword.getText().trim();
        String selectedRole = registerType.getValue();

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
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Register type is required.");
            return;
        }

        boolean registered = register(email, password, selectedRole);

        if (registered) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Account created as " + selectedRole + ".");
            HelloApplication.changeScreen("login-screen");
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "User already exists or an error occurred.");
        }
    }

    @FXML
    void navigateToLogin(MouseEvent event) {
        HelloApplication.changeScreen("login-screen");
    }

    private boolean register(String email, String password, String role) {
        return !(email.equalsIgnoreCase("admin@example.com") || email.equalsIgnoreCase("user@example.com"));
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

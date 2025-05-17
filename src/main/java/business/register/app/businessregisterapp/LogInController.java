package business.register.app.businessregisterapp;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.util.regex.Pattern;
import javafx.scene.control.PasswordField;
import java.sql.*;

public class LogInController {
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private ChoiceBox<String> loginType;
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_USER = "User";
    private final Dotenv dotenv = Dotenv.load();

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

        if (selectedRole == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Login type is required.");
            return;
        }

        boolean authenticated = authenticate(email, password, selectedRole);

        if (authenticated) {

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
        try {
            String url = dotenv.get("DB_URL");
            String username = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");
            Connection connection = DriverManager.getConnection(
                    url,
                    username,
                    dbPassword
            );

            String query = "SELECT * FROM users WHERE email = ? AND password = ? AND role = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, role);

            ResultSet resultSet = statement.executeQuery();
            boolean userExists = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return userExists;

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Database error: " + e.getMessage());
            return false;
        }
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
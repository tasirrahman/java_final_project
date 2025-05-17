package business.register.app.businessregisterapp;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.sql.*;
import java.util.regex.Pattern;

public class ForgotPasswordController {

    @FXML
    private TextField resetEmail;

    @FXML
    private PasswordField resetPassword;

    @FXML
    private ChoiceBox<String> resetType;

    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_USER = "User";

    private final Dotenv dotenv = Dotenv.load();

    @FXML
    public void initialize() {
        resetType.getItems().addAll(ROLE_ADMIN, ROLE_USER);
        resetType.setValue(ROLE_USER);
    }


    @FXML
    void resetButton(ActionEvent event) {
        String email = resetEmail.getText().trim();
        String password = resetPassword.getText().trim();
        String selectedRole = resetType.getValue();

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
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a role.");
            return;
        }

        boolean resetSuccessful = resetPassword(email, password, selectedRole);

        if (resetSuccessful) {
            showAlert(Alert.AlertType.INFORMATION, "Password Reset Successful", "Password has been reset for " + selectedRole + ".");
            HelloApplication.changeScreen("login-screen");
        } else {
            showAlert(Alert.AlertType.ERROR, "Reset Failed", "Email not found or invalid role.");
        }
    }

    @FXML
    void navigateToLogin(MouseEvent event) {
        HelloApplication.changeScreen("login-screen");
    }

    private boolean resetPassword(String email, String password, String role) {
        try {
            String url = dotenv.get("DB_URL");
            String username = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");
            Connection connection = DriverManager.getConnection(
                    url,
                    username,
                    dbPassword
            );

            // First check if the user exists with the specified email and role
            String checkQuery = "SELECT * FROM users WHERE email = ? AND role = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, email);
            checkStatement.setString(2, role);

            ResultSet resultSet = checkStatement.executeQuery();
            boolean userExists = resultSet.next();

            resultSet.close();
            checkStatement.close();

            if (!userExists) {
                connection.close();
                return false;
            }

            // Update the user's password
            String updateQuery = "UPDATE users SET password = ? WHERE email = ? AND role = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, password);
            updateStatement.setString(2, email);
            updateStatement.setString(3, role);

            int rowsAffected = updateStatement.executeUpdate();
            updateStatement.close();
            connection.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Reset Error", "Database error: " + e.getMessage());
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
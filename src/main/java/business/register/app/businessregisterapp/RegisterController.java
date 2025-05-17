package business.register.app.businessregisterapp;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.PasswordField;
import java.sql.*;
import java.util.regex.Pattern;

public class RegisterController {
    @FXML
    private TextField registerEmail;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private ChoiceBox<String> registerType;
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_USER = "User";
    private final Dotenv dotenv = Dotenv.load();

    @FXML
    public void initialize() {
        registerType.getItems().addAll(ROLE_ADMIN, ROLE_USER);
        registerType.setValue(ROLE_USER);
        setupDatabase();
    }

    private void setupDatabase() {
        try {

            String username = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/", username, dbPassword);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS business_register_app_db");
            statement.executeUpdate("USE business_register_app_db");
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "email VARCHAR(255) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "role VARCHAR(50) NOT NULL)";
            statement.executeUpdate(createTableSQL);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to initialize database: " + e.getMessage());
        }
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



        try {
            String url = dotenv.get("DB_URL");
            String username = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");
            Connection connection = DriverManager.getConnection(url, username, dbPassword);

            String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, email);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "User already exists.");
                connection.close();
                return;
            }

            String insertQuery = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, email);
            insertStatement.setString(2, password); // Note: In a real application, the password should be hashed
            insertStatement.setString(3, selectedRole);

            int rowsAffected = insertStatement.executeUpdate();
            connection.close();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Account created as " + selectedRole + ".");
                HelloApplication.changeScreen("login-screen");
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Failed to create account.");
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Database error: " + e.getMessage());
        }
    }

    @FXML
    void navigateToLogin(MouseEvent event) {
        HelloApplication.changeScreen("login-screen");
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
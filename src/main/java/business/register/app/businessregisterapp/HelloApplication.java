package business.register.app.businessregisterapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("splash-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Business Register App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScreen(String fxml){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource( fxml+".fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

            stage.setScene(scene);
            stage.show();
        }  catch (IOException e) {
           System.out.print("HelloApplication Error");
        }
    }
}
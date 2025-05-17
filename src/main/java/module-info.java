module business.register.app.businessregisterapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;


    opens business.register.app.businessregisterapp to javafx.fxml;
    exports business.register.app.businessregisterapp;
}
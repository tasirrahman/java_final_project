module business.register.app.businessregisterapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens business.register.app.businessregisterapp to javafx.fxml;
    exports business.register.app.businessregisterapp;
}
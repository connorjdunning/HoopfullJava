module com.example.hoopfulljava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.hoopfulljava to javafx.fxml;
    exports com.example.hoopfulljava;
}
module com.example.codecity {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.codecity to javafx.fxml;
    exports com.example.codecity;
}
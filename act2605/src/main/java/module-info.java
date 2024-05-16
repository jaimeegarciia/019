module com.example.act2605 {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;


    opens com.example.act2605 to javafx.fxml;
    exports com.example.act2605;
}
module com.example.sauceproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sauceproject to javafx.fxml;
    exports com.example.sauceproject;
    exports com.example.sauceproject.controllers;
    opens com.example.sauceproject.controllers to javafx.fxml;
}
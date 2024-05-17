module com.example.sauceproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires javafx.web;


    opens com.example.sauceproject.ext to com.google.gson;
    exports com.example.sauceproject;
    opens com.example.sauceproject to com.google.gson, javafx.fxml;

}
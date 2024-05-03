module com.example.sauceproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;


    opens com.example.sauceproject to javafx.fxml;
    opens com.example.sauceproject.ext to com.google.gson;
    exports com.example.sauceproject;

}
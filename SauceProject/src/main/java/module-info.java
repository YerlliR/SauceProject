module com.example.sauceproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sauceproject to javafx.fxml;
    exports com.example.sauceproject;

}
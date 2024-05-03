package com.example.sauceproject;

import com.example.sauceproject.ext.ConexionABaseDeDatos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("fxml/inicio"), 1900, 1080);
        stage.setScene(scene);
        stage.setTitle("¡Sauce Wallet!");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }



    ConexionABaseDeDatos conexionABaseDeDatos = new ConexionABaseDeDatos();
    Connection conexion = conexionABaseDeDatos.conectar();
}
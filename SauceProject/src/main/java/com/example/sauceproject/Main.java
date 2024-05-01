package com.example.sauceproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/sauceproject/fxml/inicio.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("¡Sauce Wallet!");
        stage.setScene(scene);
        stage.show();
    }

    /*@Override
    public void start(Stage stage) throws IOException{
        scene = new Scene(loadFXML("/fxml/inicio"), 600,400);
        stage.setScene(scene);
        stage.show();
    }*/

    public static void setRoot(String fxml) throws IOException{
        scene = new Scene(loadFXML(fxml));
        System.out.println("juan");

    }

    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
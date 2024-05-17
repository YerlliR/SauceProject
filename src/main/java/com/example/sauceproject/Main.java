package com.example.sauceproject;
import com.example.sauceproject.ext.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


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


    static void abrirVentana(String fxml) throws IOException {
        Stage newStage = new Stage();
        Parent root = loadFXML(fxml);
        Scene newScene = new Scene(root);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(scene.getWindow());
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setScene(newScene);
        newStage.show();
    }


    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                jsonDowload.ejecut();
                baseDatosCrypt.magic();
            }
        }, 0, 5 * 60 * 1000);
        launch();
    }
}
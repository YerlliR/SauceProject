package com.example.sauceproject;

//import org.python.util.PythonInterpreter;
//import org.python.core.PyObject;
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



        /*PythonInterpreter interpreter = new PythonInterpreter();

        // Ejecutar el script de Python
        interpreter.exec("from mi_script import saludar");

        // Obtener una referencia a la función de Python
        PyObject saludarFunc = interpreter.get("saludar");

        // Llamar a la función de Python y obtener el resultado
        PyObject resultado = saludarFunc.__call__("Juan");*/
    }
}
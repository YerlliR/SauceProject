package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class loginController {

    public static String user;


    @FXML
    private TextField usu;
    @FXML
    private TextField pass;


    public void Iniciar() throws IOException, SQLException {

        Connection connection = conexionBaseDatos.conexion();
        user = usu.getText();
        String contra = pass.getText();


        try (connection) {
            String query = "SELECT * FROM Usuarios WHERE Usuario = ? AND Contraseña = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, contra);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Main.setRoot("fxml/principal");
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de inicio de sesión");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos. Por favor, inténtelo de nuevo.");
                alert.showAndWait();
            }
        }catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }

    }
    public void Volver() throws IOException{
        Main.setRoot("fxml/inicio");
    }

    public String getUser() {
        return user;
    }

}

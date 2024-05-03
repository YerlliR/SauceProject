package com.example.sauceproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class loginController {


    @FXML
    private TextField usu;
    @FXML
    private TextField pass;


    public void Iniciar() throws IOException {


        String url = "jdbc:mysql://localhost:3306/SaucerWallet";
        String usuario = "root";
        String contraseña = "123123";


        String user = usu.getText();
        String contra = pass.getText();


        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
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
}

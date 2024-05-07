package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SigninController {
    @FXML
    private TextField name;
    @FXML
    private TextField lastName;
    @FXML
    private TextField Correo;
    @FXML
    private TextField user;
    @FXML
    private TextField contrasenya;
    @FXML
    private TextField confirmContraseña;
    public void guardarDatoEnBaseDeDatos() throws IOException, SQLException {
        Connection connection = conexionBaseDatos.conexion();


        String Nombre = name.getText();
        String Apellidos = lastName.getText();
        String CorreoElectronico = Correo.getText();
        String Usuario = user.getText();
        String Contraseña = contrasenya.getText();
        String Contraseña2 = confirmContraseña.getText();

        if (Contraseña.equals(Contraseña2)){
            try (connection) {

                String sql = "INSERT INTO Usuarios (Nombre, Apellidos, CorreoElectronico, Usuario, Contraseña) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, Nombre);
                statement.setString(2, Apellidos);
                statement.setString(3, CorreoElectronico);
                statement.setString(4, Usuario);
                statement.setString(5, Contraseña);

                statement.executeUpdate();


                Main.setRoot("fxml/inicio");

            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Datos Incorrectos");
            alert.setHeaderText(null);
            alert.setContentText("La contraseña no coincide.");

            alert.showAndWait();

            Main.setRoot("fxml/signin");
        }
    }

    /*BOTONES*/

    public void Volver() throws IOException{
        Main.setRoot("fxml/inicio");
    }


}

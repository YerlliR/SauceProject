package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VenderController {

    @FXML
    private SplitMenuButton splitMenuButton;
    public void goToComprar() throws IOException {
        Main.setRoot("fxml/comprar");
    }

    public void goToVender() throws IOException {
        Main.setRoot("fxml/vender");
    }

    public void goToConvertir() throws IOException {
        Main.setRoot("fxml/convertir");
    }

    @FXML
    private void initialize() {
        // Manejar la selección de opciones del SplitMenuButton
        for (MenuItem item : splitMenuButton.getItems()) {
            item.setOnAction(event -> {
                splitMenuButton.setText(((MenuItem) event.getSource()).getText());
            });
        }

        // Obtener los nombres de las criptomonedas de la base de datos
        List<String> criptomonedas = obtenerNombresCriptomonedas();

        // Añadir las criptomonedas como MenuItem al SplitMenuButton
        for (String criptomoneda : criptomonedas) {
            MenuItem menuItem = new MenuItem(criptomoneda);
            menuItem.setOnAction(event -> {
                splitMenuButton.setText(((MenuItem) event.getSource()).getText());
            });
            splitMenuButton.getItems().add(menuItem);
        }
    }

    private List<String> obtenerNombresCriptomonedas() {
        List<String> nombresCriptomonedas = new ArrayList<>();


        String consultaSQL = "SELECT name FROM currencies ORDER BY cmc_rank ASC";

        try (Connection conn = conexionBaseDatos.conexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(consultaSQL)) {

            while (rs.next()) {
                nombresCriptomonedas.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener nombres de criptomonedas: " + e.getMessage());
        }

        return nombresCriptomonedas;
    }

    @FXML
    private void cancelar(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
        String usuario = loginController.user;
        System.out.println(loginController.user);
    }

    @FXML
    private TextField cantidadText;
    @FXML
    private TextField textFieldPrecioPorMoneda;
    @FXML
    private DatePicker datePicker;


    @FXML
    private void aceptar(javafx.event.ActionEvent event) {
        // Obtener los valores de los campos del formulario
        String criptomoneda = splitMenuButton.getText(); // Obtener el texto del SplitMenuButton
        double cantidad = Double.parseDouble(cantidadText.getText()); // Obtener la cantidad introducida
        double precioPorMoneda = Double.parseDouble(textFieldPrecioPorMoneda.getText()); // Obtener el precio por moneda introducido
        LocalDate fecha = datePicker.getValue(); // Obtener la fecha seleccionada

        // Construir la consulta SQL de inserción
        String consultaSQL = "UPDATE transacciones SET cantidadCryptomoneda = cantidadCryptomoneda - ?, precioTotal = precioTotal - ?, fechaDeTransaccionUsuario = ?, fechaDeTransaccion = ? " +
                "WHERE idUsuario = (SELECT id FROM Usuarios WHERE Usuario = ?) AND idCrypto = (SELECT id FROM currencies WHERE name = ?)";

        try (Connection conn = conexionBaseDatos.conexion();
             PreparedStatement pstmt = conn.prepareStatement(consultaSQL)) {
            // Obtener el nombre de usuario desde loginController
            String nombreUsuario = loginController.user;

            // Establecer los valores de los parámetros de la consulta SQL
            pstmt.setDouble(1, cantidad); // Resta la cantidad de criptomonedas
            pstmt.setDouble(2, cantidad * precioPorMoneda); // Resta el precio total (cantidad * precioPorMoneda)
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // Fecha de venta (actual)
            pstmt.setTimestamp(4, Timestamp.valueOf(fecha.atStartOfDay())); // Fecha de transacción (seleccionada)
            pstmt.setString(5, nombreUsuario); // Nombre de usuario
            pstmt.setString(6, criptomoneda); // Nombre de la criptomoneda

            // Ejecutar la consulta SQL de actualización
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Transacción de venta realizada correctamente.");
            } else {
                System.out.println("Error: No se encontraron criptomonedas suficientes para vender o el usuario no tiene la criptomoneda seleccionada.");
            }

        } catch (SQLException e) {
            System.err.println("Error al realizar la transacción de venta: " + e.getMessage());
        }
    }
}

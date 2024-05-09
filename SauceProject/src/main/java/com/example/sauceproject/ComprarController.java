package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComprarController  {

    @FXML
    private SplitMenuButton splitMenuButton;

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
        String consultaSQL = "INSERT INTO transacciones (idUsuario, idCrypto, precioTransaccion, fechaDeTransaccionUsuario, fechaDeTransaccion) " +
                "VALUES (?, (SELECT id FROM currencies WHERE name = ?), ?, ?, ?)";

        try (Connection conn = conexionBaseDatos.conexion();
             PreparedStatement pstmt = conn.prepareStatement(consultaSQL)) {
            // Obtener el nombre de usuario desde loginController
            String nombreUsuario = loginController.user;

            // Consulta SQL para obtener el ID del usuario utilizando el nombre de usuario
            String consultaIdUsuario = "SELECT id FROM Usuarios WHERE Usuario = ?";
            try (PreparedStatement pstmtIdUsuario = conn.prepareStatement(consultaIdUsuario)) {
                // Establecer el parámetro de la consulta SQL
                pstmtIdUsuario.setString(1, nombreUsuario);

                // Ejecutar la consulta SQL
                try (ResultSet rsIdUsuario = pstmtIdUsuario.executeQuery()) {
                    // Verificar si se encontró un resultado
                    if (rsIdUsuario.next()) {
                        // Obtener el ID del usuario
                        int idUsuario = rsIdUsuario.getInt("id");

                        // Asignar los valores a los parámetros de la consulta SQL
                        pstmt.setInt(1, idUsuario);
                        pstmt.setString(2, criptomoneda);
                        pstmt.setDouble(3, cantidad * precioPorMoneda); // Calcular el precio total
                        pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // Fecha de compra (actual)
                        pstmt.setTimestamp(5, Timestamp.valueOf(fecha.atStartOfDay())); // Fecha de transacción (seleccionada)

                        // Ejecutar la consulta SQL de inserción
                        pstmt.executeUpdate();
                        System.out.println("Transacción insertada correctamente.");
                    } else {
                        // Si no se encontró ningún usuario con ese nombre, mostrar un mensaje de error o manejar la situación de alguna otra manera
                        System.err.println("No se encontró ningún usuario con el nombre de usuario: " + nombreUsuario);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar la transacción: " + e.getMessage());
        }
    }



}
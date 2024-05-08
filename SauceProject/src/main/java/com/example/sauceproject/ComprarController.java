package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
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
    }

    @FXML
    private void aceptar(javafx.event.ActionEvent event) {
        System.out.println("Aceptar");
    }

}
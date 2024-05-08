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

   /* @Override
    public void start(Stage primaryStage) throws SQLException {
        List<String> criptomonedas = new ArrayList<>();

        // Create a connection to the database
        Connection conn = conexionBaseDatos.conexion();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM currencies ORDER BY cmc_rank ASC");

        // Add each currency to the list
        while (rs.next()) {
            criptomonedas.add(rs.getString("name"));
        }

        // Close the database connection
        rs.close();
        stmt.close();
        conn.close();

        SplitMenuButton currencyMenu = new SplitMenuButton();
        currencyMenu.setText("Select Currency");

        for (String currency : criptomonedas) {
            MenuItem item = new MenuItem(currency);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    System.out.println("Selected currency: " + currency);
                }
            });
            currencyMenu.getItems().add(item);
        }

        VBox root = new VBox();
        root.getChildren().add(currencyMenu);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Currency Selector");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    */













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
package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GraficasController implements Initializable {

    @FXML
    private WebView webView;

    @FXML
    private TableView<Dato> tableView;

    @FXML
    private TableColumn<Dato, String> nameColumn;

    @FXML
    private TableColumn<Dato, Double> percentChange24hColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obtener el motor de renderizado web del WebView
        WebEngine webEngine = webView.getEngine();


        webEngine.load("https://es.tradingview.com/chart/?symbol=BINANCE%3ABTCUSDT");
        // Configurar las columnas de la TableView para que se correspondan con las propiedades de la clase Dato
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        percentChange24hColumn.setCellValueFactory(new PropertyValueFactory<>("percentChange24h"));

        // Establecer el evento de clic en la TableView
        tableView.setOnMouseClicked(this::handleRowClick);

        // Obtener los datos de la base de datos y mostrarlos en la TableView
        try {
            Connection connection = conexionBaseDatos.conexion();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT cg.symbol, cg.link, c.percent_change_24h " +
                            "FROM webViewrGraph cg " +
                            "JOIN currencies c ON cg.id = c.id AND cg.symbol = c.symbol " +
                            "ORDER BY cg.cmc_rank " +
                            "LIMIT 30"
            );

            while (resultSet.next()) {
                String name = resultSet.getString("symbol");
                double percentChange24h = resultSet.getDouble("percent_change_24h");
                String link = resultSet.getString("link");

                // Crear un objeto Dato con los datos obtenidos de la base de datos
                Dato dato = new Dato(name, percentChange24h, link);

                // Agregar el dato a la TableView
                tableView.getItems().add(dato);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para manejar el clic en una fila de la TableView
    private void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            Dato selectedDato = tableView.getSelectionModel().getSelectedItem();
            if (selectedDato != null && selectedDato.getLink() != null && !selectedDato.getLink().isEmpty()) {
                webView.getEngine().load(selectedDato.getLink());
            }
        }
    }

    public void goToPrincipal() throws IOException {
        Main.setRoot("fxml/principal");
    }

    public void goToGraficas() throws IOException {
        Main.setRoot("fxml/graficas");
    }

    public void goToMercado() throws IOException {
        Main.setRoot("fxml/mercado");
    }

    public void goToEarn() throws IOException {
        Main.setRoot("fxml/stake");
    }

    public void goToCartera() throws IOException {
        Main.setRoot("fxml/cartera");
    }
}

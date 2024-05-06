package com.example.sauceproject;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MercadoController implements Initializable {

    public void goToPrincipal() throws IOException {
        Main.setRoot("fxml/principal");
    }

    public void goToComprar() throws IOException {
        Main.setRoot("fxml/mercado");
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


    @FXML
    private TableView<Currency> tableView;

    @FXML
    private TableColumn<Currency, Integer> cmcRankColumn;
    @FXML
    private TableColumn<Currency, String> nameColumn;

    @FXML
    private TableColumn<Currency, String> symbolColumn;

    @FXML
    private TableColumn<Currency, Double> priceColumn;

    @FXML
    private TableColumn<Currency, Double> percentChangeColumn;

    @FXML
    private TableColumn<Currency, Double> marketCapColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        cmcRankColumn.setCellValueFactory(cellData -> cellData.getValue().cmcRankProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        symbolColumn.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        percentChangeColumn.setCellValueFactory(cellData -> cellData.getValue().percentChangeProperty().asObject());
        marketCapColumn.setCellValueFactory(cellData -> cellData.getValue().marketCapProperty().asObject());

        cargarDatos();
    }


    private void cargarDatos() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SaucerWallet", "root", "123123");
                    Statement statement = connection.createStatement();


                    ResultSet resultSet = statement.executeQuery("SELECT " +
                            "id, " +
                            "name, " +
                            "symbol, " +
                            "cmc_rank, " +
                            "CASE " +
                            "WHEN price >= 1 THEN ROUND(price, 2) " +
                            "WHEN price <= 1 AND price >= 0.1 THEN ROUND(price, 4) " +
                            "WHEN price <= 0.1 AND price >= 0.01 THEN ROUND(price, 5) " +
                            "WHEN price <= 0.01 AND price >= 0.001 THEN ROUND(price, 6) " +
                            "WHEN price <= 0.001 AND price >= 0.0001 THEN ROUND(price, 7) " +
                            "WHEN price <= 0.0001 AND price >= 0.00001 THEN ROUND(price, 8) " +
                            "WHEN price <= 0.00001 AND price >= 0.000001 THEN ROUND(price, 9) " +
                            "ELSE ROUND(price, 20) " +
                            "END AS price, " +
                            "percent_change_24h, " +
                            "market_cap " +
                            "FROM currencies " +
                            "ORDER BY cmc_rank");


                    // Limpiar la TableView antes de agregar nuevos datos
                    tableView.getItems().clear();

                    // Formato para los valores de precio con 2 o 10 decimales
                    DecimalFormat df = new DecimalFormat("#.##");
                    DecimalFormat df10 = new DecimalFormat("#.##########");

                    // Obtener los datos de la consulta y agregarlos a la TableView
                    while (resultSet.next()) {
                        int cmc_rank = resultSet.getInt("cmc_rank");
                        String name = resultSet.getString("name");
                        String symbol = resultSet.getString("symbol");
                        double price = resultSet.getDouble("price");
                        double percentChange = resultSet.getDouble("percent_change_24h");
                        double marketCap = resultSet.getDouble("market_cap");

                        // Formatear el valor de price según las condiciones
                        String formattedPrice;
                        if (price >= 1) {
                            formattedPrice = df.format(price);
                        } else {
                            formattedPrice = df10.format(price);
                        }

                        // Reemplazar la coma por un punto si es necesario
                        formattedPrice = formattedPrice.replace(",", ".");
                        tableView.getItems().add(new Currency(cmc_rank, name, symbol, Double.parseDouble(formattedPrice), percentChange, marketCap));
                    }

                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60 * 1000);
    }
}



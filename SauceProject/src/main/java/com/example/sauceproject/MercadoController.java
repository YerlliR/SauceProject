package com.example.sauceproject;


import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

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
    void accion(MouseEvent event) throws IOException {
        Main.abrirVentana("fxml/operacion");
    }

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

        // Format price and market cap columns
        priceColumn.setCellFactory(tc -> new TableCell<Currency, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    DecimalFormat df = new DecimalFormat();
                    if (price >= 1) {
                        df.setMaximumFractionDigits(2);
                    } else if (price <= 1 && price >= 0.1) {
                        df.setMaximumFractionDigits(4);
                    } else if (price <= 0.1 && price >= 0.01) {
                        df.setMaximumFractionDigits(5);
                    } else if (price <= 0.01 && price >= 0.001) {
                        df.setMaximumFractionDigits(6);
                    } else if (price <= 0.001 && price >= 0.0001) {
                        df.setMaximumFractionDigits(7);
                    } else if (price <= 0.0001 && price >= 0.00001) {
                        df.setMaximumFractionDigits(8);
                    } else if (price <= 0.00001 && price >= 0.000001) {
                        df.setMaximumFractionDigits(9);
                    } else {
                        df.setMaximumFractionDigits(20);
                    }
                    setText(df.format(price));
                }
            }
        });

        marketCapColumn.setCellFactory(tc -> new TableCell<Currency, Double>() {
            @Override
            protected void updateItem(Double marketCap, boolean empty) {
                super.updateItem(marketCap, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%,.0f", marketCap));
                }
            }
        });

        cargarDatos();
    }


    private void cargarDatos() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Connection connection = conexionBaseDatos.conexion();
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
                            "WHEN price<= 0.001 AND price >= 0.0001 THEN ROUND(price, 7) " +
                            "WHEN price <= 0.0001 AND price >= 0.00001 THEN ROUND(price, 8) " +
                            "WHEN price <= 0.00001 AND price >= 0.000001 THEN ROUND(price, 9) " +
                            "ELSE ROUND(price, 20) " +
                            "END AS price, " +
                            "percent_change_24h, " +
                            "market_cap " +
                            "FROM currencies " +
                            "ORDER BY cmc_rank");


                    tableView.getItems().clear();

                    while (resultSet.next()) {
                        int cmc_rank = resultSet.getInt("cmc_rank");
                        String name = resultSet.getString("name");
                        String symbol = resultSet.getString("symbol");
                        double price = resultSet.getDouble("price");
                        double percentChange = resultSet.getDouble("percent_change_24h");
                        double marketCap = resultSet.getDouble("market_cap");

                        tableView.getItems().add(new Currency(cmc_rank, name, symbol, price, percentChange, marketCap));
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
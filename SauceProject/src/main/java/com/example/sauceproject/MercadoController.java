package com.example.sauceproject;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    public void goToMercado() throws IOException{
        Main.setRoot("fxml/mercado");
    }
    public void goToEarn() throws IOException {
        Main.setRoot("fxml/stake");
    }
    public void goToCartera() throws IOException{
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
    public void initialize(URL location, ResourceBundle resources){


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
                    // Establecer conexión con la base de datos
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SaucerWallet", "root", "123123");
                    Statement statement = connection.createStatement();

                    // Ejecutar la consulta SQL
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM currencies ORDER BY market_cap DESC");

                    // Limpiar la TableView antes de agregar nuevos datos
                    tableView.getItems().clear();

                    // Obtener los datos de la consulta y agregarlos a la TableView
                    while (resultSet.next()) {
                        int cmc_rank = resultSet.getInt("cmc_rank");
                        String name = resultSet.getString("name");
                        String symbol = resultSet.getString("symbol");
                        double price = resultSet.getDouble("price");
                        double percentChange = resultSet.getDouble("percent_change_24h");
                        double marketCap = resultSet.getDouble("market_cap");


                        //NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
                        //String precioConSimbolo = formatoMoneda.format(price);

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


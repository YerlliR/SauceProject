package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class CarteraController implements Initializable {
    public void goToPrincipal() throws IOException {
        Main.setRoot("fxml/principal");
    }
    public void goToGraficas() throws IOException {
        Main.setRoot("fxml/graficas");
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
    private TableView<Currency2> tableView;

    @FXML
    private TableColumn<Currency2, String> nombre;

    @FXML
    private TableColumn<Currency2, String> simbolo;

    @FXML
    private TableColumn<Currency2, Double> precio;

    @FXML
    private TableColumn<Currency2, Double> rentabilidad;

    @FXML
    private TableColumn<Currency2, Double> tenencias;

    @FXML
    private TableColumn<Currency2, Double> perdidasGanancias;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        simbolo.setCellValueFactory(cellData -> cellData.getValue().simboloProperty());
        precio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        rentabilidad.setCellValueFactory(cellData -> cellData.getValue().rentabilidadProperty().asObject());
        tenencias.setCellValueFactory(cellData -> cellData.getValue().tenenciasProperty().asObject());
        perdidasGanancias.setCellValueFactory(cellData -> cellData.getValue().perdidasGananciasProperty().asObject());

        // Format price and market cap columns
        precio.setCellFactory(tc -> new TableCell<Currency2, Double>() {
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
                    setText( df.format(price) + " $"); // Agrega el símbolo del dólar
                }
            }
        });

        perdidasGanancias.setCellFactory(tc -> new TableCell<Currency2, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    setText(df.format(value) + " $"); // Agrega el símbolo del dólar
                }
            }
        });

        rentabilidad.setCellFactory(column -> new TableCell<Currency2, Double>() {
            @Override
            protected void updateItem(Double percentChange, boolean empty) {
                super.updateItem(percentChange, empty);

                if (empty || percentChange == null) {
                    setText(null);
                    setStyle("");
                } else {
                    DecimalFormat df = new DecimalFormat("#.##"); // Define el formato para la rentabilidad
                    setText(df.format(percentChange) + " %"); // Agrega el símbolo del porcentaje
                    if (percentChange > 0) {
                        setStyle("-fx-text-fill: green;");
                    } else if (percentChange < 0) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });


        perdidasGanancias.setCellFactory(column -> new TableCell<Currency2, Double>() {
            @Override
            protected void updateItem(Double perdidasGanancias, boolean empty) {
                super.updateItem(perdidasGanancias, empty);

                if (empty || perdidasGanancias == null) {
                    setText(null);
                    setStyle("");
                } else {
                    DecimalFormat df = new DecimalFormat("#.##"); // Define el formato para la rentabilidad
                    setText(df.format(perdidasGanancias) + " $  "); // Agrega el símbolo del porcentaje
                    if (perdidasGanancias > 0) {
                        setStyle("-fx-text-fill: green;");
                    } else if (perdidasGanancias < 0) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });


        tenencias.setCellFactory(tc -> new TableCell<Currency2, Double>() {
            @Override
            protected void updateItem(Double tenencia, boolean empty) {
                super.updateItem(tenencia, empty);
                if (empty || tenencia == null) {
                    setText(null);
                } else {
                    // Obtener el objeto Currency2 asociado a esta fila
                    Currency2 currency = getTableView().getItems().get(getIndex());
                    if (currency != null) {
                        DecimalFormat df = new DecimalFormat("#,##0.00");
                        setText(df.format(tenencia) + " " + currency.getSimbolo());
                    } else {
                        setText(null);
                    }
                }
            }
        });



        cargarDatos();
    }

    private void cargarDatos() {
        // Crear un nuevo hilo para cargar los datos
        new Thread(() -> {
            try {
                Connection connection = conexionBaseDatos.conexion();
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT " +
                        "c.name AS Nombre_Criptomoneda, " +
                        "c.symbol AS Símbolo, " +
                        "c.price AS Precio_Actual, " +
                        "c.percent_change_24h AS Cambio_Porcentual_24h, " +
                        "SUM(t.cantidadCryptomoneda) AS Cantidad_Total, " +
                        "((c.price - AVG(t.precioPorCriptomoneda)) / AVG(t.precioPorCriptomoneda)) * 100 AS Rentabilidad, " +
                        "SUM(t.cantidadCryptomoneda * (c.price - t.precioPorCriptomoneda)) AS Perdidas_Ganancias " +
                        "FROM " +
                        "Usuarios u " +
                        "JOIN " +
                        "transacciones t ON u.id = t.idUsuario " +
                        "JOIN " +
                        "currencies c ON t.idCrypto = c.id " +
                        "WHERE " +
                        "u.id = '1' " +
                        "GROUP BY " +
                        "c.id, c.name, c.symbol, c.price, c.percent_change_24h;"
                );


                tableView.getItems().clear();

                while (resultSet.next()) {
                    String Nombre_Criptomoneda = resultSet.getString("Nombre_Criptomoneda");
                    String Símbolo = resultSet.getString("Símbolo");
                    double Precio_Actual = resultSet.getDouble("Precio_Actual");
                    double Cambio_Porcentual_24h = resultSet.getDouble("Cambio_Porcentual_24h");
                    double Cantidad_Total = resultSet.getDouble("Cantidad_Total");
                    double Perdidas_Ganancias = resultSet.getDouble("Perdidas_Ganancias");

                    tableView.getItems().add(new Currency2(Nombre_Criptomoneda, Símbolo, Precio_Actual, Cambio_Porcentual_24h, Cantidad_Total, Perdidas_Ganancias));
                }

                resultSet.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }
}


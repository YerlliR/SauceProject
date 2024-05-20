package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class CarteraController implements Initializable {
    String usuario = loginController.user;

    @FXML
    private Label saldoTotalLabel; // Nueva etiqueta para mostrar el saldo total

    @FXML
    private TableView<Currency2> tableView;

    @FXML
    private TableColumn<Currency2, String> nombre;

    @FXML
    private TableColumn<Currency2, String> simbolo;

    @FXML
    private TableColumn<Currency2, Double> precio;

    @FXML
    private TableColumn<Currency2, Double> cambio24h;

    @FXML
    private TableColumn<Currency2, Double> tenencias;

    @FXML
    private TableColumn<Currency2, Double> rentabilidad;

    @FXML
    private TableColumn<Currency2, Double> perdidasGanancias;

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

    public void goToComprar() throws IOException {
        Main.abrirVentana("fxml/comprar");
    }

    public void goToVender() throws IOException {
        Main.abrirVentana("fxml/vender");
    }

    public void goToConvertir() throws IOException {
        Main.abrirVentana("fxml/convertir");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombre.setCellValueFactory(cellData -> cellData.getValue().nombre_CriptomonedaProperty());
        simbolo.setCellValueFactory(cellData -> cellData.getValue().símboloProperty());
        precio.setCellValueFactory(cellData -> cellData.getValue().precio_ActualProperty().asObject());
        cambio24h.setCellValueFactory(cellData -> cellData.getValue().cambio_Porcentual_24hProperty().asObject());
        tenencias.setCellValueFactory(cellData -> cellData.getValue().cantidad_TotalProperty().asObject());
        rentabilidad.setCellValueFactory(cellData -> cellData.getValue().rentabilidadProperty().asObject());
        perdidasGanancias.setCellValueFactory(cellData -> cellData.getValue().perdidas_GananciasProperty().asObject());

        // Formatear las columnas de precio y pérdidas/ganancias
        formatPriceColumn(precio);
        formatProfitLossColumn(perdidasGanancias);
        formatTenenciasColumn(tenencias);
        formatRentabilidadColumn(rentabilidad);

        // Cargar datos y actualizar el saldo total
        cargarDatos();
    }

    private void formatPriceColumn(TableColumn<Currency2, Double> column) {
        column.setCellFactory(tc -> new TableCell<Currency2, Double>() {
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
                    setText(df.format(price) + " $");
                }
            }
        });
    }

    private void formatProfitLossColumn(TableColumn<Currency2, Double> column) {
        column.setCellFactory(tc -> new TableCell<Currency2, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    setText(df.format(value) + " $");
                }
            }
        });
    }

    private void formatTenenciasColumn(TableColumn<Currency2, Double> column) {
        column.setCellFactory(tc -> new TableCell<Currency2, Double>() {
            @Override
            protected void updateItem(Double tenencia, boolean empty) {
                super.updateItem(tenencia, empty);
                if (empty || tenencia == null) {
                    setText(null);
                } else {
                    Currency2 currency = getTableView().getItems().get(getIndex());
                    if (currency != null) {
                        DecimalFormat df = new DecimalFormat("#,##0.00");
                        setText(df.format(tenencia) + " " + currency.getSímbolo());
                    } else {
                        setText(null);
                    }
                }
            }
        });
    }

    private void formatRentabilidadColumn(TableColumn<Currency2, Double> column) {
        column.setCellFactory(tc -> new TableCell<Currency2, Double>() {
            @Override
            protected void updateItem(Double percentChange, boolean empty) {
                super.updateItem(percentChange, empty);
                if (empty || percentChange == null) {
                    setText(null);
                    setStyle("");
                } else {
                    DecimalFormat df = new DecimalFormat("#.##");
                    setText(df.format(percentChange) + " %");
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
    }

    private void cargarDatos() {
        new Thread(() -> {
            try {
                Connection connection = conexionBaseDatos.conexion();
                Statement statement = connection.createStatement();

                String getUserIdQuery = "SELECT id FROM Usuarios WHERE Usuario = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(getUserIdQuery);
                preparedStatement.setString(1, usuario);
                ResultSet userResultSet = preparedStatement.executeQuery();

                int userId = -1;
                if (userResultSet.next()) {
                    userId = userResultSet.getInt("id");
                }

                userResultSet.close();
                preparedStatement.close();

                if (userId != -1) {
                    String query = "SELECT " +
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
                            "u.id = ? " +
                            "GROUP BY " +
                            "c.id, c.name, c.symbol, c.price, c.percent_change_24h;";

                    PreparedStatement statement2 = connection.prepareStatement(query);
                    statement2.setInt(1, userId);
                    ResultSet resultSet = statement2.executeQuery();

                    tableView.getItems().clear();

                    double totalSaldo = 0;

                    while (resultSet.next()) {
                        String Nombre_Criptomoneda = resultSet.getString("Nombre_Criptomoneda");
                        String Símbolo = resultSet.getString("Símbolo");
                        double Precio_Actual = resultSet.getDouble("Precio_Actual");
                        double Cambio_Porcentual_24h = resultSet.getDouble("Cambio_Porcentual_24h");
                        double Cantidad_Total = resultSet.getDouble("Cantidad_Total");
                        double Rentabilidad = resultSet.getDouble("Rentabilidad");
                        double Perdidas_Ganancias = resultSet.getDouble("Perdidas_Ganancias");

                        // Calcular el saldo total en USD
                        totalSaldo += Cantidad_Total * Precio_Actual;

                        tableView.getItems().add(new Currency2(Nombre_Criptomoneda, Símbolo, Precio_Actual, Cambio_Porcentual_24h, Cantidad_Total, Rentabilidad, Perdidas_Ganancias));
                    }

                    resultSet.close();
                    statement2.close();

                    double finalTotalSaldo = totalSaldo;
                    javafx.application.Platform.runLater(() -> {
                        DecimalFormat df = new DecimalFormat("#,##0.00");
                        saldoTotalLabel.setText(df.format(finalTotalSaldo) + " $");
                    });
                }

                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

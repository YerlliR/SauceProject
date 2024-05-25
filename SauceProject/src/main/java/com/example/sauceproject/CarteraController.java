package com.example.sauceproject;

import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    private Label beneficioTotalLabel; // Nueva etiqueta para mostrar el beneficio total

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

    @FXML
    private Button eliminarTransaccionesButton;

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
        formatearColumnaPrecio(precio);
        formatearColumnaMarketCap(perdidasGanancias);
        formatearColumnaTenencias(tenencias);
        formatearColumnaRentabilidad(rentabilidad);

        // Cargar datos y actualizar el saldo total y el beneficio total
        cargarDatos();
    }

    private void formatearColumnaPrecio(TableColumn<Currency2, Double> columna) {
        columna.setCellFactory(tc -> new TableCell<Currency2, Double>() {
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

    private void formatearColumnaMarketCap(TableColumn<Currency2, Double> column) {
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

    private void formatearColumnaTenencias(TableColumn<Currency2, Double> column) {
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

    private void formatearColumnaRentabilidad(TableColumn<Currency2, Double> column) {
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

                String queryUsuarioId = "SELECT id FROM Usuarios WHERE Usuario = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(queryUsuarioId);
                preparedStatement.setString(1, usuario);
                ResultSet userResultSet = preparedStatement.executeQuery();

                int usuarioId = -1;
                if (userResultSet.next()) {
                    usuarioId = userResultSet.getInt("id");
                }

                userResultSet.close();
                preparedStatement.close();

                if (usuarioId != -1) {
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
                    statement2.setInt(1, usuarioId);
                    ResultSet resultSet = statement2.executeQuery();

                    tableView.getItems().clear();

                    double totalSaldo = 0;
                    double totalBeneficio = 0; // Variable para almacenar el beneficio total

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

                        // Calcular el beneficio total
                        totalBeneficio += Perdidas_Ganancias;

                        tableView.getItems().add(new Currency2(Nombre_Criptomoneda, Símbolo, Precio_Actual, Cambio_Porcentual_24h, Cantidad_Total, Rentabilidad, Perdidas_Ganancias));
                    }

                    resultSet.close();
                    statement2.close();

                    double finalTotalSaldo = totalSaldo;
                    double finalTotalBeneficio = totalBeneficio;
                    javafx.application.Platform.runLater(() -> {
                        DecimalFormat df = new DecimalFormat("#,##0.00");
                        saldoTotalLabel.setText(df.format(finalTotalSaldo) + " $");
                        beneficioTotalLabel.setText(df.format(finalTotalBeneficio) + " $");
                    });
                }

                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void eliminarTransacciones() {
        new Thread(() -> {
            try {
                Connection connection = conexionBaseDatos.conexion();
                String usuarioIdQuery = "SELECT id FROM Usuarios WHERE Usuario = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(usuarioIdQuery);
                preparedStatement.setString(1, usuario);
                ResultSet userResultSet = preparedStatement.executeQuery();

                int usuarioId = -1;
                if (userResultSet.next()) {
                    usuarioId = userResultSet.getInt("id");
                }

                userResultSet.close();
                preparedStatement.close();

                if (usuarioId != -1) {
                    String deleteQuery = "DELETE FROM transacciones WHERE idUsuario = ?";
                    PreparedStatement eliminarStatement = connection.prepareStatement(deleteQuery);
                    eliminarStatement.setInt(1, usuarioId);
                    eliminarStatement.executeUpdate();
                    eliminarStatement.close();

                    javafx.application.Platform.runLater(() -> {
                        tableView.getItems().clear();
                        saldoTotalLabel.setText("0.00 $");
                        beneficioTotalLabel.setText("0.00 $");
                    });
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }
}


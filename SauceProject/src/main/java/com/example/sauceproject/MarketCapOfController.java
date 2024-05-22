package com.example.sauceproject;

import java.io.IOException;
import java.sql.*;

import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class MarketCapOfController {
    @FXML
    private Label saldoTotalLabel;

    @FXML
    private ComboBox<String> cryptoLeftComboBox;

    @FXML
    private ComboBox<String> cryptoRightComboBox;

    @FXML
    private Label resultLabel;

    String usuario = loginController.user;

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

    public double getSaldoTotal() {
        double saldoTotal = 0.0;
        String query = "SELECT SUM(t.cantidadCryptomoneda * c.price) AS saldoTotal " +
                "FROM Usuarios u " +
                "JOIN transacciones t ON u.id = t.idUsuario " +
                "JOIN currencies c ON t.idCrypto = c.id " +
                "WHERE u.Usuario = ?";

        try (Connection conn = conexionBaseDatos.conexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                saldoTotal = rs.getDouble("saldoTotal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return saldoTotal;
    }

    @FXML
    public void initialize() {
        double saldoTotal = getSaldoTotal();
        saldoTotalLabel.setText(String.format("%.2f $", saldoTotal));
        populateCryptoComboBoxes();
    }

    private void populateCryptoComboBoxes() {
        ObservableList<String> cryptoList = FXCollections.observableArrayList();
        String query = "SELECT symbol FROM currencies";

        try (Connection conn = conexionBaseDatos.conexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                cryptoList.add(rs.getString("symbol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cryptoLeftComboBox.setItems(cryptoList);
        cryptoRightComboBox.setItems(cryptoList);

        cryptoLeftComboBox.setOnAction(event -> calculatePriceWithMarketCap());
        cryptoRightComboBox.setOnAction(event -> calculatePriceWithMarketCap());
    }

    private void calculatePriceWithMarketCap() {
        String leftCrypto = cryptoLeftComboBox.getValue();
        String rightCrypto = cryptoRightComboBox.getValue();

        if (leftCrypto != null && rightCrypto != null) {
            String query = "SELECT c1.price AS leftPrice, c1.market_cap AS leftMarketCap, " +
                    "c2.market_cap AS rightMarketCap " +
                    "FROM currencies c1, currencies c2 " +
                    "WHERE c1.symbol = ? AND c2.symbol = ?";

            try (Connection conn = conexionBaseDatos.conexion();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, leftCrypto);
                pstmt.setString(2, rightCrypto);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    double leftPrice = rs.getDouble("leftPrice");
                    double leftMarketCap = rs.getDouble("leftMarketCap");
                    double rightMarketCap = rs.getDouble("rightMarketCap");

                    double newPrice = leftPrice * (rightMarketCap / leftMarketCap);
                    resultLabel.setText(String.format("%.2f $", newPrice));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

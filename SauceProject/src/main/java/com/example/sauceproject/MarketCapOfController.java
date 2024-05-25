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
        selecccionDeCasillaComboBox();
    }

    private void selecccionDeCasillaComboBox() {
        ObservableList<String> listaCrypt = FXCollections.observableArrayList();
        String query = "SELECT symbol FROM currencies";

        try (Connection conn = conexionBaseDatos.conexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                listaCrypt.add(rs.getString("symbol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cryptoLeftComboBox.setItems(listaCrypt);
        cryptoRightComboBox.setItems(listaCrypt);

        cryptoLeftComboBox.setOnAction(event -> calculadorPrecios());
        cryptoRightComboBox.setOnAction(event -> calculadorPrecios());
    }

    private void calculadorPrecios() {
        String cryptoA = cryptoLeftComboBox.getValue();
        String cryptoB = cryptoRightComboBox.getValue();

        if (cryptoA != null && cryptoB != null) {
            String query = "SELECT c1.price AS leftPrice, c1.market_cap AS leftMarketCap, " +
                    "c2.market_cap AS rightMarketCap " +
                    "FROM currencies c1, currencies c2 " +
                    "WHERE c1.symbol = ? AND c2.symbol = ?";

            try (Connection conn = conexionBaseDatos.conexion();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, cryptoA);
                pstmt.setString(2, cryptoB);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    double precioMonedaA = rs.getDouble("leftPrice");
                    double marketCapA = rs.getDouble("leftMarketCap");
                    double marcketCapB = rs.getDouble("rightMarketCap");

                    double precioSimulado = precioMonedaA * (marcketCapB / marketCapA);
                    resultLabel.setText(String.format("%.2f $", precioSimulado));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

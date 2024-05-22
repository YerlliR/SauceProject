package com.example.sauceproject;

import java.io.IOException;
import java.sql.*;

import com.example.sauceproject.ext.conexionBaseDatos;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrincipalController {
    @FXML
    private Label saldoTotalLabel;

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
    }
}

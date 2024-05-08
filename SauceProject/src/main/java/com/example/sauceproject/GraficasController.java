package com.example.sauceproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GraficasController implements Initializable {


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





    @FXML
    private WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obtener el motor de renderizado web del WebView
        WebEngine webEngine = webView.getEngine();

        // Cargar una URL en el WebView
        webEngine.load("https://es.tradingview.com/chart/?symbol=BINANCE%3ABTCUSDT");
    }
}

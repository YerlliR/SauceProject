package com.example.sauceproject;

import java.io.IOException;

public class PrincipalController {


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
}

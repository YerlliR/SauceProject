package com.example.sauceproject;

import java.io.IOException;

public class StakeController {

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


}

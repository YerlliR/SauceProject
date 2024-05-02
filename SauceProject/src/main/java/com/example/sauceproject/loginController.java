package com.example.sauceproject;

import java.io.IOException;

public class loginController {
    public void Iniciar() throws IOException {
        Main.setRoot("fxml/principal");
    }
    public void Volver() throws IOException{
        Main.setRoot("fxml/inicio");
    }
}

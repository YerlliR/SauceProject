package com.example.sauceproject.controllers;

import com.example.sauceproject.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

        public void CambiarLogIn() throws IOException{
            Main.setRoot("fxml/login");
        }
}

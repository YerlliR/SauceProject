package com.example.sauceproject;

import com.example.sauceproject.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class InicioController {

    public void CambiarLogIn() throws IOException{


        Main.setRoot("fxml/login");

    }

    public void CambiarSignIn() throws IOException{
        Main.setRoot("fxml/signin");
    }
}

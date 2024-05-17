package com.example.sauceproject.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionBaseDatos {
    public static Connection conexion() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SaucerWallet", "root", "123123");
        return connection;
    }
}

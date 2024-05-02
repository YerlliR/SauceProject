package com.example.sauceproject.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionABaseDeDatos {
    static final String URL = "jdbc:mysql://localhost:3306/SaucerWallet";
    static final String USUARIO = "root";
    static final String CONTRASEÑA = "123123";
    private Connection conexion;
    public Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC: " + e.getMessage());
        }
        return conexion;
    }
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}

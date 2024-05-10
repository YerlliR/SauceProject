package com.example.sauceproject;

import javafx.beans.property.*;

public class Currency2 {
    private final StringProperty nombre;
    private final StringProperty simbolo;
    private final DoubleProperty precio;
    private final DoubleProperty rentabilidad;
    private final DoubleProperty tenencias;
    private final DoubleProperty perdidasGanancias;

    public Currency2(String nombre, String simbolo, double precio, double rentabilidad, double tenencias, double perdidasGanancias) {
        this.nombre = new SimpleStringProperty(nombre);
        this.simbolo = new SimpleStringProperty(simbolo);
        this.precio = new SimpleDoubleProperty(precio);
        this.rentabilidad = new SimpleDoubleProperty(rentabilidad);
        this.tenencias = new SimpleDoubleProperty(tenencias);
        this.perdidasGanancias = new SimpleDoubleProperty(perdidasGanancias);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty simboloProperty() {
        return simbolo;
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public DoubleProperty rentabilidadProperty() {
        return rentabilidad;
    }

    public DoubleProperty tenenciasProperty() {
        return tenencias;
    }

    public DoubleProperty perdidasGananciasProperty() {
        return perdidasGanancias;
    }


    public String getNombre() {
        return nombre.get();
    }

    public double getPerdidasGanancias() {
        return perdidasGanancias.get();
    }

    public double getTenencias() {
        return tenencias.get();
    }

    public double getRentabilidad() {
        return rentabilidad.get();
    }

    public double getPrecio() {
        return precio.get();
    }

    public String getSimbolo() {
        return simbolo.get();
    }
}

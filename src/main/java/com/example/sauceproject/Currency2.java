package com.example.sauceproject;

import javafx.beans.property.*;

public class Currency2 {
    private final StringProperty Nombre_Criptomoneda;
    private final StringProperty Símbolo;
    private final DoubleProperty Precio_Actual;
    private final DoubleProperty Cambio_Porcentual_24h;
    private final DoubleProperty Cantidad_Total;
    private final DoubleProperty Rentabilidad;
    private final DoubleProperty Perdidas_Ganancias;

    public Currency2(String Nombre_Criptomoneda, String Símbolo, double Precio_Actual, double Cambio_Porcentual_24h, double Cantidad_Total, double Rentabilidad, double Perdidas_Ganancias) {
        this.Nombre_Criptomoneda = new SimpleStringProperty(Nombre_Criptomoneda);
        this.Símbolo = new SimpleStringProperty(Símbolo);
        this.Precio_Actual = new SimpleDoubleProperty(Precio_Actual);
        this.Cambio_Porcentual_24h = new SimpleDoubleProperty(Cambio_Porcentual_24h);
        this.Cantidad_Total = new SimpleDoubleProperty(Cantidad_Total);
        this.Rentabilidad = new SimpleDoubleProperty(Rentabilidad);
        this.Perdidas_Ganancias = new SimpleDoubleProperty(Perdidas_Ganancias);
    }

    public String getNombre_Criptomoneda() {
        return Nombre_Criptomoneda.get();
    }

    public StringProperty nombre_CriptomonedaProperty() {
        return Nombre_Criptomoneda;
    }

    public void setNombre_Criptomoneda(String nombre_Criptomoneda) {
        this.Nombre_Criptomoneda.set(nombre_Criptomoneda);
    }

    public String getSímbolo() {
        return Símbolo.get();
    }

    public StringProperty símboloProperty() {
        return Símbolo;
    }

    public void setSímbolo(String símbolo) {
        this.Símbolo.set(símbolo);
    }

    public double getPrecio_Actual() {
        return Precio_Actual.get();
    }

    public DoubleProperty precio_ActualProperty() {
        return Precio_Actual;
    }

    public void setPrecio_Actual(double precio_Actual) {
        this.Precio_Actual.set(precio_Actual);
    }

    public double getCambio_Porcentual_24h() {
        return Cambio_Porcentual_24h.get();
    }

    public DoubleProperty cambio_Porcentual_24hProperty() {
        return Cambio_Porcentual_24h;
    }

    public void setCambio_Porcentual_24h(double cambio_Porcentual_24h) {
        this.Cambio_Porcentual_24h.set(cambio_Porcentual_24h);
    }

    public double getCantidad_Total() {
        return Cantidad_Total.get();
    }

    public DoubleProperty cantidad_TotalProperty() {
        return Cantidad_Total;
    }

    public void setCantidad_Total(double cantidad_Total) {
        this.Cantidad_Total.set(cantidad_Total);
    }

    public double getRentabilidad() {
        return Rentabilidad.get();
    }

    public DoubleProperty rentabilidadProperty() {
        return Rentabilidad;
    }

    public void setRentabilidad(double rentabilidad) {
        this.Rentabilidad.set(rentabilidad);
    }

    public double getPerdidas_Ganancias() {
        return Perdidas_Ganancias.get();
    }

    public DoubleProperty perdidas_GananciasProperty() {
        return Perdidas_Ganancias;
    }

    public void setPerdidas_Ganancias(double perdidas_Ganancias) {
        this.Perdidas_Ganancias.set(perdidas_Ganancias);
    }
}

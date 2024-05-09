package com.example.sauceproject;

import javafx.beans.property.*;

public class Currency {
    private final IntegerProperty cmcRank;
    private final StringProperty name;
    private final StringProperty symbol;
    private final DoubleProperty price;
    private final DoubleProperty percentChange;
    private final DoubleProperty marketCap;

    public Currency(int cmcRank, String name, String symbol, double price, double percentChange, double marketCap) {
        this.cmcRank = new SimpleIntegerProperty(cmcRank);
        this.name = new SimpleStringProperty(name);
        this.symbol = new SimpleStringProperty(symbol);
        this.price = new SimpleDoubleProperty(price);
        this.percentChange = new SimpleDoubleProperty(percentChange);
        this.marketCap = new SimpleDoubleProperty(marketCap);
    }


    public IntegerProperty cmcRankProperty() {
        return cmcRank;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty symbolProperty() {
        return symbol;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public DoubleProperty percentChangeProperty() {
        return percentChange;
    }

    public DoubleProperty marketCapProperty() {
        return marketCap;
    }

















}

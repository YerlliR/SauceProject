package com.example.sauceproject;

public class Dato {
    private String symbol;
    private Double percentChange24h;

    public Dato(String symbol, Double percentChange24h) {
        this.symbol = symbol;
        this.percentChange24h = percentChange24h;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String name) {
        this.symbol = name;
    }

    public Double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(Double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }
}

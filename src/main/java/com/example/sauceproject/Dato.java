package com.example.sauceproject;

public class Dato {
    private String symbol;
    private Double percentChange24h;
    private String link;

    public Dato(String symbol, Double percentChange24h, String link) {
        this.symbol = symbol;
        this.percentChange24h = percentChange24h;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

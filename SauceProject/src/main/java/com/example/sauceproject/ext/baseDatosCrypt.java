package com.example.sauceproject.ext;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class baseDatosCrypt {
    public static void magic() {
        String filePath = "SauceProject/src/main/resources/com/example/sauceproject/json/data.json";

        try {
            // Leer el contenido del archivo JSON
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            // Establecer conexión con la base de datos
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SaucerWallet", "root", "123123");
            clearTable(connection);
            // Instanciar Gson
            Gson gson = new Gson();

            // Iterar sobre los objetos en el JSON y guardarlos en la base de datos
            for (int i = 0; i < dataArray.size(); i++) {
                JsonObject currencyObject = dataArray.get(i).getAsJsonObject();
                CryptoCurrency currency = gson.fromJson(currencyObject, CryptoCurrency.class);
                saveToDatabase(currency, connection);
            }

            connection.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    private static void clearTable(Connection connection) throws SQLException {
        String query = "DELETE FROM currencies";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private static void saveToDatabase(CryptoCurrency currency, Connection connection) throws SQLException {
        String query = "INSERT INTO currencies (id, name, symbol, cmc_rank, last_updated, price, percent_change_24h, market_cap) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, currency.getId());
        preparedStatement.setString(2, currency.getName());
        preparedStatement.setString(3, currency.getSymbol());
        preparedStatement.setInt(4, currency.getCmc_rank());
        preparedStatement.setString(5, currency.getLast_updated());
        preparedStatement.setDouble(6, currency.getQuote().getUSD().getPrice());
        preparedStatement.setDouble(7, currency.getQuote().getUSD().getPercent_change_24h());
        preparedStatement.setDouble(8, currency.getQuote().getUSD().getMarket_cap());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}

class CryptoCurrency {
    private int id;
    private String name;
    private String symbol;
    private int cmc_rank;
    private String last_updated;
    private Quote quote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getCmc_rank() {
        return cmc_rank;
    }

    public void setCmc_rank(int cmc_rank) {
        this.cmc_rank = cmc_rank;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    // Getters y setters
}

class Quote {
    private USD USD;

    public USD getUSD() {
        return USD;
    }

    public void setUSD(USD USD) {
        this.USD = USD;
    }

    // Getters y setters
}

class USD {
    private double price;
    private double percent_change_24h;
    private double market_cap;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(double percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public double getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(double market_cap) {
        this.market_cap = market_cap;
    }

    // Getters y setters
}

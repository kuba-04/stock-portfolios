package com.kuba.stock.portfolios.portfolio;

import java.util.*;

public class Portfolio {

    private String name;
    private int position;
    private Map<String, Integer> stocks = new HashMap<>();

    private Portfolio(String name) {
        this.name = name;
    }

    static Portfolio create(String name) {
        return new Portfolio(name);
    }

    void updatePosition(int newPosition) {
        position = newPosition;
    }

    void applySorting(Map<String, Integer> sorting) {
        stocks.putAll(sorting);
    }

    void addStock(String stockId) {
        stocks.put(stockId, stocks.size());
    }

    void deleteStock(String stockId) {
        stocks.remove(stockId);
    }

    String getName() {
        return name;
    }

    int getPosition() {
        return position;
    }

    Map<String, Integer> getStocks() {
        return stocks;
    }
}

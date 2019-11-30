package com.kuba.stock.portfolios.domain.portfolio;

import com.kuba.stock.portfolios.domain.shared.PortfolioDto;
import java.util.*;

public class Portfolio {

    private PortfolioId id;
    private String userId;
    private String name;
    private int position;
    private Map<String, Integer> stocks = new HashMap<>();

    private Portfolio(String name, String userId) {
        this.name = name;
        this.userId = userId;
        this.position = 0;
        this.id = PortfolioId.newOne();
    }

    static Portfolio create(String name, String userId) {
        return new Portfolio(name, userId);
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

    PortfolioId id() {
        return this.id;
    }

    boolean isOfUser(String userId) {
        return userId.equals(this.userId);
    }

    PortfolioDto toDto() {
        return new PortfolioDto(id, userId, name, position, stocks);
    }
}

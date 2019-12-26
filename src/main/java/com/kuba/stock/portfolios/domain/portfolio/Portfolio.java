package com.kuba.stock.portfolios.domain.portfolio;

import com.kuba.stock.portfolios.domain.portfolio.shared.PortfolioDto;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    private final PortfolioId id;
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

    public static Portfolio create(String name, String userId) {
        return new Portfolio(name, userId);
    }

    Portfolio updatePosition(int newPosition) {
        position = newPosition;
        return this;
    }

    Portfolio applySorting(Map<String, Integer> sorting) {
        stocks.putAll(sorting);
        return this;
    }

    public Portfolio addStock(String stockId) {
        stocks.put(stockId, stocks.size());
        return this;
    }

    Portfolio deleteStock(String stockId) {
        stocks.remove(stockId);
        return this;
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

    public PortfolioId id() {
        return this.id;
    }

    boolean isOfUser(String userId) {
        return userId.equals(this.userId);
    }

    PortfolioDto toDto() {
        return new PortfolioDto(id, userId, name, position, stocks);
    }
}

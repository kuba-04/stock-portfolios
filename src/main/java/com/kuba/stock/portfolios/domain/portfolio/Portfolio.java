package com.kuba.stock.portfolios.domain.portfolio;

import com.kuba.stock.portfolios.domain.portfolio.shared.PortfolioDto;
import lombok.EqualsAndHashCode;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
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

    public Portfolio updatePosition(int newPosition) {
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

    public Portfolio deleteStock(String stockId) {
        stocks.remove(stockId);
        return this;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public Map<String, Integer> getStocks() {
        return stocks;
    }

    public PortfolioId id() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    PortfolioDto toDto() {
        return new PortfolioDto(id, userId, name, position, stocks);
    }
}

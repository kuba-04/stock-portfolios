package com.kuba.stock.portfolios.domain.stock;

import java.util.*;

public class InMemoryStockRepository implements StockRepository {

    private Map<StockId, Stock> stocks = new HashMap<>();

    @Override
    public Stock save(Stock stock) {
        stocks.put(stock.id(), stock);
        return stock;
    }

    @Override
    public Optional<Stock> findById(StockId id) {
        return stocks.values().stream()
                .filter(stock -> stock.id().equals(id))
                .findFirst();
    }
}

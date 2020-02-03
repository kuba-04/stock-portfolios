package com.kuba.stock.portfolios.domain.stock;

import java.util.*;
import java.util.stream.Stream;

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

    @Override
    public Stream<Stock> findByIdIn(Set<String> ids) {
        return stocks.values().stream()
                .filter((stock -> ids.contains(stock.id().id())));
    }
}

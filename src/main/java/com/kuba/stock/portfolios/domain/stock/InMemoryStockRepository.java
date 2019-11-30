package com.kuba.stock.portfolios.domain.stock;

import java.util.*;
import java.util.stream.Stream;

public class InMemoryStockRepository implements StockRepository {

    private Map<StockId, Stock> stocks = new HashMap<>();

    @Override
    public Stock save(Stock stock) {
        return stocks.put(stock.id(), stock);
    }

    @Override
    public void deleteById(StockId id) {
        stocks.remove(id);
    }

    @Override
    public Optional<Stock> findById(StockId id) {
        return stocks.values().stream()
                .filter(stock -> stock.id().equals(id))
                .findFirst();
    }

    @Override
    public Stream<Stock> findAllByIdIn(List<StockId> ids) {
        return stocks.values().stream()
                .filter(stock -> ids.contains(stock.id()));
    }
}

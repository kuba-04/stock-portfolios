package com.kuba.stock.portfolios.domain.stock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface StockRepository {

    Stock save(Stock stock);

    void deleteById(StockId id);

    Optional<Stock> findById(StockId id);

    Stream<Stock> findAllByIdIn(List<StockId> ids);
}

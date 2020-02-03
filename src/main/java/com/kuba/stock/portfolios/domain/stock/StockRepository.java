package com.kuba.stock.portfolios.domain.stock;

import java.util.*;
import java.util.stream.Stream;

public interface StockRepository {

    Stock save(Stock stock);

    Optional<Stock> findById(StockId id);

    Stream<Stock> findByIdIn(Set<String> ids);

}

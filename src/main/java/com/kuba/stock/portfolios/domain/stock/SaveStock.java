package com.kuba.stock.portfolios.domain.stock;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveStock {

    private final StockRepository stockRepository;
    private final RetrieveStock retrievalService;

    Stock saveNew(String symbol) {
        // stock already exists, handle that exception
        stockRepository.findById(new StockId(symbol))
                .ifPresent(e -> {
                    throw new IllegalArgumentException("Stock " + symbol + " already exists!");
                });
        // stock is new so go to IEX API to retrieve it and save
        Stock newStock = retrievalService.retrieve(symbol);
        return stockRepository.save(newStock);
    }
}

package com.kuba.stock.portfolios.domain.stock.usecases;

import com.kuba.stock.portfolios.domain.stock.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveNewStock {

    private final StockRepository stockRepository;
    private final RetrieveStock retrievalService;

    public Stock getNewAndSave(String symbol) {
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

class RetrieveStock {

    //TODO implement with webclient
    Stock retrieve(String symbol) {
        return new Stock(symbol);
    }
}

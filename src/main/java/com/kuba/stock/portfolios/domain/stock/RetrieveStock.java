package com.kuba.stock.portfolios.domain.stock;

class RetrieveStock {

    //TODO implement with webclient
    Stock retrieve(String symbol) {
        return new Stock(symbol);
    }
}

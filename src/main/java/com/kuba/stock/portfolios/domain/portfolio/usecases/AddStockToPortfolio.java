package com.kuba.stock.portfolios.domain.portfolio.usecases;

import com.kuba.stock.portfolios.domain.commons.DomainEvents;
import com.kuba.stock.portfolios.domain.commons.Result;
import com.kuba.stock.portfolios.domain.portfolio.*;
import lombok.RequiredArgsConstructor;

import static com.kuba.stock.portfolios.domain.commons.Result.REJECTION;
import static com.kuba.stock.portfolios.domain.commons.Result.SUCCESS;

@RequiredArgsConstructor
public class AddStockToPortfolio {

    private final PortfolioRepository repository;
    private final DomainEvents domainEvents;

    public Result addStock(String symbol, PortfolioId portfolioId) {
        return repository.findById(portfolioId)
                .map(p -> p.addStock(symbol))
                .map(p -> saveAndPublishEvent(p, symbol))
                .map(savedPortfolio -> SUCCESS)
                .orElse(REJECTION);
    }

    private Portfolio saveAndPublishEvent(Portfolio portfolio, String symbol) {
        repository.save(portfolio);
        domainEvents.publish(new StockAddedToPortfolio(portfolio.id(), symbol));
        return portfolio;
    }

}

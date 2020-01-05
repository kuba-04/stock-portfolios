package com.kuba.stock.portfolios.domain.portfolio.usecases;

import com.kuba.stock.portfolios.domain.commons.Result;
import com.kuba.stock.portfolios.domain.portfolio.PortfolioId;
import com.kuba.stock.portfolios.domain.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;

import static com.kuba.stock.portfolios.domain.commons.Result.REJECTION;
import static com.kuba.stock.portfolios.domain.commons.Result.SUCCESS;

@RequiredArgsConstructor
public class DeleteStockFromPortfolio {

    private final PortfolioRepository repository;

    public Result deleteStock(String symbol, PortfolioId portfolioId) {
        return repository.findById(portfolioId)
                .map(p -> p.deleteStock(symbol))
                .map(repository::save)
                .map(savedPortfolio -> SUCCESS)
                .orElse(REJECTION);
    }

}

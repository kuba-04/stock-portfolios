package com.kuba.stock.portfolios.domain.portfolio.usecases;

import com.kuba.stock.portfolios.domain.portfolio.PortfolioId;
import com.kuba.stock.portfolios.domain.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletePortfolio {

    private final PortfolioRepository portfolioRepository;

    void delete(String userId, PortfolioId id) {
        portfolioRepository.deleteByUserIdAndId(userId, id);
    }
}

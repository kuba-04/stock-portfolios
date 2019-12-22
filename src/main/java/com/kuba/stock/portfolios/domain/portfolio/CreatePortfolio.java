package com.kuba.stock.portfolios.domain.portfolio;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatePortfolio {

    private final PortfolioRepository portfolioRepository;

    Portfolio create(String userId, String portfolioName) {
        portfolioRepository.findByUserIdAndName(userId, portfolioName)
                .ifPresent(e -> {
                    throw new IllegalArgumentException("Portfolio " + portfolioName + " already exists for user " + userId);
                });
        Portfolio newPortfolio = Portfolio.create(portfolioName, userId);
        return portfolioRepository.save(newPortfolio);
    }
}

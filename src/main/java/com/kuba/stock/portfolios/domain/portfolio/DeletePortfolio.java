package com.kuba.stock.portfolios.domain.portfolio;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletePortfolio {

    private final PortfolioRepository portfolioRepository;

    void delete(String userId, PortfolioId id) {
        portfolioRepository.deleteByUserIdAndId(userId, id);
    }
}

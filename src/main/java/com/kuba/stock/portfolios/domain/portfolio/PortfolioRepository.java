package com.kuba.stock.portfolios.domain.portfolio;

import java.util.List;

public interface PortfolioRepository {

    Portfolio save(Portfolio portfolio);

    void deleteById(PortfolioId id);

    List<Portfolio> findByUserId(String userId);

}

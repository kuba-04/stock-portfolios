package com.kuba.stock.portfolios.domain.portfolio;

import java.util.Optional;
import java.util.stream.Stream;

public interface PortfolioRepository {

    Portfolio save(Portfolio portfolio);

    void deleteById(PortfolioId id);

    Stream<Portfolio> findByUserId(String userId);

    Optional<Portfolio> findById(PortfolioId id);

}

package com.kuba.stock.portfolios.domain.portfolio;

import java.util.Optional;
import java.util.stream.Stream;

public interface PortfolioRepository {

    Portfolio save(Portfolio portfolio);

    void deleteByUserIdAndId(String userId, PortfolioId id);

    Stream<Portfolio> findByUserId(String userId);

    Optional<Portfolio> findById(PortfolioId id);

    Optional<Portfolio> findByUserIdAndName(String userId, String name);

}

package com.kuba.stock.portfolios.domain.portfolio;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryPortfolioRepository implements PortfolioRepository {

    private Map<PortfolioId, Portfolio> portfolios = new HashMap<>();

    @Override
    public Portfolio save(Portfolio portfolio) {
        portfolios.put(portfolio.id(), portfolio);
        return portfolio;
    }

    @Override
    public void deleteById(PortfolioId id) {
        portfolios.remove(id);
    }

    @Override
    public List<Portfolio> findByUserId(String userId) {
        return portfolios.values().stream()
                .filter(p -> p.isOfUser(userId))
                .collect(Collectors.toList());
    }
}

package com.kuba.stock.portfolios.domain.portfolio;

import java.util.*;
import java.util.stream.Stream;

public class InMemoryPortfolioRepository implements PortfolioRepository {

    private Map<PortfolioId, Portfolio> portfolios = new HashMap<>();

    @Override
    public Portfolio save(Portfolio portfolio) {
        portfolios.put(portfolio.id(), portfolio);
        return portfolio;
    }

    @Override
    public void deleteByUserIdAndId(String userId, PortfolioId id) {
        portfolios.values().removeIf(p -> p.isOfUser(userId) && p.id().equals(id));
    }

    @Override
    public Stream<Portfolio> findByUserId(String userId) {
        return portfolios.values().stream()
                .filter(p -> p.isOfUser(userId));
    }

    @Override
    public Optional<Portfolio> findById(PortfolioId id) {
        return portfolios.values().stream()
                .filter(p -> p.id() == id)
                .findFirst();
    }

    @Override
    public Optional<Portfolio> findByUserIdAndName(String userId, String name) {
        return portfolios.values().stream()
                .filter(p -> p.isOfUser(userId) && p.getName().equals(name))
                .findFirst();
    }
}

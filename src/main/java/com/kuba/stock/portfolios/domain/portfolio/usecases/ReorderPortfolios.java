package com.kuba.stock.portfolios.domain.portfolio.usecases;

import com.kuba.stock.portfolios.domain.portfolio.Portfolio;
import com.kuba.stock.portfolios.domain.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReorderPortfolios {

    final PortfolioRepository repository;

    public List<Portfolio> reorder(String userId, Map<String, Integer> portfolios) {
        return repository.findByUserId(userId)
                .map(p -> p.updatePosition(portfolios.get(p.getName())))
                .map(repository::save)
                .collect(Collectors.toList());
    }
}

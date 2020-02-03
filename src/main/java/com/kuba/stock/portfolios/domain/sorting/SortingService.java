package com.kuba.stock.portfolios.domain.sorting;

import com.kuba.stock.portfolios.domain.portfolio.*;
import com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter;
import com.kuba.stock.portfolios.domain.sorting.normalizer.PortfolioNormalizer;
import com.kuba.stock.portfolios.domain.stock.*;
import lombok.RequiredArgsConstructor;
import java.util.*;
import java.util.stream.Collectors;

import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.ACTUAL_EPS;
import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.MARKETCAP_TO_EMPLOYEE;
import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.PE_RATIO;

@RequiredArgsConstructor
public class SortingService {

    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final PortfolioNormalizer portfolioNormalizer;

    public PortfolioSorted sort(PortfolioId portfolioId, List<Parameter> sorting) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        Set<String> stockList = portfolio
                .map(p -> p.getStocks().keySet())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio: " + portfolioId + " doesn't exist!"));

        List<StockDto> sortedStocks = portfolioNormalizer.normalize(stockRepository.findByIdIn(stockList)
                .map(Stock::toDto).collect(Collectors.toList())).stream()
                .map(s -> applyCoefficient(s, sorting))
                .sorted(Comparator.comparing(NormalizedStockDto::getSortingCoefficient).reversed())
                .map(NormalizedStockDto::toDto)
                .collect(Collectors.toList());

        return new PortfolioSorted(portfolio.get().getUserId(), portfolioId.id().toString(), sortedStocks);
    }

    private NormalizedStockDto applyCoefficient(NormalizedStockDto stock, List<Parameter> sorting) {
        double coefficient = 0;
        if (sorting.contains(ACTUAL_EPS)) {
            coefficient += stock.getNActualEPS().doubleValue();
        }
        if (sorting.contains(PE_RATIO)) {
            coefficient += stock.getNPeRatio().doubleValue();
        }
        if (sorting.contains(MARKETCAP_TO_EMPLOYEE)) {
            coefficient += stock.getNMarketCapToEmployees().doubleValue();
        }
        stock.setSortingCoefficient(coefficient);
        return stock;
    }
}

package com.kuba.stock.portfolios.domain.sorting.normalizer;

import com.kuba.stock.portfolios.domain.stock.NormalizedStockDto;
import com.kuba.stock.portfolios.domain.stock.StockDto;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.ACTUAL_EPS;
import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.MARKETCAP_TO_EMPLOYEE;
import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.PE_RATIO;
import static java.math.BigDecimal.ZERO;

@RequiredArgsConstructor
public class PortfolioNormalizer {

    private static final String EXCEPTION_MESSAGE = "Unknown normalization parameter, unable to normalize values. Parameter = {}";

    public List<NormalizedStockDto> normalize(List<StockDto> stocks) {
        return stocks.stream()
                .map(stock -> applyNewNormalizedValues(stocks, stock))
                .collect(Collectors.toList());
    }

    private NormalizedStockDto applyNewNormalizedValues(List<StockDto> stocks, StockDto stock) {
        BigDecimal nPeRatio = getNormalizedValue(stocks, stock, PE_RATIO);
        BigDecimal nActualEPS = getNormalizedValue(stocks, stock, ACTUAL_EPS);
        BigDecimal nMarketCapToEmployees = getNormalizedValue(stocks, stock, MARKETCAP_TO_EMPLOYEE);
        return new NormalizedStockDto(stock.getId(), stock.getEmployees(), stock.getSharesOutstanding(), stock.getDividendYield(),
                stock.getActualEPS(), stock.getLatestPrice(), stock.getMarketCap(), stock.getPeRatio(), stock.getWeek52High(),
                stock.getWeek52Low(), stock.getMarketCapPerEmployee(), nPeRatio, nActualEPS, nMarketCapToEmployees,
                stock.getCompanyName(), stock.getIndustry(), stock.getSector(), stock.getDescription(), 0);
    }

    private BigDecimal getNormalizedValue(List<StockDto> stocks, StockDto stock, Parameter param) {
        switch (param) {
            case ACTUAL_EPS:
                BigDecimal actualEPS = stock.getActualEPS();
                return normalizeByParam(stocks, actualEPS, param);
            case PE_RATIO:
                BigDecimal actualPeRatio = stock.getPeRatio();
                if (actualPeRatio.signum() < 0) {
                    actualPeRatio = ZERO;
                }
                return BigDecimal.ONE.subtract(normalizeByParam(stocks, actualPeRatio, param));
            case MARKETCAP_TO_EMPLOYEE:
                BigDecimal actualMarkCapEmpl = stock.getMarketCapPerEmployee();
                return normalizeByParam(stocks, actualMarkCapEmpl, param);
            default:
                throw new IllegalArgumentException(MessageFormat.format(EXCEPTION_MESSAGE, param));
        }
    }

    private BigDecimal normalizeByParam(List<StockDto> stocks, BigDecimal actualValue, Parameter param) {
        return (actualValue.subtract(getMin(stocks, param)))
                .divide((getMax(stocks, param).subtract(getMin(stocks, param))))
                .setScale(2, RoundingMode.HALF_DOWN);
    }

    private BigDecimal getMin(List<StockDto> stocks, Parameter param) {
        switch (param) {
            case ACTUAL_EPS:
                return stocks.stream()
                        .map(StockDto::getActualEPS)
                        .min(Comparator.naturalOrder()).orElseThrow(IllegalArgumentException::new);
            case PE_RATIO:
                return stocks.stream()
                        .map(StockDto::getPeRatio)
                        .min(Comparator.naturalOrder()).orElseThrow(IllegalArgumentException::new);
            case MARKETCAP_TO_EMPLOYEE:
                return stocks.stream()
                        .map(StockDto::getMarketCapPerEmployee)
                        .min(Comparator.naturalOrder()).orElseThrow(IllegalArgumentException::new);
            default:
                throw new IllegalArgumentException(MessageFormat.format(EXCEPTION_MESSAGE, param));
        }
    }

    private BigDecimal getMax(List<StockDto> stockList, Parameter param) {
        switch (param) {
            case ACTUAL_EPS:
                return stockList.stream().parallel()
                        .map(StockDto::getActualEPS)
                        .max(Comparator.naturalOrder()).orElseThrow(IllegalArgumentException::new);
            case PE_RATIO:
                return stockList.stream().parallel()
                        .map(StockDto::getPeRatio)
                        .max(Comparator.naturalOrder()).orElseThrow(IllegalArgumentException::new);
            case MARKETCAP_TO_EMPLOYEE:
                return stockList.stream().parallel()
                        .map(StockDto::getMarketCapPerEmployee)
                        .max(Comparator.naturalOrder()).orElseThrow(IllegalArgumentException::new);
            default:
                throw new IllegalArgumentException(MessageFormat.format(EXCEPTION_MESSAGE, param));
        }
    }
}

package com.kuba.stock.portfolios.domain.stock;

import lombok.*;
import java.math.BigDecimal;

/**
 *  <p>
 *  values to normalize:
 *  - peRatio
 *  - actualEPS
 *  - marketCap/employees
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NormalizedStockDto {

    private StockId id;
    private BigDecimal employees;
    private BigDecimal sharesOutstanding;
    private BigDecimal dividendYield;
    private BigDecimal actualEPS;
    private BigDecimal latestPrice;
    private BigDecimal marketCap;
    private BigDecimal peRatio;
    private BigDecimal week52High;
    private BigDecimal week52Low;
    private BigDecimal marketCapPerEmployee;
    private BigDecimal nPeRatio;
    private BigDecimal nActualEPS;
    private BigDecimal nMarketCapToEmployees;
    private String companyName;
    private String industry;
    private String sector;
    private String description;
    private double sortingCoefficient;

    public void setSortingCoefficient(double sortingCoefficient) {
        this.sortingCoefficient = sortingCoefficient;
    }

    public StockDto toDto() {
        return new StockDto(
                id,
                employees,
                sharesOutstanding,
                dividendYield,
                actualEPS,
                latestPrice,
                marketCap,
                peRatio,
                week52High,
                week52Low,
                marketCapPerEmployee,
                companyName,
                industry,
                sector,
                description);
    }
}

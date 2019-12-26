package com.kuba.stock.portfolios.domain.stock;

import lombok.Data;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * stock entity, contains the following fields available for free from IEXCloud:
 * - symbol (id)
 * - price
 * <p>
 * stats:
 * - employees
 * - sharesOutstanding
 * - dividendYield
 * <p>
 * earnings:
 * - actualEPS (earnings.earnings[0].actualEPS)
 * <p>
 * quote:
 * - latestPrice
 * - marketCap
 * - peRatio
 * - week52High
 * - week52Low
 * <p>
 * company:
 * - company
 * - companyName
 * - industry
 * - sector
 * - description
 */
public class Stock {

    private final StockId id;
    private BigDecimal employees = BigDecimal.ZERO;
    private BigDecimal sharesOutstanding = BigDecimal.ZERO;
    private BigDecimal dividendYield = BigDecimal.ZERO;
    private BigDecimal actualEPS = BigDecimal.ZERO;
    private BigDecimal latestPrice = BigDecimal.ZERO;
    private BigDecimal marketCap = BigDecimal.ZERO;
    private BigDecimal peRatio = BigDecimal.ZERO;
    private BigDecimal week52High = BigDecimal.ZERO;
    private BigDecimal week52Low = BigDecimal.ZERO;
    private BigDecimal marketCapPerEmployee = BigDecimal.ZERO;
    private Company company;

    public Stock(String symbol) {
        id = new StockId(symbol);
    }

    public StockId id() {
        return id;
    }

    void updateEmployees(BigDecimal newEmployees) {
        employees = newEmployees;
        updateMarketCapPerEmployee();
    }

    void updateSharesOutstanding(BigDecimal newSharesOutstanding) {
        sharesOutstanding = newSharesOutstanding;
    }

    void updateDividendYield(BigDecimal newDividendYield) {
        dividendYield = newDividendYield;
    }

    void updateActualEPS(BigDecimal newActualEPS) {
        actualEPS = newActualEPS;
    }

    void updateLatestPrice(BigDecimal newLatestPrice) {
        latestPrice = newLatestPrice;
    }

    void updateMarketCap(BigDecimal newMarketCap) {
        marketCap = newMarketCap;
        updateMarketCapPerEmployee();
    }

    void updatePeRatio(BigDecimal newPeRatio) {
        peRatio = newPeRatio;
    }

    void updateWeek52High(BigDecimal newWeek52High) {
        week52High = newWeek52High;
    }

    void updateWeek52low(BigDecimal newWeek52low) {
        week52Low = newWeek52low;
    }

    void updateCompany(Company newCompany) {
        company = newCompany;
    }

    StockDto toDto() {
        return new StockDto(
                id, employees, sharesOutstanding, dividendYield, actualEPS,
                latestPrice, marketCap, peRatio, week52High, week52Low, marketCapPerEmployee,
                company.getCompanyName(), company.getIndustry(), company.getSector(), company.getDescription());
    }

    private void updateMarketCapPerEmployee() {
        if (employees.signum() > 0) {
            marketCapPerEmployee = marketCap.divide(employees, RoundingMode.FLOOR);
        }
    }
}

@Data
class Company {

    private String companyName;
    private String industry;
    private String sector;
    private String description;
}

package com.kuba.stock.portfolios.domain.sorting;

import com.kuba.stock.portfolios.domain.stock.StockId;
import lombok.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SortedStockDto {

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
    private String companyName;
    private String industry;
    private String sector;
    private String description;
    private String sortOrder;
}

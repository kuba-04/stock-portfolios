package com.kuba.stock.portfolios.domain.sorting;

import com.kuba.stock.portfolios.domain.stock.StockDto;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PortfolioSorted {

    private String userId;
    private String portfolioId;
    private List<StockDto> stocks;

}


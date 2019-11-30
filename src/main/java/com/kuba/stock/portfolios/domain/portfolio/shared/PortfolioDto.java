package com.kuba.stock.portfolios.domain.portfolio.shared;

import com.kuba.stock.portfolios.domain.portfolio.PortfolioId;
import lombok.*;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PortfolioDto {

    private PortfolioId id;
    private String userId;
    private String name;
    private int position;
    private Map<String, Integer> stocks;
}

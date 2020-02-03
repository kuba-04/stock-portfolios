package com.kuba.stock.portfolios.domain.portfolio;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@EqualsAndHashCode
@AllArgsConstructor
public class PortfolioId {

    private final UUID id;

    static PortfolioId newOne() {
        return new PortfolioId(UUID.randomUUID());
    }

    public UUID id() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

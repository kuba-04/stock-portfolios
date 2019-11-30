package com.kuba.stock.portfolios.domain.portfolio;

import lombok.*;
import java.util.UUID;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class PortfolioId {

    private final UUID id;

    public static PortfolioId newOne() {
        return new PortfolioId(UUID.randomUUID());
    }

    public UUID id() {
        return id;
    }

}

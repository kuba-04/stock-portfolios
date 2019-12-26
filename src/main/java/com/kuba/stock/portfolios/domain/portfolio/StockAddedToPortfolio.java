package com.kuba.stock.portfolios.domain.portfolio;

import com.kuba.stock.portfolios.domain.commons.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Value;
import java.time.Instant;
import java.util.UUID;

@Value
@AllArgsConstructor
public class StockAddedToPortfolio implements DomainEvent {

    private UUID eventId = UUID.randomUUID();
    private PortfolioId portfolioId;
    private String symbol;
    private Instant when = Instant.now();

    @Override
    public UUID getEventId() {
        return eventId;
    }
}

package com.kuba.stock.portfolios.domain.stock;

import com.kuba.stock.portfolios.domain.commons.DomainEvent;
import com.kuba.stock.portfolios.domain.commons.storage.EventStorage;
import com.kuba.stock.portfolios.domain.portfolio.StockAddedToPortfolio;
import com.kuba.stock.portfolios.domain.stock.usecases.SaveNewStock;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class AddStockToPortfolioEventHandler {

    private final EventStorage eventStorage;
    private final SaveNewStock addStock;

    public void handleEvents() {
        List<DomainEvent> events = eventStorage.toPublish();
        events.stream()
                .filter(e -> e instanceof StockAddedToPortfolio)
                .map(e -> ((StockAddedToPortfolio) e))
                .forEach(e -> addStock.getNewAndSave(e.getSymbol()));
        eventStorage.published(events);
    }

}

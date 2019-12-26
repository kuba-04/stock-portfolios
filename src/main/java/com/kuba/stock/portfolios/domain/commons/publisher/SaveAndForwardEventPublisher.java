package com.kuba.stock.portfolios.domain.commons.publisher;

import com.kuba.stock.portfolios.domain.commons.DomainEvent;
import com.kuba.stock.portfolios.domain.commons.DomainEvents;
import com.kuba.stock.portfolios.domain.commons.storage.EventStorage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveAndForwardEventPublisher implements DomainEvents {

    private final EventStorage eventStorage;

    //TODO implement
    public void publish(DomainEvent event) {
        eventStorage.save(event);
    }
}

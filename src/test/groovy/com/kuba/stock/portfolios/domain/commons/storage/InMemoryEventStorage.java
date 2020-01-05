package com.kuba.stock.portfolios.domain.commons.storage;

import com.kuba.stock.portfolios.domain.commons.DomainEvent;
import java.util.*;

public class InMemoryEventStorage implements EventStorage {

    private final List<DomainEvent> eventList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(DomainEvent event) {
        eventList.add(event);
    }

    @Override
    public List<DomainEvent> toPublish() {
        return Collections.unmodifiableList(eventList);
    }

    @Override
    public void published(List<DomainEvent> events) {
        eventList.removeAll(events);
    }
}

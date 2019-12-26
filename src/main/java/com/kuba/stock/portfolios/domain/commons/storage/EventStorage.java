package com.kuba.stock.portfolios.domain.commons.storage;

import com.kuba.stock.portfolios.domain.commons.DomainEvent;
import java.util.List;

public interface EventStorage {

    void save(DomainEvent event);

    List<DomainEvent> toPublish();

    void published(List<DomainEvent> events);
}

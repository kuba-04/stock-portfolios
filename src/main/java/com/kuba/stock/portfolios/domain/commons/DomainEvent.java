package com.kuba.stock.portfolios.domain.commons;

import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();
}

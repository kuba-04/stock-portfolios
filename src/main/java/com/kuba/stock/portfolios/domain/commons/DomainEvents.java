package com.kuba.stock.portfolios.domain.commons;

public interface DomainEvents {

    void publish(DomainEvent event);
}

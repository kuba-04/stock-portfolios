package com.kuba.stock.portfolios.domain.portfolio;

import java.util.Objects;
import java.util.UUID;

public class PortfolioId {

    private final UUID id;

    public PortfolioId(UUID id) {this.id = id;}

    public static PortfolioId newOne() {
        return new PortfolioId(UUID.randomUUID());
    }

    public UUID id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioId that = (PortfolioId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "PortfolioId{" +
                "id=" + id +
                '}';
    }
}

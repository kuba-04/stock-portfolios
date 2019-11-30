package com.kuba.stock.portfolios.domain.stock;

import lombok.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class StockId {

    private final String id;

    String id() {
        return id;
    }

}

package com.kuba.stock.portfolios.domain.stock

import spock.lang.Specification
import spock.lang.Subject

class SaveStockTest extends Specification {

    def repository = new InMemoryStockRepository()
    def retrieveStock = new RetrieveStock()

    @Subject
    def saveStock = new SaveStock(repository, retrieveStock)


    def "should save new stock if not present already"() {
        given:
        def symbol = "XOM"

        when:
        def savedStock = saveStock.saveNew(symbol)

        then:
        Optional<Stock> addedStock = repository.findById(savedStock.id())
        addedStock.isPresent()
        addedStock.get().id() == new StockId(symbol)
    }

    def "should not save new stock if already exists"() {
        given:
        def symbol = "XOM"

        and:
        saveStock.saveNew(symbol)

        when:
        saveStock.saveNew(symbol)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Stock " + symbol + " already exists!"
    }
}

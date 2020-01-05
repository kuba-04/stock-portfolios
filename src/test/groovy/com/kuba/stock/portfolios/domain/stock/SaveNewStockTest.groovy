package com.kuba.stock.portfolios.domain.stock

import com.kuba.stock.portfolios.domain.stock.usecases.RetrieveStock
import com.kuba.stock.portfolios.domain.stock.usecases.SaveNewStock
import spock.lang.Specification
import spock.lang.Subject

class SaveNewStockTest extends Specification {

    def repository = new InMemoryStockRepository()
    def retrieveStock = new RetrieveStock()

    @Subject
    def saveNewStock = new SaveNewStock(repository, retrieveStock)


    def "should add new stock if not present already"() {
        given:
        def symbol = "XOM"

        when:
        def savedStock = saveNewStock.getNewAndSave(symbol)

        then:
        Optional<Stock> addedStock = repository.findById(savedStock.id())
        addedStock.isPresent()
        addedStock.get().id() == new StockId(symbol)
    }

    def "should not add new stock if already exists"() {
        given:
        def symbol = "XOM"

        and:
        saveNewStock.getNewAndSave(symbol)

        when:
        saveNewStock.getNewAndSave(symbol)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Stock " + symbol + " already exists!"
    }
}

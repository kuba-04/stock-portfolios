package com.kuba.stock.portfolios.domain.portfolio

import static com.kuba.stock.portfolios.domain.commons.Result.SUCCESS
import com.kuba.stock.portfolios.domain.commons.publisher.SaveAndForwardEventPublisher
import com.kuba.stock.portfolios.domain.commons.storage.InMemoryEventStorage
import com.kuba.stock.portfolios.domain.portfolio.usecases.AddStockToPortfolio
import com.kuba.stock.portfolios.domain.stock.InMemoryStockRepository
import com.kuba.stock.portfolios.domain.stock.StockId
import com.kuba.stock.portfolios.domain.stock.AddStockToPortfolioEventHandler
import com.kuba.stock.portfolios.domain.stock.usecases.RetrieveStock
import com.kuba.stock.portfolios.domain.stock.usecases.SaveNewStock
import spock.lang.Specification
import spock.lang.Subject

class AddStockToPortfolioTest extends Specification {

    // portfolio aggregate
    def portfolioRepository = new InMemoryPortfolioRepository()
    def eventStorage = new InMemoryEventStorage()
    def eventPublisher = new SaveAndForwardEventPublisher(eventStorage)

    @Subject
    def addStockToPortfolio = new AddStockToPortfolio(portfolioRepository, eventPublisher)

    // stock aggregate
    def stockRepository = new InMemoryStockRepository()
    def retrieveStock = new RetrieveStock()
    def saveNewStock = new SaveNewStock(stockRepository, retrieveStock)
    def eventHandler = new AddStockToPortfolioEventHandler(eventStorage, saveNewStock)

    def "should add stocks to portfolio with events"() {
        given: "existing portfolio"
        def portfolio = Portfolio.create("US stocks", "1234")
        portfolioRepository.save(portfolio)

        when: "stock is added to portfolio"
        def addStockResult = addStockToPortfolio.addStock("XOM", portfolio.id())

        then: "transaction is successful"
        addStockResult == SUCCESS

        and: "portfolio has updated list of stocks"
        portfolioRepository.findById(portfolio.id()).get().stocks.containsKey("XOM")

        and: "event handler is triggered"
        eventHandler.handleEvents()

        and: "stock repository is updated with that stock"
        stockRepository.findById(new StockId("XOM")).present
    }
}

package com.kuba.stock.portfolios.domain.portfolio.usecases

import com.kuba.stock.portfolios.domain.commons.Result
import com.kuba.stock.portfolios.domain.portfolio.InMemoryPortfolioRepository
import com.kuba.stock.portfolios.domain.portfolio.Portfolio
import spock.lang.Specification
import spock.lang.Subject

class DeleteStockFromPortfolioTest extends Specification {

    def repository = new InMemoryPortfolioRepository()

    @Subject
    def deleteStock = new DeleteStockFromPortfolio(repository)

    def "should delete stock from portfolio"() {
        given: "portfolio was saved"
        def portfolio = Portfolio.create("US Stocks", "1234")
        portfolio.addStock("XOM")
        repository.save(portfolio)
        def existingPortfolio = repository.findByUserIdAndName("1234", "US Stocks")

        expect: "US Stocks portfolio is present in db and contains XOM"
        existingPortfolio.present
        existingPortfolio.get().stocks.containsKey("XOM")

        when: "XOM is deleted from US Stocks"
        def result = deleteStock.deleteStock("XOM", existingPortfolio.get().id())

        and: "US stocks portfolio is retrieved again"
        def retrievedAgainPortfolio = repository.findByUserIdAndName("1234", "US Stocks")

        then: "US Stocks doesn't contain XOM"
        existingPortfolio.present
        retrievedAgainPortfolio.get().stocks.isEmpty()

        and: "returns success"
        result == Result.SUCCESS
    }

}

package com.kuba.stock.portfolios.domain.portfolio.usecases

import com.kuba.stock.portfolios.domain.portfolio.InMemoryPortfolioRepository
import com.kuba.stock.portfolios.domain.portfolio.Portfolio
import spock.lang.Specification

class DeletePortfolioTest extends Specification {

    def portfolioRepo = new InMemoryPortfolioRepository()
    def deletePortfolio = new DeletePortfolio(portfolioRepo)

    def "should delete portfolio"() {
        given:
        def portfolio = Portfolio.create("US stocks", "1234")
        portfolioRepo.save(portfolio)
        and:
        portfolioRepo.findByUserId("1234").count() > 0

        when:
        deletePortfolio.delete("1234", portfolio.id())

        then:
        portfolioRepo.findByUserId("1234").count() == 0
    }
}

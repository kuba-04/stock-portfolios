package com.kuba.stock.portfolios.domain.portfolio

import com.kuba.stock.portfolios.domain.portfolio.usecases.CreatePortfolio
import spock.lang.Specification

class CreatePortfolioTest extends Specification {

    def portfolioRepo = new InMemoryPortfolioRepository()
    def createPortfolio = new CreatePortfolio(portfolioRepo)

    def "should create new portfolio"() {
        when:
        def createdPortfolio = createPortfolio.create("1234", "US stocks")

        then:
        def retrievedPortfolio = portfolioRepo.findById(createdPortfolio.id())
        retrievedPortfolio.get().name == "US stocks"
    }

    def "should not create portfolio with the same name for one user"() {
        given:
        createPortfolio.create("1234", "US stocks")

        when:
        createPortfolio.create("1234", "US stocks")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Portfolio US stocks already exists for user 1234"
    }
}

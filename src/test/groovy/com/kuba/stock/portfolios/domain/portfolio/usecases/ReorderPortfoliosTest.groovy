package com.kuba.stock.portfolios.domain.portfolio.usecases


import com.kuba.stock.portfolios.domain.portfolio.InMemoryPortfolioRepository
import spock.lang.Specification
import spock.lang.Subject

class ReorderPortfoliosTest extends Specification {

    def repository = new InMemoryPortfolioRepository()
    def createPortfolio = new CreatePortfolio(repository)

    @Subject
    def reorderPortfolios = new ReorderPortfolios(repository)

    def "should reorder portfolios"() {
        given: "portfolios"
        createPortfolio.create("1234", "p1")
        createPortfolio.create("1234", "p2")
        createPortfolio.create("1234", "p3")
        createPortfolio.create("12345", "p5")
        createPortfolio.create("1234", "p4")

        expect: "positions were saved sequentially"
        repository.findByUserIdAndName("1234", "p1").get().position == 0
        repository.findByUserIdAndName("1234", "p2").get().position == 1
        repository.findByUserIdAndName("1234", "p3").get().position == 2
        repository.findByUserIdAndName("1234", "p4").get().position == 3

        when: "user updates the positions"
        def newPositions = Map.of("p1", 2, "p2", 0, "p3", 3, "p4", 1)
        reorderPortfolios.reorder("1234", newPositions)

        then: "portfolios are reordered"
        repository.findByUserIdAndName("1234", "p1").get().position == 2
        repository.findByUserIdAndName("1234", "p2").get().position == 0
        repository.findByUserIdAndName("1234", "p3").get().position == 3
        repository.findByUserIdAndName("1234", "p4").get().position == 1
    }
}

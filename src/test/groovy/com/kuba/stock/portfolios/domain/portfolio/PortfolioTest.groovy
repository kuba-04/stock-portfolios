package com.kuba.stock.portfolios.domain.portfolio

import spock.lang.Specification

class PortfolioTest extends Specification {

    def "should create new portfolio"() {
        when:
        Portfolio portfolio = Portfolio.create("new stocks", "user1")

        then:
        portfolio.name == "new stocks"
    }

    def "should update portfolio position"() {
        given:
        Portfolio portfolio = Portfolio.create("new stocks", "user1")

        when:
        portfolio.updatePosition(2)

        then:
        portfolio.position == 2
    }

    def "should apply new sorting"() {
        given:
        Portfolio portfolio = Portfolio.create("new stocks", "user1")
        portfolio.addStock("one")
        portfolio.addStock("two")

        expect:
        portfolio.stocks.get("one") == 0
        portfolio.stocks.get("two") == 1

        and:
        Map<String, Integer> newSorting = ["one": 1, "two": 0]

        when:
        portfolio.applySorting(newSorting)

        then:
        portfolio.stocks.get("one") == 1
        portfolio.stocks.get("two") == 0
    }

    def "should add stock"() {
        given:
        Portfolio portfolio = Portfolio.create("new stocks", "user1")

        when:
        portfolio.addStock("one")

        then:
        !portfolio.stocks.isEmpty()
    }

    def "should delete stock"() {
        given:
        Portfolio portfolio = Portfolio.create("new stocks", "user1")
        portfolio.addStock("one")

        when:
        portfolio.deleteStock("one")

        then:
        portfolio.stocks.isEmpty()
    }

}

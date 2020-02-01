package com.kuba.stock.portfolios.domain.sorting.normalizer

import com.kuba.stock.portfolios.domain.stock.StockDto
import com.kuba.stock.portfolios.domain.stock.StockId
import spock.lang.Specification
import java.math.RoundingMode

class PortfolioNormalizerTest extends Specification {

    PortfolioNormalizer dataNormalizer = new PortfolioNormalizer()

    def "should normalize stocks"() {
        given:
        def exxon = new StockDto(new StockId("1"), new BigDecimal(1000), new BigDecimal(1_000_000), new BigDecimal(3),
                new BigDecimal(0.5), new BigDecimal(120), new BigDecimal(2_000_000), new BigDecimal(20), new BigDecimal(140),
                new BigDecimal(80), new BigDecimal(2_000_000).divide(new BigDecimal(1000), RoundingMode.FLOOR), "Exxon", "Oil & Gas", "Energy", "Major oil company in US")

        def chevron = new StockDto(new StockId("2"), new BigDecimal(900), new BigDecimal(1_500_000), new BigDecimal(2),
                new BigDecimal(0.4), new BigDecimal(200), new BigDecimal(3_000_000), new BigDecimal(30), new BigDecimal(240),
                new BigDecimal(180), new BigDecimal(3_000_000).divide(new BigDecimal(900), RoundingMode.FLOOR), "Chevron", "Oil & Gas", "Energy", "Major oil company in US")

        def shell = new StockDto(new StockId("3"), new BigDecimal(1100), new BigDecimal(1_200_000), new BigDecimal(2.5),
                new BigDecimal(0.6), new BigDecimal(50), new BigDecimal(3_200_000), new BigDecimal(10), new BigDecimal(40),
                new BigDecimal(60), new BigDecimal(3_200_000).divide(new BigDecimal(800), RoundingMode.FLOOR), "Shell", "Oil & Gas", "Energy", "Major oil company in the UK")

        def stockList = List.of(exxon, chevron, shell)

        when:
        def normalizedStocks = dataNormalizer.normalize(stockList)

        def normalizedExxon = normalizedStocks.find { s -> (s.companyName == "Exxon") }
        def normalizedChevron = normalizedStocks.find { s -> s.companyName == "Chevron" }
        def normalizedShell = normalizedStocks.find { s -> s.companyName == "Shell" }

        then:
        normalizedExxon.getNActualEPS() == 0.5.toBigDecimal()
        normalizedChevron.getNActualEPS() == BigDecimal.ZERO
        normalizedShell.getNActualEPS() == BigDecimal.ONE

        normalizedExxon.getNPeRatio() == 0.5.toBigDecimal()
        normalizedChevron.getNPeRatio() == BigDecimal.ZERO
        normalizedShell.getNPeRatio() == BigDecimal.ONE

        normalizedExxon.getNMarketCapToEmployees() == BigDecimal.ZERO
        normalizedChevron.getNMarketCapToEmployees() == 0.67.toBigDecimal()
        normalizedShell.getNMarketCapToEmployees() == BigDecimal.ONE
    }
}

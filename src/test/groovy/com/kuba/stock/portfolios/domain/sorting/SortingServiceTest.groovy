package com.kuba.stock.portfolios.domain.sorting

import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.ACTUAL_EPS
import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.MARKETCAP_TO_EMPLOYEE
import static com.kuba.stock.portfolios.domain.sorting.normalizer.Parameter.PE_RATIO
import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.ZERO
import com.kuba.stock.portfolios.domain.portfolio.InMemoryPortfolioRepository
import com.kuba.stock.portfolios.domain.portfolio.Portfolio
import com.kuba.stock.portfolios.domain.portfolio.PortfolioRepository
import com.kuba.stock.portfolios.domain.sorting.normalizer.PortfolioNormalizer
import com.kuba.stock.portfolios.domain.stock.*
import spock.lang.Specification
import spock.lang.Subject

class SortingServiceTest extends Specification {

    PortfolioRepository portfolioRepository = new InMemoryPortfolioRepository()
    StockRepository stockRepository = new InMemoryStockRepository()
    PortfolioNormalizer portfolioNormalizer = Mock()
    @Subject
    SortingService sortingService = new SortingService(portfolioRepository, stockRepository, portfolioNormalizer)

    def "should sort stocks by 1 parameter"() {
        given: "stocks exit in db and are added to portfolio"
        Stock exxon = createExxonStock()
        Stock chevron = createChevronStock()
        Stock shell = createShellStock()

        stockRepository.save(exxon)
        stockRepository.save(chevron)
        stockRepository.save(shell)

        StockDto exxonDto = exxon.toDto()
        StockDto chevronDto = chevron.toDto()
        StockDto shellDto = shell.toDto()

        Portfolio portfolio = Portfolio.create("US Stocks", "1234")
        portfolio.addStock("XOM")
        portfolio.addStock("CVX")
        portfolio.addStock("RDS")

        portfolioRepository.save(portfolio)

        and: "stocks are normalized"
        portfolioNormalizer.normalize(_ as List) >> List.of(createNormalizedExxon(exxonDto), createNormalizedChevron(chevronDto), createNormalizedShell(shellDto))

        when: "portfolio is sorted by Actual EPS"
        PortfolioSorted portfolioSorted = sortingService.sort(portfolio.id(), List.of(MARKETCAP_TO_EMPLOYEE))

        then: "basic portfolio info is copied"
        portfolioSorted.portfolioId == portfolio.id().toString()
        portfolioSorted.userId == portfolio.getUserId()

        and: "stocks are sorted"
        portfolioSorted.stocks[0] == shellDto
        portfolioSorted.stocks[1] == chevronDto
        portfolioSorted.stocks[2] == exxonDto
    }

    def "should sort stocks by 3 parameters"() {
        given: "stocks exit in db and are added to portfolio"
        Stock exxon = createExxonStock()
        Stock chevron = createChevronStock()
        Stock shell = createShellStock()

        stockRepository.save(exxon)
        stockRepository.save(chevron)
        stockRepository.save(shell)

        StockDto exxonDto = exxon.toDto()
        StockDto chevronDto = chevron.toDto()
        StockDto shellDto = shell.toDto()

        Portfolio portfolio = Portfolio.create("US Stocks", "1234")
        portfolio.addStock("XOM")
        portfolio.addStock("CVX")
        portfolio.addStock("RDS")

        portfolioRepository.save(portfolio)

        and: "stocks are normalized"
        portfolioNormalizer.normalize(_ as List) >> List.of(createNormalizedExxon(exxonDto), createNormalizedChevron(chevronDto), createNormalizedShell(shellDto))

        when: "portfolio is sorted by Actual EPS"
        PortfolioSorted portfolioSorted = sortingService.sort(portfolio.id(), List.of(ACTUAL_EPS, PE_RATIO, MARKETCAP_TO_EMPLOYEE))

        then: "basic portfolio info is copied"
        portfolioSorted.portfolioId == portfolio.id().toString()
        portfolioSorted.userId == portfolio.getUserId()

        and: "stocks are sorted"
        portfolioSorted.stocks[0] == shellDto
        portfolioSorted.stocks[1] == exxonDto
        portfolioSorted.stocks[2] == chevronDto
    }

    private static NormalizedStockDto createNormalizedShell(StockDto shellDto) {
        new NormalizedStockDto(shellDto.id, shellDto.employees, ZERO, ZERO, shellDto.actualEPS,
                ZERO, shellDto.marketCap, shellDto.peRatio, ZERO, ZERO, shellDto.marketCapPerEmployee, ONE,
                ONE, ONE, null, null, null, null, 0)
    }

    private static NormalizedStockDto createNormalizedChevron(StockDto chevronDto) {
        new NormalizedStockDto(chevronDto.id, chevronDto.employees, ZERO, ZERO, chevronDto.actualEPS,
                ZERO, chevronDto.marketCap, chevronDto.peRatio, ZERO, ZERO, chevronDto.marketCapPerEmployee, ZERO,
                ZERO, 0.67.toBigDecimal(), null, null, null, null, 0)
    }

    private static NormalizedStockDto createNormalizedExxon(StockDto exxonDto) {
        new NormalizedStockDto(exxonDto.id, exxonDto.employees, ZERO, ZERO, exxonDto.actualEPS,
                ZERO, exxonDto.marketCap, exxonDto.peRatio, ZERO, ZERO, exxonDto.marketCapPerEmployee, 0.5.toBigDecimal(),
                0.5.toBigDecimal(), ZERO, null, null, null, null, 0)
    }

    private static Stock createShellStock() {
        Stock shell = new Stock("RDS")
        shell.updateActualEPS(0.6.toBigDecimal())
        shell.updateEmployees(1100.toBigDecimal())
        shell.updateMarketCap(1_200_000.toBigDecimal())
        shell.updatePeRatio(10.toBigDecimal())
        shell.updateCompany(new Company())
        shell
    }

    private static Stock createChevronStock() {
        Stock chevron = new Stock("CVX")
        chevron.updateActualEPS(0.4.toBigDecimal())
        chevron.updateEmployees(900.toBigDecimal())
        chevron.updateMarketCap(1_500_000.toBigDecimal())
        chevron.updatePeRatio(30.toBigDecimal())
        chevron.updateCompany(new Company())
        chevron
    }

    private static Stock createExxonStock() {
        Stock exxon = new Stock("XOM")
        exxon.updateActualEPS(0.5.toBigDecimal())
        exxon.updateEmployees(1000.toBigDecimal())
        exxon.updateMarketCap(2_000_000.toBigDecimal())
        exxon.updatePeRatio(20.toBigDecimal())
        exxon.updateCompany(new Company())
        exxon
    }
}

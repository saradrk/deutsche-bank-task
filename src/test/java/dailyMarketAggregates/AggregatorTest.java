package dailyMarketAggregates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AggregatorTest {

	Aggregator aggregator;
	List<Trade> trades;
	@Mock
	Trade mockTrade1;
	@Mock
	Trade mockTrade2;
	@Mock
	Trade mockTrade3;
	@Mock
	Trade mockTrade4;
	LocalTime earlierTestTime = LocalTime.parse("09:00:01");
	LocalTime laterTestTime = LocalTime.parse("09:00:02");
	double lowerPrice = 100.0;
	double higherPrice = 200.0;
	String testTicker = "TT";

	@BeforeEach
	public void setUp() {
		aggregator = new Aggregator();
		trades = new ArrayList<>();

	}

	@Test
	public void test_calculateOpenPriceOfTicker_returnsZero_whenInputListOfTradesIsEmpty() {
		double openPrice = aggregator.calculateOpenPriceOfTicker(trades);
		assertEquals(0.0, openPrice);
	}

	@Test 
	public void test_calculateOpenPriceOfTicker_returns100_whenInputListOfTradesContainsOneTradeWithPrice100() {
		when(mockTrade1.getPrice()).thenReturn(lowerPrice);
		trades.add(mockTrade1);
		double openPrice = aggregator.calculateOpenPriceOfTicker(trades);
		assertEquals(lowerPrice, openPrice);
	}

	@Test 
	public void test_calculateOpenPriceOfTicker_returnsPriceOfLaterTrade_whenInputListOfTradesContainsTwoTrades() {
		when(mockTrade1.getTime()).thenReturn(laterTestTime);
		when(mockTrade2.getTime()).thenReturn(earlierTestTime);
		when(mockTrade2.getPrice()).thenReturn(higherPrice);
		trades.add(mockTrade1);
		trades.add(mockTrade2);
		double openPrice = aggregator.calculateOpenPriceOfTicker(trades);
		assertEquals(higherPrice, openPrice);
	}

	@Test
	public void test_calculateClosePriceOfTicker_returnsZero_whenInputListOfTradesIsEmpty() {
		double closePrice = aggregator.calculateClosePriceOfTicker(trades);
		assertEquals(0.0, closePrice);
	}

	@Test 
	public void test_calculateClosePriceOfTicker_returns100_whenInputListOfTradesContainsOneTradeWithPrice100() {
		when(mockTrade1.getPrice()).thenReturn(lowerPrice);
		trades.add(mockTrade1);
		double closePrice = aggregator.calculateClosePriceOfTicker(trades);
		assertEquals(lowerPrice, closePrice);
	
	}

	@Test 
	public void test_calculateClosePriceOfTicker_returns200_whenInputListOfTradesContainsTwoTradesAndTheLaterOnesPriceIs200() {
		when(mockTrade1.getTime()).thenReturn(earlierTestTime);
		when(mockTrade2.getTime()).thenReturn(laterTestTime);
		when(mockTrade2.getPrice()).thenReturn(higherPrice);
		trades.add(mockTrade1);
		trades.add(mockTrade2);
		double closePrice = aggregator.calculateClosePriceOfTicker(trades);
		assertEquals(higherPrice, closePrice);
	}

	@Test
	public void test_calculateHighestPriceOfTicker_returnsZero_whenInputListOfTradesIsEmpty() {
		double highestPrice = aggregator.calculateHighestPriceOfTicker(trades);
		assertEquals(0.0, highestPrice);
	}

	@Test 
	public void test_calculateHighestPriceOfTicker_returns100_whenInputListOfTradesContainsOneTradeWithPrice100() {
		when(mockTrade1.getPrice()).thenReturn(lowerPrice);
		trades.add(mockTrade1);
		double highestPrice = aggregator.calculateHighestPriceOfTicker(trades);
		assertEquals(lowerPrice, highestPrice);
	}

	@Test 
	public void test_calculateHighestPriceOfTicker_returnsHigherPrice_whenInputListOfTradesContainsTwoTradesWithPrice100And200() {
		when(mockTrade1.getPrice()).thenReturn(lowerPrice);
		when(mockTrade2.getPrice()).thenReturn(higherPrice);
		trades.add(mockTrade1);
		trades.add(mockTrade2);
		double highestPrice = aggregator.calculateHighestPriceOfTicker(trades);
		assertEquals(higherPrice, highestPrice);
	}

	@Test
	public void test_calculateLowestPriceOfTicker_returnsZero_whenInputListOfTradesIsEmpty() {
		double lowestPrice = aggregator.calculateHighestPriceOfTicker(trades);
		assertEquals(0.0, lowestPrice);
	}

	@Test 
	public void test_calculateLowestPriceOfTicker_returns200_whenInputListOfTradesContainsOneTradeWithPrice200() {
		when(mockTrade1.getPrice()).thenReturn(higherPrice);
		trades.add(mockTrade1);
		double lowestPrice = aggregator.calculateLowestPriceOfTicker(trades);
		assertEquals(higherPrice, lowestPrice);
	}

	@Test 
	public void test_calculateLowestPriceOfTicker_returnsLowerPrice_whenInputListOfTradesContainsTwoTradesWithPrice100And200() {
		when(mockTrade1.getPrice()).thenReturn(higherPrice);
		when(mockTrade2.getPrice()).thenReturn(lowerPrice);
		trades.add(mockTrade1);
		trades.add(mockTrade2);
		double lowestPrice = aggregator.calculateLowestPriceOfTicker(trades);
		assertEquals(lowerPrice, lowestPrice);
	}

	@Test
	public void test_calculateDailyTradedVolumeOfTicker_returnsZero_whenInputListOfTradesIsEmpty() {
		double dailyTradedVolume = aggregator.calculateDailyTradedVolumeOfTicker(trades);
		assertEquals(0.0, dailyTradedVolume);
	}

	@Test 
	public void test_calculateDailyTradedVolumeOfTicker_returnsProductOfPriceAndNrOfSecuritiesTraded_whenInputListOfTradesContainsOneTrade() {
		when(mockTrade1.getPrice()).thenReturn(100.0);
		when(mockTrade1.getNrOfSecuritiesTraded()).thenReturn(4);
		trades.add(mockTrade1);
		double dailyTradedVolume = aggregator.calculateDailyTradedVolumeOfTicker(trades);
		assertEquals(400.0, dailyTradedVolume);
	}

	@Test 
	public void test_calculateDailyTradedVolumeOfTicker_returnsSumOfTheProductsOfPriceAndNrOfSecuritiesTraded_whenInputListOfTradesContainsTwoTrades() {
		when(mockTrade1.getPrice()).thenReturn(100.0);
		when(mockTrade2.getPrice()).thenReturn(100.0);
		when(mockTrade1.getNrOfSecuritiesTraded()).thenReturn(4);
		when(mockTrade2.getNrOfSecuritiesTraded()).thenReturn(4);
		trades.add(mockTrade1);
		trades.add(mockTrade2);
		double dailyTradedVolume = aggregator.calculateDailyTradedVolumeOfTicker(trades);
		assertEquals(800.0, dailyTradedVolume);

	}

	@Test
	public void test_calculateOpenPriceOfMarket_returnsZero_ifNoLastKnownOpenPricesOfAnyTickersExist() {
		double openPrice = aggregator.calculateOpenPriceOfMarket();
		assertEquals(0.0, openPrice);
	}

	@Test
	public void test_calculateOpenPriceOfMarket_returnsWeightetSumOfOpenPrices_ifOpenPriceOfEveryMarketTickerExists() {
		when(mockTrade1.getTicker()).thenReturn("ABC");
		when(mockTrade2.getTicker()).thenReturn("MEGA");
		when(mockTrade3.getTicker()).thenReturn("NGL");
		when(mockTrade4.getTicker()).thenReturn("TRX");
		when(mockTrade1.getPrice()).thenReturn(100.0);
		when(mockTrade2.getPrice()).thenReturn(100.0);
		when(mockTrade3.getPrice()).thenReturn(100.0);
		when(mockTrade4.getPrice()).thenReturn(100.0);
		trades.add(mockTrade1);
		aggregator.calculateOpenPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade2);
		aggregator.calculateOpenPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade3);
		aggregator.calculateOpenPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade4);
		aggregator.calculateOpenPriceOfTicker(trades);
		double openPrice = aggregator.calculateOpenPriceOfMarket();
		assertEquals(100.0, openPrice);
	}

	@Test
	public void test_calculateClosePriceOfMarket_returnsZero_ifNoLastKnownClosePricesOfAnyTickersExist() {
		double closePrice = aggregator.calculateClosePriceOfMarket();
		assertEquals(0.0, closePrice);
	}

	@Test
	public void test_calculateClosePriceOfMarket_returnsWeightetSumOfClosePrices_ifClosePriceOfEveryMarketTickerExists() {
		when(mockTrade1.getTicker()).thenReturn("ABC");
		when(mockTrade2.getTicker()).thenReturn("MEGA");
		when(mockTrade3.getTicker()).thenReturn("NGL");
		when(mockTrade4.getTicker()).thenReturn("TRX");
		when(mockTrade1.getPrice()).thenReturn(100.0);
		when(mockTrade2.getPrice()).thenReturn(100.0);
		when(mockTrade3.getPrice()).thenReturn(100.0);
		when(mockTrade4.getPrice()).thenReturn(100.0);
		trades.add(mockTrade1);
		aggregator.calculateClosePriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade2);
		aggregator.calculateClosePriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade3);
		aggregator.calculateClosePriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade4);
		aggregator.calculateClosePriceOfTicker(trades);
		double closePrice = aggregator.calculateClosePriceOfMarket();
		assertEquals(100.0, closePrice);
	}

	@Test
	public void test_calculateHighestPriceOfMarket_returnsZero_ifNoLastKnownHighestPricesOfAnyTickersExist() {
		double highestPrice = aggregator.calculateHighestPriceOfMarket();
		assertEquals(0.0, highestPrice);
	}

	@Test
	public void test_calculateHighestPriceOfMarket_returnsWeightetSumOfHighestPrices_ifHighestPriceOfEveryMarketTickerExists() {
		when(mockTrade1.getTicker()).thenReturn("ABC");
		when(mockTrade2.getTicker()).thenReturn("MEGA");
		when(mockTrade3.getTicker()).thenReturn("NGL");
		when(mockTrade4.getTicker()).thenReturn("TRX");
		when(mockTrade1.getPrice()).thenReturn(100.0);
		when(mockTrade2.getPrice()).thenReturn(100.0);
		when(mockTrade3.getPrice()).thenReturn(100.0);
		when(mockTrade4.getPrice()).thenReturn(100.0);
		trades.add(mockTrade1);
		aggregator.calculateHighestPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade2);
		aggregator.calculateHighestPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade3);
		aggregator.calculateHighestPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade4);
		aggregator.calculateHighestPriceOfTicker(trades);
		double highestPrice = aggregator.calculateHighestPriceOfMarket();
		assertEquals(100.0, highestPrice);
	}

	@Test
	public void test_calculateLowestPriceOfMarket_returnsZero_ifNoLastKnownLowestPricesOfAnyTickersExist() {
		double lowestPrice = aggregator.calculateLowestPriceOfMarket();
		assertEquals(0.0, lowestPrice);
	}

	@Test
	public void test_calculateLowestPriceOfMarket_returnsWeightetSumOfLowestPrices_ifLowestPriceOfEveryMarketTickerExists() {
		when(mockTrade1.getTicker()).thenReturn("ABC");
		when(mockTrade2.getTicker()).thenReturn("MEGA");
		when(mockTrade3.getTicker()).thenReturn("NGL");
		when(mockTrade4.getTicker()).thenReturn("TRX");
		when(mockTrade1.getPrice()).thenReturn(100.0);
		when(mockTrade2.getPrice()).thenReturn(100.0);
		when(mockTrade3.getPrice()).thenReturn(100.0);
		when(mockTrade4.getPrice()).thenReturn(100.0);
		trades.add(mockTrade1);
		aggregator.calculateLowestPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade2);
		aggregator.calculateLowestPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade3);
		aggregator.calculateLowestPriceOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade4);
		aggregator.calculateLowestPriceOfTicker(trades);
		double lowestPrice = aggregator.calculateLowestPriceOfMarket();
		assertEquals(100.0, lowestPrice);
	}

	@Test
	public void test_calculateDailyTradedVolumeOfMarket_returnsZero_ifNoLastKnownDailyTradedVolumeOfAnyTickersExist() {
		double dailyTradedVolume = aggregator.calculateDailyTradedVolumeOfMarket();
		assertEquals(0.0, dailyTradedVolume);
	}

	@Test
	public void test_calculateDailyTradedVolumeOfMarket_returnsWeightetSumOfDailyTradedVolume_ifDailyTradedVolumeOfEveryMarketTickerExists() {
		when(mockTrade1.getTicker()).thenReturn("ABC");
		when(mockTrade2.getTicker()).thenReturn("MEGA");
		when(mockTrade3.getTicker()).thenReturn("NGL");
		when(mockTrade4.getTicker()).thenReturn("TRX");
		when(mockTrade1.getPrice()).thenReturn(50.0);
		when(mockTrade2.getPrice()).thenReturn(50.0);
		when(mockTrade3.getPrice()).thenReturn(50.0);
		when(mockTrade4.getPrice()).thenReturn(50.0);
		when(mockTrade1.getNrOfSecuritiesTraded()).thenReturn(2);
		when(mockTrade2.getNrOfSecuritiesTraded()).thenReturn(2);
		when(mockTrade3.getNrOfSecuritiesTraded()).thenReturn(2);
		when(mockTrade4.getNrOfSecuritiesTraded()).thenReturn(2);
		trades.add(mockTrade1);
		aggregator.calculateDailyTradedVolumeOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade2);
		aggregator.calculateDailyTradedVolumeOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade3);
		aggregator.calculateDailyTradedVolumeOfTicker(trades);
		trades = new ArrayList<>();
		trades.add(mockTrade4);
		aggregator.calculateDailyTradedVolumeOfTicker(trades);
		double dailyTradedVolume = aggregator.calculateDailyTradedVolumeOfMarket();
		assertEquals(100.0, dailyTradedVolume);
	}

}

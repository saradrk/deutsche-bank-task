package dailyMarketAggregates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TradeDayTest {

	TradeDay tradeDay;
	@Mock
	Trade mockTrade1;
	@Mock
	Trade mockTrade2;
	LocalDate testDate = LocalDate.parse("2023-06-01");
	LocalTime earlierTestTime = LocalTime.parse("09:00:01");
	LocalTime laterTestTime = LocalTime.parse("09:00:02");
	String testTicker1 = "TT1";
	String testTicker2 = "TT2";

	@BeforeEach
	public void setUp() {
		tradeDay = new TradeDay(testDate);

	}

	@Test
	public void test_getTradesPerTicker_returnsEmptyTradesMap_ifNoTradesHaveBeenAdded() {
		Map<String, List<Trade>> tradesPerTicker = tradeDay.getTradesPerTicker();
		assertEquals(0, tradesPerTicker.size());
	}

	@Test
	public void test_getTradesPerTicker_returnsMapOfSizeOne_afterOneTradeHasBeenAdded() {
		tradeDay.addTrade(mockTrade1);
		Map<String, List<Trade>> tradesPerTicker = tradeDay.getTradesPerTicker();
		assertEquals(1, tradesPerTicker.size());
	}

	@Test
	public void test_getTradesPerTicker_returnsMapOfSizeTwo_afterTwoTradesOfDifferentTickersHaveBeenAdded() {
		when(mockTrade1.getTicker()).thenReturn(testTicker1);
		when(mockTrade2.getTicker()).thenReturn(testTicker2);
		tradeDay.addTrade(mockTrade1);
		tradeDay.addTrade(mockTrade2);
		Map<String, List<Trade>> tradesPerTicker = tradeDay.getTradesPerTicker();
		assertEquals(2, tradesPerTicker.size());
	}

}

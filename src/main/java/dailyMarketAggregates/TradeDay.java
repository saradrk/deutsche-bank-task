package dailyMarketAggregates;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TradeDay contains methods to handle the market trades of a day.
 * 
 * @author sara.derakhshani
 *
 */
public class TradeDay {

	private LocalDate date;
	private Map<String, List<Trade>> tradesPerTicker = new HashMap<>();

	public TradeDay(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return HashMap that maps lists of trades of this trade day to the tickers
	 *         they belong to.
	 */
	public Map<String, List<Trade>> getTradesPerTicker() {
		return tradesPerTicker;
	}

	/**
	 * Adds trade to the list of trades of its ticker.
	 * 
	 * @param trade Trade object.
	 */
	public void addTrade(Trade trade) {
		if (tradesPerTicker.containsKey(trade.getTicker())) {
			tradesPerTicker.get(trade.getTicker()).add(trade);
		} else {
			List<Trade> trades = new ArrayList<>();
			trades.add(trade);
			tradesPerTicker.put(trade.getTicker(), trades);
		}
	}

}

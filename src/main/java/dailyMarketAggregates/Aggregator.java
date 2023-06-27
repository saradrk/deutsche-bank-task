package dailyMarketAggregates;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregator contains methods to calculate daily aggregates for the market.
 * 
 * @author sara.derakhshani
 *
 */
public class Aggregator {

	private Map<String, Double> lastKnownOpenPrices = new HashMap<>();
	private Map<String, Double> lastKnownClosePrices = new HashMap<>();
	private Map<String, Double> lastKnownHighestPrices = new HashMap<>();
	private Map<String, Double> lastKnownLowestPrices = new HashMap<>();
	private Map<String, Double> lastKnownDailyTradedVolume = new HashMap<>();

	public Aggregator() {
	}

	private void addLastKnownOpenPriceOfTicker(String ticker, double price) {
		this.lastKnownOpenPrices.put(ticker, price);
	}

	private void addLastKnownClosePriceOfTicker(String ticker, double price) {
		this.lastKnownClosePrices.put(ticker, price);
	}

	private void addLastKnownHighestPriceOfTicker(String ticker, double price) {
		this.lastKnownHighestPrices.put(ticker, price);
	}

	private void addLastKnownLowestPriceOfTicker(String ticker, double price) {
		this.lastKnownLowestPrices.put(ticker, price);
	}

	private void addLastKnownDailyTradedVolumeOfTicker(String ticker, double tradedVolume) {
		this.lastKnownDailyTradedVolume.put(ticker, tradedVolume);
	}

	/**
	 * Returns the price of the earliest trade of a day for a market ticker if
	 * trades of the day exist and 0.0 otherwise.
	 * 
	 * @param trades A list of all trades of a day of a ticker.
	 * @return open price of the trading day of the ticker.
	 */
	public double calculateOpenPriceOfTicker(List<Trade> trades) {
		if (trades.size() > 0) {
			// sort trades by time from earliest to latest time, so first list position
			// contains trade with open price
			Collections.sort(trades, (t1, t2) -> t1.getTime().compareTo(t2.getTime()));
			double openPrice = trades.get(0).getPrice();
			this.addLastKnownOpenPriceOfTicker(trades.get(0).getTicker(), openPrice);
			return openPrice;
		} else {
			return 0.0;
		}
	}

	/**
	 * Returns the price of the latest trade of a day for a market ticker if trades
	 * of the day exist and 0.0 otherwise.
	 * 
	 * @param trades A list of all trades of a day of a ticker.
	 * @return close price of the trading day of the ticker.
	 */
	public double calculateClosePriceOfTicker(List<Trade> trades) {
		if (trades.size() > 0) {
			Collections.sort(trades, (t1, t2) -> t1.getTime().compareTo(t2.getTime()));
			// sort trades by time from earliest to latest time, so last list position
			// contains trade with close price
			double closePrice = trades.get(trades.size() - 1).getPrice();
			this.addLastKnownClosePriceOfTicker(trades.get(trades.size() - 1).getTicker(), closePrice);
			return closePrice;
		} else {
			return 0.0;
		}
	}

	/**
	 * Returns the highest trading price of a day for a market ticker if trades of
	 * the day exist and 0.0 otherwise.
	 * 
	 * @param trades A list of all trades of a day of a ticker.
	 * @return highest price of the trading day of the ticker.
	 */
	public double calculateHighestPriceOfTicker(List<Trade> trades) {
		if (trades.size() > 0) {
			// sort trades by price in ascending order, so last list position
			// contains trade with highest price
			Collections.sort(trades, (t1, t2) -> Double.compare(t1.getPrice(), t2.getPrice()));
			double highestPrice = trades.get(trades.size() - 1).getPrice();
			this.addLastKnownHighestPriceOfTicker(trades.get(trades.size() - 1).getTicker(), highestPrice);
			return highestPrice;
		} else {
			return 0.0;
		}
	}

	/**
	 * Returns the lowest trading price of a day for a market ticker if trades of
	 * the day exist and 0.0 otherwise.
	 * 
	 * @param trades A list of all trades of a day of a ticker.
	 * @return lowest price of the trading day of the ticker.
	 */
	public double calculateLowestPriceOfTicker(List<Trade> trades) {
		if (trades.size() > 0) {
			// sort trades by price in ascending order, so first list position
			// contains trade with lowest price
			Collections.sort(trades, (t1, t2) -> Double.compare(t1.getPrice(), t2.getPrice()));
			double lowestPrice = trades.get(0).getPrice();
			this.addLastKnownLowestPriceOfTicker(trades.get(0).getTicker(), lowestPrice);
			return lowestPrice;
		} else {
			return 0.0;
		}
	}

	/**
	 * Returns the daily traded volume of a day for a market ticker if trades of the
	 * day exist and 0.0 otherwise. Daily traded volume is calculated by sum of
	 * products of price and number of securities traded.
	 * 
	 * @param trades A list of all trades of a day of a ticker.
	 * @return daily traded volume of the trading day of the ticker.
	 */
	public double calculateDailyTradedVolumeOfTicker(List<Trade> trades) {
		if (trades.size() > 0) {
			double dailyTradedVolume = trades.stream()
					.mapToDouble(trade -> trade.getPrice() * (double) trade.getNrOfSecuritiesTraded()).sum();
			this.addLastKnownDailyTradedVolumeOfTicker(trades.get(0).getTicker(), dailyTradedVolume);
			return dailyTradedVolume;
		} else {
			return 0.0;
		}
	}

	/**
	 * Returns the open price for market index if trades of the day exist. Open
	 * price for market index is a weighted sum of ticker prices at an instant. Uses
	 * last known open prices of each ticker for calculation. Returns 0.0 if no last
	 * known prices exist.
	 * 
	 * @return open price of the trading day of the market.
	 */
	public double calculateOpenPriceOfMarket() {
		double openPrice = 0.0;
		for (Map.Entry<String, Double> entry : this.lastKnownOpenPrices.entrySet()) {
			openPrice += this.multiplyIndexWeight(entry.getKey(), entry.getValue());
		}
		return openPrice;
	}

	/**
	 * Returns the close price for market index if trades of the day exist. Close
	 * price for market index is a weighted sum of ticker prices at an instant. Uses
	 * last known close prices of each ticker for calculation. Returns 0.0 if no
	 * last known prices exist.
	 * 
	 * @return close price of the trading day of the market.
	 */
	public double calculateClosePriceOfMarket() {
		double closePrice = 0.0;
		for (Map.Entry<String, Double> entry : this.lastKnownClosePrices.entrySet()) {
			closePrice += this.multiplyIndexWeight(entry.getKey(), entry.getValue());
		}
		return closePrice;
	}

	/**
	 * Returns the highest price for market index if trades of the day exist.
	 * Highest price for market index is a weighted sum of ticker prices at an
	 * instant. Uses last known highest prices of each ticker for calculation.
	 * Returns 0.0 if no last known prices exist.
	 * 
	 * @return highest price of the trading day of the market.
	 */
	public double calculateHighestPriceOfMarket() {
		double highestPrice = 0.0;
		for (Map.Entry<String, Double> entry : this.lastKnownHighestPrices.entrySet()) {
			highestPrice += this.multiplyIndexWeight(entry.getKey(), entry.getValue());
		}
		return highestPrice;
	}

	/**
	 * Returns the lowest price for market index if trades of the day exist. Lowest
	 * price for market index is a weighted sum of ticker prices at an instant. Uses
	 * last known lowest prices of each ticker for calculation. Returns 0.0 if no
	 * last known prices exist.
	 * 
	 * @return lowest price of the trading day of the market.
	 */
	public double calculateLowestPriceOfMarket() {
		double lowestPrice = 0.0;
		for (Map.Entry<String, Double> entry : this.lastKnownLowestPrices.entrySet()) {
			lowestPrice += this.multiplyIndexWeight(entry.getKey(), entry.getValue());
		}
		return lowestPrice;
	}

	/**
	 * Returns the daily traded volume for market index if trades of the day exist.
	 * Daily traded volume for market index is a weighted sum of ticker daily traded
	 * volumes at an instant. Uses last known daily traded volume of each ticker for
	 * calculation. Returns 0.0 if no last known volumes exist.
	 * 
	 * @return daily traded volume of the trading day of the market.
	 */
	public double calculateDailyTradedVolumeOfMarket() {
		double dailyTradedVolume = 0.0;
		for (Map.Entry<String, Double> entry : this.lastKnownDailyTradedVolume.entrySet()) {
			dailyTradedVolume += this.multiplyIndexWeight(entry.getKey(), entry.getValue());
		}
		return dailyTradedVolume;
	}

	private double multiplyIndexWeight(String ticker, double value) {
		switch (ticker) {
		case "ABC":
			return value * 0.1;
		case "MEGA":
			return value * 0.3;
		case "NGL":
			return value * 0.4;
		case "TRX":
			return value * 0.2;
		}
		return 0.0;
	}

}

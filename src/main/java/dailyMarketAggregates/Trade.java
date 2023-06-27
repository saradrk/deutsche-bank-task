package dailyMarketAggregates;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Trade contains methods to get data of a trade.
 * 
 * @author sara.derakhshani
 *
 */
public class Trade {

	private String ticker;
	private LocalDate date;
	private LocalTime time;
	private double price;
	private int nrOfSecuritiesTraded;

	public Trade(String ticker, LocalDate date, LocalTime time, double price, int nrOfSecuritiesTraded) {
		this.ticker = ticker;
		this.date = date;
		this.time = time;
		this.price = price;
		this.nrOfSecuritiesTraded = nrOfSecuritiesTraded;
	}

	public String getTicker() {
		return ticker;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

	public double getPrice() {
		return price;
	}

	public int getNrOfSecuritiesTraded() {
		return nrOfSecuritiesTraded;
	}

}

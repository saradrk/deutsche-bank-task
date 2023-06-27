package dailyMarketAggregates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Main {

	/*
	 * Iterates market historical log. While trades belong to the same day, the
	 * trades are accumulated in trade day object. When all trades of a day are
	 * gathered, the daily aggregates are calculated and printed.
	 */
	public static void main(String[] args) {
		String file = "src/main/data/test-market.csv";
		String currentLine = null;
		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);) {
			Aggregator aggregator = new Aggregator();
			// Initialize first trade day
			TradeDay tradeDay = new TradeDay(null);
			while ((currentLine = bufferedReader.readLine()) != null) {
				String[] tradeInfo = currentLine.split(";");
				String[] dateTime = tradeInfo[0].split(" ");
				LocalDate date = LocalDate.parse(dateTime[0]);
				LocalTime time = LocalTime.parse(dateTime[1]);
				Trade trade = new Trade(tradeInfo[1], date, time, Double.parseDouble(tradeInfo[2].replace(',', '.')),
						Integer.parseInt(tradeInfo[3]));
				// if trade day is empty create new trade day of last extracted date
				if (tradeDay.getDate() == null) {
					tradeDay = new TradeDay(date);
					tradeDay.addTrade(trade);
					// keep on gathering trades
				} else if (date.equals(tradeDay.getDate())) {
					tradeDay.addTrade(trade);
					// trades of new date appear in file, so print recent daily aggregates and
					// create new trade day
				} else {
					printTradeDayAggregates(aggregator, tradeDay);
					tradeDay = new TradeDay(null);
				}
			}
			// Print the last occurring trade day
			if (tradeDay.getDate() != null) {
				printTradeDayAggregates(aggregator, tradeDay);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void printTradeDayAggregates(Aggregator aggregator, TradeDay tradeDay) {
		System.out.println("####" + tradeDay.getDate() + "####\n");
		for (Map.Entry<String, List<Trade>> tickerEntry : tradeDay.getTradesPerTicker().entrySet()) {
			System.out.println("--" + tickerEntry.getKey() + "--");
			double openPrice = aggregator.calculateOpenPriceOfTicker(tickerEntry.getValue());
			printAggregateNameAndResult("Open price", openPrice);
			double closePrice = aggregator.calculateClosePriceOfTicker(tickerEntry.getValue());
			printAggregateNameAndResult("Close price", closePrice);
			double highestPrice = aggregator.calculateHighestPriceOfTicker(tickerEntry.getValue());
			printAggregateNameAndResult("Highest price", highestPrice);
			double lowestPrice = aggregator.calculateLowestPriceOfTicker(tickerEntry.getValue());
			printAggregateNameAndResult("Lowest price", lowestPrice);
			double dailyTradedVolume = aggregator.calculateDailyTradedVolumeOfTicker(tickerEntry.getValue());
			printAggregateNameAndResult("Daily traded volume", dailyTradedVolume);
			System.out.println("\t");
		}
		System.out.println("--INDEX--");
		printAggregateNameAndResult("Open price", aggregator.calculateOpenPriceOfMarket());
		printAggregateNameAndResult("Close price", aggregator.calculateClosePriceOfMarket());
		printAggregateNameAndResult("Highest price", aggregator.calculateHighestPriceOfMarket());
		printAggregateNameAndResult("Lowest price", aggregator.calculateLowestPriceOfMarket());
		printAggregateNameAndResult("Daily traded volume", aggregator.calculateDailyTradedVolumeOfMarket());
		System.out.println("\n");
	}

	public static void printAggregateNameAndResult(String nameOfAggregate, double result) {
		if (result > 0.0) {
			System.out.println(nameOfAggregate + ": " + result);
		} else {
			System.out.println(nameOfAggregate + ": " + "N/A");
		}
	}

}

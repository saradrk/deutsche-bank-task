# Deutsche Bank Task
Deutsche Bank Task is a project with the purpose to calculate and display daily aggregates of a market histroical log. For every log date and traded ticker daily values for open price, close price, highest price, lowest price and daily traded volume are printed as well as the daily values for the market index. This repository contains the implementation of this task in form of a Java package as well as an historical log to run the program examplary. 

## Technologies
Project is created with:  
- Java 1.8
- Eclipse IDE Version: 2023-03

## Repository Overview
### Javadoc
Read for further documentation:  

``doc/index.html``

### dailyMarketAggregates package
Implementation of the task:  

``src/main/com/fdmgroup/dailyMarketAggregates/Aggregator.java``  

``src/main/com/fdmgroup/dailyMarketAggregates/Main.java``  

``src/main/com/fdmgroup/dailyMarketAggregates/Trade.java``  

``src/main/com/fdmgroup/dailyMarketAggregates/TradeDay.java``  

### Data
The examplary historical market log:  

``src/main/data/test-market.csv``

### Testing
Run:   

``src/test/java/com/fdmgroup/dailyMarketAggregates/AggregatorTest.java``  

``src/test/java/com/fdmgroup/dailyMarketAggregates/TradeDayTest.java``  

## Running the program
Clone this repository and run:  

``src/main/com/fdmgroup/dailyMarketAggregates/Main.java``
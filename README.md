# Deutsche Bank Task
Deutsche Bank Task is a project with the purpose to calculate and display daily aggregates of a market historical log. For every log date and traded ticker, daily values for open price, close price, highest price, lowest price, and daily traded volume are printed as well as the daily values for the market index. This repository contains the implementation of this task in the form of a Java package as well as a historical log to run the program exemplary. 

## Technologies
The project is created with:  
- Java 1.8
- Eclipse IDE Version: 2023-03

## Repository Overview
Clone this repository and find the following:

### dailyMarketAggregates package
Implementation of the task:  

``src/main/com/fdmgroup/dailyMarketAggregates/Aggregator.java``   

``src/main/com/fdmgroup/dailyMarketAggregates/Trade.java``  

``src/main/com/fdmgroup/dailyMarketAggregates/TradeDay.java``  

*Run the program:* 

*``src/main/com/fdmgroup/dailyMarketAggregates/Main.java``*

### Data
The exemplary historical market log:  

``src/main/data/test-market.csv``

### Dependencies  

``pom.xml``

### Javadoc
Read for further documentation:  

``doc/index.html``

### Tests
Run:   

``src/test/java/com/fdmgroup/dailyMarketAggregates/AggregatorTest.java``  

``src/test/java/com/fdmgroup/dailyMarketAggregates/TradeDayTest.java``  

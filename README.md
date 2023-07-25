# Web Auction Application

An application for an online auction. It implements the ability to add/modify/delete new Lots and change status on:
* Created (Lot created, but bidding doesn't commence.)
* Stopped (Bidding has been stopped.)
* Started (Bidding has started.)

Additionally, users can place bids on Lots, and all bid data is stored in the database. The final price of a Lot in the application is calculated using the formula:
```
number of bids * bid price + initial price of the lot. 
```
The application allows gathering various statistical information about Lots and bids, including exporting it in CSV format.

Technology stack:
* Spring Boot
* PostgreSQL
* JPA and Hibernate
* SLF4 and logback(with AOP)
* Liquibase
* Mock and JUnit
# TrainDelayMonitor
## Introduction
This is a Spring Boot application. It makes use of Selenium tools to scrawl delayed trains from the MTA website (https://new.mta.info/).

Whenever there is a line changing its delay status (either from not delayed to delay or delay to not delay), a new line will be printed to the console. And there are two available endpoints to interact with this application: 1) "/status/{line}" to know if this line is delayed or not at the moment (~within these 10 seconds) 2) "/uptime/{line}" to know the fraction of the time today this line is not delayed. Our program only records accumulated delayed time during run-time and calculates the fraction of not delayed time by dividing accumulated delayed time with the amount of time that has been passed today.

## Here is a short demo of the application.


## Class Design
![](https://github.com/Yiranluc/TrainDelayMonitor/blob/main/trainDelayMonitor.png)

## Future Improvements
1. Add a persistent database to keep these delayed trains' info when the application stops/restarts.
2. If the application scales, separate the logic of the endpoints (two controller classes) from the scrawling logic (webScrawlingScheduler class) into different servers.
3. Deploy the application on the cloud.

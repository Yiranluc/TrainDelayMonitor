# TrainDelayMonitor
## Introduction
This is a Spring Boot application. It makes use of Selenium tools to scrawl delayed trains from the MTA website (https://new.mta.info/). 

Whenever there is a line changing its delay status (either from not delayed to delay or delay to not delay), a new line will be printed to the console. And there are two available endpoints to interact with this application: 1) "/status/{line}" to know if this line is delayed or not at the moment (~within these 10 seconds) 2) "/uptime/{line}" to know the fraction of the time today this line is not delayed. Our program only records accumulated delayed time during run-time and calculates the fraction of not delayed time by dividing accumulated delayed time by the amount of time that has passed today.

## Screenshots of the application.
![image](https://github.com/Yiranluc/TrainDelayMonitor/assets/45097607/2b3bfaf8-1464-4e8b-9a31-84ff94fb806f)
![image](https://github.com/Yiranluc/TrainDelayMonitor/assets/45097607/cf75fcd2-c725-40aa-8c60-72cfdf9fee3d)
![image](https://github.com/Yiranluc/TrainDelayMonitor/assets/45097607/08ca4c29-72a5-4f0c-a51b-2769f5832f69)
![image](https://github.com/Yiranluc/TrainDelayMonitor/assets/45097607/36ecb240-343a-4fea-afee-1963add7fb59)
![image](https://github.com/Yiranluc/TrainDelayMonitor/assets/45097607/23dade61-05f8-4f33-9bb1-b7ca2141f8ed)


## Class Design
![](https://github.com/Yiranluc/TrainDelayMonitor/blob/main/trainDelayMonitor.png)

## Future Improvements
1. Add a persistent database to keep these delayed trains' info when the application stops/restarts.
2. Apply multi-threads and WebServerPool to scrawl the data from the MTA website to achieve more timely data updates. However, the bottleneck lies in web scrawling itself since Selenium needs several seconds to load the web page and thus find the target elements.
3. If the application scales, separate the logic of the endpoints (two controller classes) from the scrawling logic (webScrawlingScheduler class) into different servers.
4. Deploy the application on the cloud.
5. Add some tests.

## Other Attempts
I have previously tried getting data from MTA Real Time Data Feed (https://api.mta.info/#/landing). According to their documentation (chrome-extension://efaidnbmnnnibpcajpcglclefindmkaj/https://api.mta.info/GTFS.pdf), they would post "Alert" messages regarding delayed trains. However, after comparing parsed data from their API and the delayed train on the MTA official website, their API provides very limited alerts of delayed trains, which are much less than the delayed trains shown on their website. And this is confirmed by a public transit enthusiast (https://www.youtube.com/watch?v=Vx0SJj8yVrM by Sunny Ng, 6'50'' to 7'10'') who has done similar work two years ago. Without official API's assistance, I decided to directly scrape the data from the MTA website instead. 

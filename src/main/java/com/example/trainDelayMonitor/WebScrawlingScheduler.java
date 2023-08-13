package com.example.trainDelayMonitor;

import com.example.trainDelayMonitor.objects.DelayedTrainsInfo;
import com.example.trainDelayMonitor.objects.ServiceStatues;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebScrawlingScheduler {

  private final WebDriver webDriver;
  private final DelayedTrainsInfo delayedTrainsInfo;

  @Autowired
  public WebScrawlingScheduler(DelayedTrainsInfo delayedTrainsInfo, WebDriver webDriver) {
    this.webDriver = webDriver;
    this.delayedTrainsInfo = delayedTrainsInfo;
  }

  /**
   * Every 10 seconds, send a request to MTA website to scrawl the delayed train info.*
   */
  @Scheduled(fixedRate = 10000) // 10 seconds
  public void sendGetRequest() {
    try {
      WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
      webDriver.get(AppConfig.MTA_URL);

      // Wait for the web page to load for 5 seconds
      // before scrawling the elements showing delayed trains.
      List<WebElement> h5Elements = wait.until(
          ExpectedConditions.presenceOfAllElementsLocatedBy(
              By.cssSelector(".by-status h5")));

      Set<String> scrawledTrainSet = new HashSet<>();

      for (WebElement h5 : h5Elements) {
        if (h5.getText().equals(ServiceStatues.DELAYS.title)) {
          WebElement outerElement = h5.findElement(By.xpath(".."));
          List<WebElement> delayedSubways = outerElement
              .findElements(By.cssSelector(".mta-subway-alert"));

          for (WebElement delayedSubway : delayedSubways) {
            scrawledTrainSet.add(delayedSubway.getText());
          }
        }
      }

      LocalDateTime currentTimestamp = LocalDateTime.now();
      String isoTimestamp = currentTimestamp.format(AppConfig.isoFormatter);

      this.delayedTrainsInfo.compare(scrawledTrainSet, isoTimestamp);
      this.delayedTrainsInfo.resetTrainsAccumulatedDelayTimeIfNeeded(isoTimestamp);

    } catch (Exception e) {
      // Most likely no class called "by-status" is found in the web page or there is no h5 element
      // within the "by-status" class, so we have the exception above.
      System.out.println(e.getMessage());
    }
  }

}

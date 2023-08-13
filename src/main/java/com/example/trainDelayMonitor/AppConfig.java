package com.example.trainDelayMonitor;

import com.example.trainDelayMonitor.objects.DelayedTrainsInfo;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  public static final String MTA_URL = "https://new.mta.info/";
  // change your chrome driver path here.
  public static final String CHROME_DRIVER_PATH = "C:/Users/wangy/Downloads/chromedriver-win64/chromedriver.exe";
  public static final String LINE_NOT_AVAILABLE_RESPONSE = "The line you entered is not available. Please enter a valid line";
  public static final DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;
  public static final ZoneId NEW_YORK_ZONE = ZoneId.of("America/New_York");
  public static final LocalTime START_OF_A_DAY = LocalTime.of(0, 0, 0); // Midnight


  @Bean
  public DelayedTrainsInfo delayedTrainSet() {
    return new DelayedTrainsInfo();
  }

  @Bean
  public WebDriver webDriver() {
    System.setProperty("webdriver.chrome.driver", AppConfig.CHROME_DRIVER_PATH);
    return new ChromeDriver();
  }
}

package com.example.trainDelayMonitor.objects;

import com.example.trainDelayMonitor.AppConfig;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class DelayedTrain {

  private final String routeId;
  private long accumulatedDelayTime;
  private String previousCheckInTimeStamp;

  public DelayedTrain(String routeId) {
    this.routeId = routeId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DelayedTrain that = (DelayedTrain) o;
    return routeId.equals(that.routeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(routeId);
  }

  public long getAccumulatedDelayTime() {
    return accumulatedDelayTime;
  }

  public void setPreviousCheckInTimeStamp(String previousCheckInTimeStamp) {
    this.previousCheckInTimeStamp = previousCheckInTimeStamp;
  }

  @Override
  public String toString() {
    return "objects.DelayedTrain{" +
        "routeId='" + routeId + '\'' +
        ", accumulatedDelayTime='" + accumulatedDelayTime + '\'' +
        ", previousDelayStartTime='" + previousCheckInTimeStamp + '\'' +
        '}';
  }

  public void updateAccumulatedDelayTime(String createdTimeStamp) {
    LocalDateTime startTime = LocalDateTime.parse(this.previousCheckInTimeStamp,
        AppConfig.isoFormatter);
    LocalDateTime currentTime = LocalDateTime.parse(createdTimeStamp, AppConfig.isoFormatter);
    this.accumulatedDelayTime += Duration.between(startTime, currentTime).getSeconds();
//    System.out.println("accumalted time for " + this.routeId + " is: " + this.accumulatedDelayTime);
  }

  public void resetAccumulatedDelayTime() {
    this.accumulatedDelayTime = 0;
  }
}

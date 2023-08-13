package com.example.trainDelayMonitor.objects;

import com.example.trainDelayMonitor.AppConfig;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DelayedTrainsInfo {

  private final ConcurrentHashMap<String, DelayedTrain> mapOfAllTrains;
  private ConcurrentHashMap<String, DelayedTrain> currentDelayedTrains;

  public DelayedTrainsInfo() {
    currentDelayedTrains = new ConcurrentHashMap<>();
    mapOfAllTrains = new ConcurrentHashMap<>();
  }

  private static long calculateSecondsPassedToday() {
    LocalTime currentTimeInNewYork = LocalTime.now(AppConfig.NEW_YORK_ZONE);
    Duration duration = Duration.between(AppConfig.START_OF_A_DAY, currentTimeInNewYork);
    return duration.getSeconds();
  }

  public boolean checkIfLineIsCurrentlyDelayed(String line) {
    return currentDelayedTrains.containsKey(line);
  }

  public int numberofCurrentDelayedTrains() {
    return currentDelayedTrains.size();

  }

  /**
   * Compare the latest scrawled delayed trains with the trains within our currentDelayedTrains map
   * in order to print the newly delayed lines and recovered lines to the console. In addition,
   * update the trains in currentDelayedTrains and mapOfAllTrains maps with updated
   * previousCheckInTimeStamp and accumulated delayed time.
   *
   * @param scrawledTrainSet Newly scrawled delayed trains from the MTA website
   * @param createdTimeStamp The timestamp when the new delayed trains are scrawled from the
   *                         website.
   */
  public void compare(Set<String> scrawledTrainSet, String createdTimeStamp) {

    // Delayed lines
    for (String line : scrawledTrainSet) {
      if (!currentDelayedTrains.containsKey(line)) {
        System.out.println("Line " + line + " is experiencing delays");

        DelayedTrain train = this.mapOfAllTrains.containsKey(line) ?
            this.mapOfAllTrains.get(line) : new DelayedTrain(line);
        train.setPreviousCheckInTimeStamp(createdTimeStamp);
        this.mapOfAllTrains.put(line, train);

      }
    }

    // Recovered lines
    for (Map.Entry<String, DelayedTrain> entry : currentDelayedTrains.entrySet()) {
      DelayedTrain train = mapOfAllTrains.get(entry.getKey());
      // Update the accumulated delay time of each train in currentDelayedTrains map
      train.updateAccumulatedDelayTime(createdTimeStamp);
      train.setPreviousCheckInTimeStamp(createdTimeStamp);
      if (!scrawledTrainSet.contains(entry.getKey())) {
        System.out.println("Line " + entry.getKey() + " is now recovered");
      }
    }

    // Update currentDelayedTrains
    currentDelayedTrains = new ConcurrentHashMap<>();
    scrawledTrainSet.stream().forEach(line -> {
      currentDelayedTrains.put(line, mapOfAllTrains.get(line));
    });

  }

  /**
   * Reset the accumulated delay time to 0 for each train/line at the start of every day.*
   *
   * @param currentTimeStamp current time stamp
   */
  public void resetTrainsAccumulatedDelayTimeIfNeeded(String currentTimeStamp) {
    Duration duration = Duration.between(AppConfig.START_OF_A_DAY,
        LocalDateTime.parse(currentTimeStamp, AppConfig.isoFormatter));
    if (duration.getSeconds() < 10) {
      mapOfAllTrains.entrySet().forEach(e -> {
        if (e.getValue() != null) {
          e.getValue().resetAccumulatedDelayTime();
        }
      });
    }

  }

  public double getFractionofNotDelay(String line) {
    // if there is line in the mapOfAllTrains, then the line has no delays since the program starts.
    if(!mapOfAllTrains.containsKey(line)) return 1.0;

    DelayedTrain train = mapOfAllTrains.get(line);
    long secondsPassedToday = calculateSecondsPassedToday();
    System.out.println("seconds passed today: " + secondsPassedToday);
    return secondsPassedToday == 0 ? 1.0 :
        1.0 - (double) (train.getAccumulatedDelayTime() / secondsPassedToday);
  }
}

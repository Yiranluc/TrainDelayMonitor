package com.example.trainDelayMonitor.controllers;

import com.example.trainDelayMonitor.AppConfig;
import com.example.trainDelayMonitor.objects.AvailableTrains;
import com.example.trainDelayMonitor.objects.DelayedTrainsInfo;
import java.util.Locale;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

  private final DelayedTrainsInfo delayedTrainsInfo;

  public StatusController(DelayedTrainsInfo delayedTrainsInfo) {
    this.delayedTrainsInfo = delayedTrainsInfo;
  }

  @GetMapping("/status/{line}")
  public ResponseEntity<String> getStatusResponse(@PathVariable String line) {
    line = line.toUpperCase(Locale.ROOT);
    if (!AvailableTrains.checkLineExists(line)) {
      return ResponseEntity.badRequest().body(AppConfig.LINE_NOT_AVAILABLE_RESPONSE);
    } else {
      boolean delayedInfo = this.delayedTrainsInfo.checkIfLineIsCurrentlyDelayed(line);

      if (delayedInfo) {
        return ResponseEntity.ok("Line " + line + " is delayed");
      } else {
        return ResponseEntity.ok("Line " + line + " is not delayed");
      }
    }

  }
}

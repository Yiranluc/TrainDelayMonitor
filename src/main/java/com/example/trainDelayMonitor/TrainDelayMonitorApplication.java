package com.example.trainDelayMonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrainDelayMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainDelayMonitorApplication.class, args);
	}

}

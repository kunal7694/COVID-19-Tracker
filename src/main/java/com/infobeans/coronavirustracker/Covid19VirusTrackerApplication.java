package com.infobeans.coronavirustracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Covid19VirusTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19VirusTrackerApplication.class, args);
	}
}

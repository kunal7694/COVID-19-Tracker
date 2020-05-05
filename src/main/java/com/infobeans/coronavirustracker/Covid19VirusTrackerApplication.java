package com.infobeans.coronavirustracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.infobeans.coronavirustracker.services.CoronaVirusDataServiceImpl;

@SpringBootApplication
@EnableScheduling
public class Covid19VirusTrackerApplication {

	@Autowired
	CoronaVirusDataServiceImpl coronaVirusDataServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(Covid19VirusTrackerApplication.class, args);

	}

}

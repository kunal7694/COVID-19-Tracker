package com.infobeans.coronavirustracker.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infobeans.coronavirustracker.constants.ApplicationConstant;
import com.infobeans.coronavirustracker.models.LocationStatus;
import com.infobeans.coronavirustracker.services.CoronaVirusTraker;

@RestController
@RequestMapping(value = ApplicationConstant.BASE_URL)
public class CoronaVirusTrackerRestController {

	@Autowired
	CoronaVirusTraker coronaVirusTraker;

	@GetMapping(value = ApplicationConstant.GLOBAL_CONFIRMED_CORONA_VIRUS_DATA)
	public List<LocationStatus> getAllGlobalConfirmedCoronaVirusAffectedCasesData() {
		return coronaVirusTraker.getObject().getTotalConfirmedCases();
	}

}

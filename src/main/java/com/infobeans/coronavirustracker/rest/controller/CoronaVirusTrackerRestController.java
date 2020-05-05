package com.infobeans.coronavirustracker.rest.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infobeans.coronavirustracker.api.GlobalConfirmedCoronaVirusData;
import com.infobeans.coronavirustracker.constants.ApplicationConstant;
import com.infobeans.coronavirustracker.models.LocationStatus;

@RestController
@RequestMapping(value = ApplicationConstant.BASE_URL)
public class CoronaVirusTrackerRestController {

	@Autowired
	@Qualifier(value = "globalCases")
	private GlobalConfirmedCoronaVirusData<LocationStatus> globalConfirmedCoronaVirusData;

	public List<LocationStatus> locationStatus = null;

	@GetMapping(value = ApplicationConstant.GLOBAL_CONFIRMED_CORONA_VIRUS_DATA)
	public List<LocationStatus> getAllGlobalConfirmedCoronaVirusAffectedCasesData() {

		try {
			locationStatus = globalConfirmedCoronaVirusData.getGlobalConfirmedCoronaVirusAffectedCasesData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locationStatus;
	}

}

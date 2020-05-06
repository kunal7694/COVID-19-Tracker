package com.infobeans.coronavirustracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.infobeans.coronavirustracker.api.GlobalConfirmedCoronaVirusData;
import com.infobeans.coronavirustracker.models.LocationStatus;

import lombok.Getter;

@Component
public class CoronaVirusTraker {

	@Autowired
	@Qualifier(value = "globalCases")
	@Getter
	private GlobalConfirmedCoronaVirusData<LocationStatus> object;

}

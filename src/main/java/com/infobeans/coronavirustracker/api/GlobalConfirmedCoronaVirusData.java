package com.infobeans.coronavirustracker.api;

import java.util.List;

public interface GlobalConfirmedCoronaVirusData<R> {

	public <T> List<R> getTotalConfirmedCases();

}

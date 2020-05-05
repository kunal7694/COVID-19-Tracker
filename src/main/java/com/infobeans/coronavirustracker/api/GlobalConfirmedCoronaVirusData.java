package com.infobeans.coronavirustracker.api;

import java.io.IOException;
import java.util.List;

import com.infobeans.coronavirustracker.models.LocationStatus;

public interface GlobalConfirmedCoronaVirusData<R> {

	public List<R> getGlobalConfirmedCoronaVirusAffectedCasesData() throws IOException;

	public List<LocationStatus> allConfirmedCases();

}

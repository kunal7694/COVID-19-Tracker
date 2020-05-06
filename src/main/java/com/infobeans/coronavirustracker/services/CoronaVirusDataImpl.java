/**
 * 
 */
package com.infobeans.coronavirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infobeans.coronavirustracker.api.GlobalConfirmedCoronaVirusData;
import com.infobeans.coronavirustracker.models.LocationStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * @author
 *
 */
@Service
@Component(value = "globalCases")
public class CoronaVirusDataImpl implements GlobalConfirmedCoronaVirusData<LocationStatus> {

	@Value(value = "${virus.tracker.url}")
	private String url;

	@Autowired
	private RestTemplate restTemplate;

	@Getter
	@Setter
	private List<LocationStatus> allConfirmedCases = null;

	@Scheduled(cron = "* * 1 * * *")
	public List<LocationStatus> getGlobalConfirmedCoronaVirusAffectedCasesData() throws IOException {
		String rawCsvData = getForEntity(url, String.class);
		return formatingCsvDataToList(rawCsvData);
	}

	private <T> T getForEntity(String url, Class<T> responseType) {
		return restTemplate.getForEntity(url, responseType).getBody();
	}

	private List<CSVRecord> parseRawCsvDataToList(String rawCsvData) throws IOException {
		return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new StringReader(rawCsvData)).getRecords();
	}

	private List<LocationStatus> formatingCsvDataToList(String rawCsvData) throws IOException {
		return parseRawCsvDataToList(rawCsvData).parallelStream().map(csvRecord -> {
			LocationStatus locationStatus = new LocationStatus();
			locationStatus
					.setProvinceOrState(!csvRecord.get("Province/State").isEmpty() ? csvRecord.get("Province/State")
							: csvRecord.get("Province/State").replace("", "Presence-Unknown"));
			locationStatus.setCountryOrRegion(csvRecord.get("Country/Region"));
			int latestTotalCasesFound = Integer.parseInt(csvRecord.get(csvRecord.size() - 1));
			int prevDaysCases = Integer.parseInt(csvRecord.get(csvRecord.size() - 2));
			locationStatus.setLatestTotalCasesFound(latestTotalCasesFound);
			locationStatus.setDiffFromPrevDay((latestTotalCasesFound - prevDaysCases));
			return locationStatus;
		}).sorted((locationStatus1, locationStatus2) -> locationStatus1.getProvinceOrState()
				.compareTo(locationStatus2.getProvinceOrState())).collect(Collectors.toList());
	}

	@Override
	public <T> List<LocationStatus> getTotalConfirmedCases() {
		try {
			setAllConfirmedCases(getGlobalConfirmedCoronaVirusAffectedCasesData());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return getAllConfirmedCases();
	}

}

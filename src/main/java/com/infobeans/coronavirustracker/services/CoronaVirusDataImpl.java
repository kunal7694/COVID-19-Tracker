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
 * @author L3THAL
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
	private List<LocationStatus> allConfirmedCases;

	@Override
	@Scheduled(cron = "* * 1 * * *")
	public List<LocationStatus> getGlobalConfirmedCoronaVirusAffectedCasesData() throws IOException {
		String rawCsvData = getForEntity(url, String.class);
		this.setAllConfirmedCases(formatingCsvDataToList(rawCsvData));
		return this.getAllConfirmedCases();
	}

	/**
	 * @param rawVirusData
	 * @return
	 * @throws IOException
	 */
	private List<LocationStatus> formatingCsvDataToList(String rawCsvData) throws IOException {
		return parseRawCsvDataToList(rawCsvData).parallelStream().map(virusData -> {
			LocationStatus locationStatus = new LocationStatus();
			locationStatus
					.setProvinceOrState(!virusData.get("Province/State").isEmpty() ? virusData.get("Province/State")
							: virusData.get("Province/State").replace("", "Presence-Unknown"));
			locationStatus.setCountryOrRegion(virusData.get("Country/Region"));
			int latestTotalCasesFound = Integer.parseInt(virusData.get(virusData.size() - 1));
			int prevDaysCases = Integer.parseInt(virusData.get(virusData.size() - 2));
			locationStatus.setLatestTotalCasesFound(latestTotalCasesFound);
			locationStatus.setDiffFromPrevDay((latestTotalCasesFound - prevDaysCases));
			return locationStatus;
		}).sorted((state1, state2) -> state1.getProvinceOrState().compareTo(state2.getProvinceOrState()))
				.collect(Collectors.toList());
	}

	/**
	 * @param rawVirusData
	 * @return
	 * @throws IOException
	 */
	private List<CSVRecord> parseRawCsvDataToList(String rawCsvData) throws IOException {
		return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new StringReader(rawCsvData)).getRecords();
	}

	/**
	 * @return Raw Data From Pass Url
	 */
	private String getForEntity(String url, Class<String> clazz) {
		return restTemplate.getForEntity(url, clazz).getBody();
	}

	@Override
	public List<LocationStatus> allConfirmedCases() {
		try {
			this.setAllConfirmedCases(this.getGlobalConfirmedCoronaVirusAffectedCasesData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allConfirmedCases;
	}

}

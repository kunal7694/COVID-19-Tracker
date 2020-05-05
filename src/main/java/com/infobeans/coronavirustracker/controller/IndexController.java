package com.infobeans.coronavirustracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infobeans.coronavirustracker.api.GlobalConfirmedCoronaVirusData;
import com.infobeans.coronavirustracker.models.LocationStatus;

@Controller
public class IndexController {

	@Autowired
	@Qualifier(value = "globalCases")
	private GlobalConfirmedCoronaVirusData<LocationStatus> globalConfirmedCoronaVirusData;

	@RequestMapping(value = "/")
	public String indexPage(Model model) {
		List<LocationStatus> locationStats = globalConfirmedCoronaVirusData.allConfirmedCases();
		int totalReportedCases = locationStats.stream().mapToInt(stat -> stat.getLatestTotalCasesFound()).sum();
		int totalNewCases = locationStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
		model.addAttribute("locationStats", locationStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewCases);
		return "index";
	}

}

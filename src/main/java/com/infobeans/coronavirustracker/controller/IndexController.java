package com.infobeans.coronavirustracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infobeans.coronavirustracker.models.LocationStatus;
import com.infobeans.coronavirustracker.services.CoronaVirusTraker;

@Controller
public class IndexController {

	@Autowired
	CoronaVirusTraker coronaVirusTraker;

	@RequestMapping(value = "/")
	public String indexPage(Model model) {
		List<LocationStatus> locationStats = coronaVirusTraker.getObject().getTotalConfirmedCases();
		int totalReportedCases = locationStats.stream().mapToInt(stat -> stat.getLatestTotalCasesFound()).sum();
		int totalNewCases = locationStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
		model.addAttribute("locationStats", locationStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalNewCases", totalNewCases);
		return "index";
	}

}

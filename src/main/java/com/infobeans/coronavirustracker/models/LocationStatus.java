/**
 * 
 */
package com.infobeans.coronavirustracker.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DELL-KUNAL
 *
 */
@Data
@NoArgsConstructor
public class LocationStatus {

	private String provinceOrState;
	private String countryOrRegion;
	private int latestTotalCasesFound;
	private int diffFromPrevDay;

}

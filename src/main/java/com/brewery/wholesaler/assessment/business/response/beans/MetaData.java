package com.brewery.wholesaler.assessment.business.response.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.Date;
import com.brewery.wholesaler.assessment.constants.BreweryWholesaleConstants;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
	@Builder.Default
	private String requestId = UUID.randomUUID().toString();
	@Builder.Default
	private String timestamp = new SimpleDateFormat(BreweryWholesaleConstants.META_DATA_DATE_FORMAT).format(new Date());
}
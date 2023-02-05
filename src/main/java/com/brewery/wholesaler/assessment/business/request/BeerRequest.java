package com.brewery.wholesaler.assessment.business.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class BeerRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String breweryId;
	private String name;
	private BigDecimal alcoholContent;
	private BigDecimal price;
}
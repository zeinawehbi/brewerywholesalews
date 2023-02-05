package com.brewery.wholesaler.assessment.business.request.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class BeerQuoteRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String beerId;
	private BigDecimal numberOfBeers;
}
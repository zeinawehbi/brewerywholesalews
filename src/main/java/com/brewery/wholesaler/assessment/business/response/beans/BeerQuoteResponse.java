package com.brewery.wholesaler.assessment.business.response.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeerQuoteResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String beerId;
	private BigDecimal numberOfBeers;
	private BigDecimal totalPrice;
}
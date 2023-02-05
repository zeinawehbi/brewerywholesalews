package com.brewery.wholesaler.assessment.business.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.brewery.wholesaler.assessment.business.response.beans.BeerQuoteResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal totalPrice;
	private List<BeerQuoteResponse> beerQuoteList;
}
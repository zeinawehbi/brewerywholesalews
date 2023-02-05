package com.brewery.wholesaler.assessment.business.request;

import java.io.Serializable;
import java.util.List;

import com.brewery.wholesaler.assessment.business.request.beans.BeerQuoteRequest;

import lombok.Data;

@Data
public class QuoteRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String wholesalerId;
	private List<BeerQuoteRequest> beerList;
}

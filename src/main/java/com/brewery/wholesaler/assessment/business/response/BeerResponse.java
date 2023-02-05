package com.brewery.wholesaler.assessment.business.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeerResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private BigDecimal alcoholContent;
	private String name;
	private BigDecimal price;
}
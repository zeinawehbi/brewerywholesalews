package com.brewery.wholesaler.assessment.business.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class WholesalerStockRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal stock;
	private String beerId;
	private String wholesalerId;
}

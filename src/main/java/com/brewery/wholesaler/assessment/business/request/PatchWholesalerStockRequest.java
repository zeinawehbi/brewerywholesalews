package com.brewery.wholesaler.assessment.business.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class PatchWholesalerStockRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal stock;
}
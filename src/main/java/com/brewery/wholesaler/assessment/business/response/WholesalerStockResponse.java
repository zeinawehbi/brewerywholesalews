package com.brewery.wholesaler.assessment.business.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class WholesalerStockResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private BigDecimal stock;
	private String beerId;
	private String wholesalerId;
}
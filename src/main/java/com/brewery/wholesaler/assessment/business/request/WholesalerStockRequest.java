package com.brewery.wholesaler.assessment.business.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WholesalerStockRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Stock must be not empty")
	@Positive(message = "Stock must be greater than 0")
	private Integer stock;
}
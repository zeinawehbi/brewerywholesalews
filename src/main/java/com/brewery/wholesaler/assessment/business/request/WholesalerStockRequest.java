package com.brewery.wholesaler.assessment.business.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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

	@NotBlank(message = "Beer Id must not be empty")
	@Size(max = 36, message = "Beer Id should be maximum of 36 characters")
	private String beerId;

	@NotBlank(message = "Wholesaler Id must not be empty")
	@Size(max = 36, message = "Wholesaler Id should be maximum of 36 characters")
	private String wholesalerId;
}

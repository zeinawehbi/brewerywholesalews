package com.brewery.wholesaler.assessment.business.request.beans;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerQuoteRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Brewery Id must not be empty")
	@Size(max = 36, message = "Beer Id should be maximum of 36 characters")
	private String beerId;

	@NotNull(message = "Number of beers must not be empty")
	@DecimalMin(value = "0.0", inclusive = false, message = "Number of beers must be greater than 0")
	private Integer numberOfBeers;
}
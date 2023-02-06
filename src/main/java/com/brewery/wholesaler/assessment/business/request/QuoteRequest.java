package com.brewery.wholesaler.assessment.business.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.brewery.wholesaler.assessment.business.request.beans.BeerQuoteRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Wholesaler Id must not be empty")
	@Size(max = 36, message = "Wholesaler Id should be maximum of 36 characters")
	private String wholesalerId;

	@Valid
	@NotNull(message = "The order cannot be empty")
	@NotEmpty(message = "The order cannot be empty")
	private List<BeerQuoteRequest> beerList;
}

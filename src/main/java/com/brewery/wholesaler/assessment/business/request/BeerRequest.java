package com.brewery.wholesaler.assessment.business.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Beer name must not be empty")
	@Size(max = 500, message = "Beer name should be maximum of 500 characters")
	private String name;

	@PositiveOrZero(message = "Alcohol content must be a positive number")
	private BigDecimal alcoholContent;

	@NotNull(message = "Price must not be empty")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private BigDecimal price;
}
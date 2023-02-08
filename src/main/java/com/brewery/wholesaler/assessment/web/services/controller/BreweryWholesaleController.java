package com.brewery.wholesaler.assessment.web.services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brewery.wholesaler.assessment.business.request.BeerRequest;
import com.brewery.wholesaler.assessment.business.request.PatchWholesalerStockRequest;
import com.brewery.wholesaler.assessment.business.request.QuoteRequest;
import com.brewery.wholesaler.assessment.business.request.WholesalerStockRequest;
import com.brewery.wholesaler.assessment.business.response.BeerResponse;
import com.brewery.wholesaler.assessment.business.response.QuoteResponse;
import com.brewery.wholesaler.assessment.business.response.WholesalerStockResponse;
import com.brewery.wholesaler.assessment.business.response.beans.BaseResponse;
import com.brewery.wholesaler.assessment.business.service.BreweryWholesaleService;

@RestController
@RequestMapping(path = "/api/brewery-wholesale")
public class BreweryWholesaleController {

	@Autowired
	BreweryWholesaleService breweryWholesaleService;

	@GetMapping(value = "/breweries/{breweryId}/beers")
	public BaseResponse<List<BeerResponse>> getBeerListByBreweryId(@PathVariable String breweryId) {
		BaseResponse<List<BeerResponse>> response = new BaseResponse<>();
		response.setData(breweryWholesaleService.getBeerListByBreweryId(breweryId));
		return response;
	}

	@PostMapping(value = "/breweries/{breweryId}/beers")
	public BaseResponse<BeerResponse> addBeer(@PathVariable String breweryId,
			@RequestBody @Validated BeerRequest request) throws Exception {
		BaseResponse<BeerResponse> response = new BaseResponse<>();
		response.setData(breweryWholesaleService.addBeer(breweryId, request));
		return response;
	}

	@DeleteMapping(value = "/beers/{beerId}")
	public BaseResponse<String> deleteBeer(@PathVariable String beerId) throws Exception {
		BaseResponse<String> response = new BaseResponse<>();
		response.setData(breweryWholesaleService.deleteBeer(beerId));
		return response;
	}

	@PostMapping(value = "/wholesalers/{wholesalerId}/beers/{beerId}/wholesaler-stock")
	public BaseResponse<WholesalerStockResponse> addWholesalerStock(@PathVariable String wholesalerId,
			@PathVariable String beerId, @RequestBody @Validated WholesalerStockRequest request) throws Exception {
		BaseResponse<WholesalerStockResponse> response = new BaseResponse<>();
		response.setData(breweryWholesaleService.addWholesalerStock(wholesalerId, beerId, request));
		return response;
	}

	@PatchMapping(value = "/wholesaler-stock/{wholesalerStockId}")
	public BaseResponse<WholesalerStockResponse> patchWholesaletStock(@PathVariable String wholesalerStockId,
			@RequestBody @Validated PatchWholesalerStockRequest request) throws Exception {
		BaseResponse<WholesalerStockResponse> response = new BaseResponse<>();
		response.setData(breweryWholesaleService.patchWholesaletStock(wholesalerStockId, request));
		return response;
	}

	@PostMapping(value = "/request-quote")
	public BaseResponse<QuoteResponse> requestQuote(@RequestBody @Validated QuoteRequest request) throws Exception {
		BaseResponse<QuoteResponse> response = new BaseResponse<>();
		response.setData(breweryWholesaleService.requestQuote(request));
		return response;
	}
}
package com.brewery.wholesaler.assessment.business.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brewery.wholesaler.assessment.business.request.BeerRequest;
import com.brewery.wholesaler.assessment.business.request.PatchWholesalerStockRequest;
import com.brewery.wholesaler.assessment.business.request.QuoteRequest;
import com.brewery.wholesaler.assessment.business.request.WholesalerStockRequest;
import com.brewery.wholesaler.assessment.business.request.beans.BeerQuoteRequest;
import com.brewery.wholesaler.assessment.business.response.BeerResponse;
import com.brewery.wholesaler.assessment.business.response.QuoteResponse;
import com.brewery.wholesaler.assessment.business.response.WholesalerStockResponse;
import com.brewery.wholesaler.assessment.business.response.beans.BeerQuoteResponse;
import com.brewery.wholesaler.assessment.business.response.beans.DataBaseResponse;
import com.brewery.wholesaler.assessment.data.entity.Beer;
import com.brewery.wholesaler.assessment.data.entity.Brewery;
import com.brewery.wholesaler.assessment.data.entity.Wholesaler;
import com.brewery.wholesaler.assessment.data.entity.WholesalerStock;
import com.brewery.wholesaler.assessment.utils.ResponseUtilities;

@Service
public class BreweryWholesaleService {

	@Autowired
	BeerService beerService;

	@Autowired
	BreweryService breweryService;

	@Autowired
	WholesalerService wholesalerService;

	@Autowired
	WholesalerStockService wholesalerStockService;

	@Autowired
	ResponseUtilities responseUtilities;

	public DataBaseResponse<List<BeerResponse>> getBeerListByBreweryId(String breweryId) {
		List<BeerResponse> response = new ArrayList<>();

		Iterable<Beer> beerIterable = beerService.getBeerListByBreweryId(breweryId);
		if (beerIterable != null) {
			for (Beer beer : beerIterable) {
				if (beer != null) {
					response.add(BeerResponse.builder().id(beer.getId()).name(beer.getName())
							.alcoholContent(beer.getAlcoholContent()).price(beer.getPrice()).build());
				}
			}
		}
		return responseUtilities.getContent(response);
	}

	public DataBaseResponse<BeerResponse> addBeer(BeerRequest request) throws Exception {
		if (!breweryService.existsById(request.getBreweryId())) {
			throw new Exception("Brewery doesn't exist");
		}

		Beer beer = beerService.save(Beer.builder().brewery(Brewery.builder().id(request.getBreweryId()).build())
				.alcoholContent(request.getAlcoholContent()).name(request.getName()).price(request.getPrice()).build());
		if (beer == null) {
			throw new Exception("Beer was not added successfully!");
		}
		return responseUtilities.getContent(BeerResponse.builder().id(beer.getId()).name(beer.getName())
				.alcoholContent(beer.getAlcoholContent()).price(beer.getPrice()).build());
	}

	public DataBaseResponse<String> deleteBeer(String beerId) throws Exception {
		if (!beerService.existsById(beerId)) {
			throw new Exception("Beer doesn't exist!");
		}

		beerService.deleteById(beerId);
		return responseUtilities.getContent("Beer is deleted successfully!");
	}

	public DataBaseResponse<WholesalerStockResponse> addWholesalerStock(WholesalerStockRequest request)
			throws Exception {
		if (!wholesalerService.existsById(request.getWholesalerId())) {
			throw new Exception("The wholesaler must exist");
		}

		if (!beerService.existsById(request.getBeerId())) {
			throw new Exception("Beer doesn't exist!");
		}

		WholesalerStock wholesalerStock = wholesalerStockService.save(
				WholesalerStock.builder().stock(request.getStock()).beer(Beer.builder().id(request.getBeerId()).build())
						.wholesaler(Wholesaler.builder().id(request.getWholesalerId()).build()).build());
		if (wholesalerStock == null) {
			throw new Exception("Stock was not added successfully!");
		}
		return responseUtilities.getContent(WholesalerStockResponse.builder().id(wholesalerStock.getId())
				.beerId(wholesalerStock.getBeer().getId()).wholesalerId(wholesalerStock.getWholesaler().getId())
				.stock(wholesalerStock.getStock()).build());
	}

	public DataBaseResponse<WholesalerStockResponse> patchWholesaletStock(String wholesalerStockId,
			PatchWholesalerStockRequest request) throws Exception {
		Optional<WholesalerStock> wholesalerStock = wholesalerStockService.findById(wholesalerStockId);
		if (wholesalerStock == null || !wholesalerStock.isPresent()) {
			throw new Exception("The wholesaler stock doesn't exist");
		}
		WholesalerStock wholesalerStockEntity = wholesalerStock.get();
		wholesalerStockEntity.setStock(request.getStock());
		wholesalerStockEntity = wholesalerStockService.save(wholesalerStockEntity);
		if (wholesalerStockEntity == null) {
			throw new Exception("Stock was not patched successfully!");
		}
		return responseUtilities.getContent(WholesalerStockResponse.builder().id(wholesalerStockEntity.getId())
				.stock(wholesalerStockEntity.getStock()).build());
	}

	public DataBaseResponse<QuoteResponse> requestQuote(QuoteRequest request) throws Exception {
		if (request == null || request.getBeerList() == null || request.getBeerList().isEmpty()) {
			throw new Exception("The order cannot be empty");
		}
		String wholesalerId = request.getWholesalerId();
		if (!wholesalerService.existsById(wholesalerId)) {
			throw new Exception("The wholesaler must exist");
		}
		Map<String, BigDecimal> beerQuoteMap = new HashMap<>();
		try {
			beerQuoteMap = request.getBeerList().stream()
					.collect(Collectors.toMap(BeerQuoteRequest::getBeerId, BeerQuoteRequest::getNumberOfBeers));
		} catch (IllegalStateException ex) {
			throw new Exception("There can't be any duplicate in the order");
		}

		WholesalerStock wholesalerStock = null;
		BigDecimal totalNumberOfBeers = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);
		List<BeerQuoteResponse> beerQuoteList = new ArrayList<>();
		for (Map.Entry<String, BigDecimal> beerQuote : beerQuoteMap.entrySet()) {
			wholesalerStock = wholesalerStockService.getWholesalerStockByWholesalerIdAndBeerId(wholesalerId,
					beerQuote.getKey());
			if (wholesalerStock == null) {
				throw new Exception("The beer must be sold by the wholesaler");
			}

			if (beerQuote.getValue() == null || beerQuote.getValue().compareTo(new BigDecimal(0)) == 0) {
				throw new Exception("The order cannot be empty");
			}

			if (beerQuote.getValue().compareTo(wholesalerStock.getStock()) > 0) {
				throw new Exception("The number of beers ordered cannot be greater than the wholesaler's stock");
			}
			totalNumberOfBeers = totalNumberOfBeers.add(beerQuote.getValue());
			totalPrice = totalPrice.add(beerQuote.getValue().multiply(wholesalerStock.getBeer().getPrice()));
			beerQuoteList.add(BeerQuoteResponse.builder().beerId(beerQuote.getKey()).numberOfBeers(beerQuote.getValue())
					.totalPrice(beerQuote.getValue().multiply(wholesalerStock.getBeer().getPrice()).setScale(2,
							BigDecimal.ROUND_HALF_UP))
					.build());
		}

		BigDecimal discount = null;
		if (totalNumberOfBeers.compareTo(new BigDecimal(20)) > 0) {
			discount = new BigDecimal(0.2);
		} else if (totalNumberOfBeers.compareTo(new BigDecimal(10)) > 0) {
			discount = new BigDecimal(0.1);
		}

		if (discount != null) {
			for (BeerQuoteResponse b : beerQuoteList) {
				b.setTotalPrice(b.getTotalPrice().subtract(b.getTotalPrice().multiply(discount)).setScale(2,
						BigDecimal.ROUND_HALF_UP));
			}
			totalPrice = totalPrice.subtract(totalPrice.multiply(discount));
		}

		totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

		return responseUtilities
				.getContent(QuoteResponse.builder().totalPrice(totalPrice).beerQuoteList(beerQuoteList).build());
	}
}

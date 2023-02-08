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
import com.brewery.wholesaler.assessment.data.entity.WholesalerStock;
import com.brewery.wholesaler.assessment.exception.BadRequestException;
import com.brewery.wholesaler.assessment.exception.ConflictException;
import com.brewery.wholesaler.assessment.exception.NotFoundException;
import com.brewery.wholesaler.assessment.exception.UnprocessableEntityException;
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

	public DataBaseResponse<BeerResponse> addBeer(String breweryId, BeerRequest request) throws Exception {
		if (!breweryService.existsById(breweryId)) {
			throw new NotFoundException("Brewery doesn't exist");
		}

		Beer beer = beerService.addBeer(breweryId, request);
		if (beer == null) {
			throw new Exception("Beer was not added successfully!");
		}
		return responseUtilities.getContent(BeerResponse.builder().id(beer.getId()).name(beer.getName())
				.alcoholContent(beer.getAlcoholContent()).price(beer.getPrice()).build());
	}

	public DataBaseResponse<String> deleteBeer(String beerId) throws Exception {
		if (!beerService.existsById(beerId)) {
			throw new NotFoundException("Beer doesn't exist!");
		}
		if (wholesalerStockService.getCountByBeerId(beerId) != 0) {
			throw new ConflictException("Beer cannot be deleted as it is already in a wholesaler's stock");
		}

		beerService.deleteById(beerId);
		return responseUtilities.getContent("Beer is deleted successfully!");
	}

	public DataBaseResponse<WholesalerStockResponse> addWholesalerStock(String wholesalerId, String beerId,
			WholesalerStockRequest request) throws Exception {
		if (!wholesalerService.existsById(wholesalerId)) {
			throw new NotFoundException("The wholesaler must exist");
		}

		if (!beerService.existsById(beerId)) {
			throw new NotFoundException("Beer doesn't exist!");
		}

		if (wholesalerStockService.getCountByWholesalerIdAndBeerId(wholesalerId, beerId) != 0) {
			throw new ConflictException("Stock of this beer already exists for this wholesaler");
		}

		WholesalerStock wholesalerStock = wholesalerStockService.addWholesalerStock(wholesalerId, beerId, request);
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
			throw new NotFoundException("The wholesaler stock doesn't exist");
		}
		WholesalerStock wholesalerStockEntity = wholesalerStockService.patchWholesalerStock(wholesalerStock.get(),
				request);
		if (wholesalerStockEntity == null) {
			throw new Exception("Stock was not patched successfully!");
		}
		return responseUtilities.getContent(WholesalerStockResponse.builder().id(wholesalerStockEntity.getId())
				.stock(wholesalerStockEntity.getStock()).build());
	}

	public DataBaseResponse<QuoteResponse> requestQuote(QuoteRequest request) throws Exception {
		String wholesalerId = request.getWholesalerId();
		if (!wholesalerService.existsById(wholesalerId)) {
			throw new NotFoundException("The wholesaler must exist");
		}
		Map<String, Integer> beerQuoteMap = new HashMap<>();
		try {
			beerQuoteMap = request.getBeerList().stream()
					.collect(Collectors.toMap(BeerQuoteRequest::getBeerId, BeerQuoteRequest::getNumberOfBeers));
		} catch (IllegalStateException ex) {
			throw new BadRequestException("There can't be any duplicate in the order");
		}

		WholesalerStock wholesalerStock = null;
		Integer totalNumberOfBeers = 0;
		BigDecimal totalPrice = new BigDecimal(0);
		List<BeerQuoteResponse> beerQuoteList = new ArrayList<>();
		for (Map.Entry<String, Integer> beerQuote : beerQuoteMap.entrySet()) {
			wholesalerStock = wholesalerStockService.getWholesalerStockByWholesalerIdAndBeerId(wholesalerId,
					beerQuote.getKey());
			if (wholesalerStock == null) {
				throw new UnprocessableEntityException("The beer must be sold by the wholesaler");
			}

			if (beerQuote.getValue() > wholesalerStock.getStock()) {
				throw new UnprocessableEntityException(
						"The number of beers ordered cannot be greater than the wholesaler's stock");
			}
			totalNumberOfBeers = totalNumberOfBeers + beerQuote.getValue();
			totalPrice = totalPrice
					.add(new BigDecimal(beerQuote.getValue()).multiply(wholesalerStock.getBeer().getPrice()));
			beerQuoteList.add(BeerQuoteResponse.builder().beerId(beerQuote.getKey()).numberOfBeers(beerQuote.getValue())
					.totalPrice(new BigDecimal(beerQuote.getValue()).multiply(wholesalerStock.getBeer().getPrice())
							.setScale(2, BigDecimal.ROUND_HALF_UP))
					.build());
		}

		BigDecimal discount = null;
		if (totalNumberOfBeers > 20) {
			discount = new BigDecimal(0.2);
		} else if (totalNumberOfBeers > 10) {
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

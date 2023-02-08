package com.brewery.wholesaler.assessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.brewery.wholesaler.assessment.business.request.BeerRequest;
import com.brewery.wholesaler.assessment.business.request.PatchWholesalerStockRequest;
import com.brewery.wholesaler.assessment.business.request.WholesalerStockRequest;
import com.brewery.wholesaler.assessment.business.service.BeerService;
import com.brewery.wholesaler.assessment.business.service.BreweryService;
import com.brewery.wholesaler.assessment.business.service.WholesalerService;
import com.brewery.wholesaler.assessment.business.service.WholesalerStockService;
import com.brewery.wholesaler.assessment.data.entity.Beer;
import com.brewery.wholesaler.assessment.data.entity.Brewery;
import com.brewery.wholesaler.assessment.data.entity.Wholesaler;
import com.brewery.wholesaler.assessment.data.entity.WholesalerStock;

@SpringBootTest(classes = AssessmentApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class AssessmentApplicationTests {

	@Autowired
	private BreweryService breweryService;

	@Autowired
	BeerService beerService;

	@Autowired
	WholesalerService wholesalerService;

	@Autowired
	WholesalerStockService wholesalerStockService;

	private static Brewery brewery;
	private static Beer beer;
	private static Wholesaler wholesaler;
	private static WholesalerStock wholesalerStock;

	@BeforeAll
	public void setupBreweryWholesaler() {
		brewery = Brewery.builder().name("Abbaye de Leffe").build();
		brewery = breweryService.save(brewery);
		wholesaler = Wholesaler.builder().name("GeneDrinks").build();
		wholesaler = wholesalerService.save(wholesaler);
	}

	@Test
	@Order(1)
	public void testAddBeer() {
		BeerRequest request = new BeerRequest("Leffe Blonde", new BigDecimal(6.6), new BigDecimal(2.20));
		beer = beerService.addBeer(brewery.getId(), request);
		assertTrue(beerService.existsById(beer.getId()));
	}

	@Test
	@Order(2)
	public void testGetBeerListByBreweryId() {
		Iterable<Beer> beerList = beerService.getBeerListByBreweryId(brewery.getId());
		for (Beer b : beerList) {
			assertEquals(b.getId(), beer.getId());
		}
	}

	@Test
	@Order(4)
	public void testAddWholesalerStock() {
		assertEquals(wholesalerStockService.getCountByWholesalerIdAndBeerId(wholesaler.getId(), beer.getId()), 0);

		WholesalerStockRequest wholesalerStockRequest = new WholesalerStockRequest(10);
		wholesalerStock = wholesalerStockService.addWholesalerStock(wholesaler.getId(), beer.getId(),
				wholesalerStockRequest);
		assertTrue(wholesalerStockService.existsById(wholesalerStock.getId()));
		assertEquals(wholesalerStockService.getCountByWholesalerIdAndBeerId(wholesaler.getId(), beer.getId()), 1);
	}

	@Test
	@Order(5)
	public void testPatchWholesalerStock() {
		PatchWholesalerStockRequest request = new PatchWholesalerStockRequest(20);
		Optional<WholesalerStock> wholesalerStockOptional = wholesalerStockService.findById(wholesalerStock.getId());

		assertTrue(wholesalerStockOptional != null && wholesalerStockOptional.isPresent());

		wholesalerStock = wholesalerStockService.patchWholesalerStock(wholesalerStockOptional.get(), request);
		assertEquals(wholesalerStock.getStock(), 20);
	}

	@Test
	@Order(6)
	public void testRequestQuote() {
		String actualWholesalerStockId = wholesalerStock.getId();
		wholesalerStock = wholesalerStockService.getWholesalerStockByWholesalerIdAndBeerId(wholesaler.getId(),
				beer.getId());
		assertEquals(wholesalerStock.getId(), actualWholesalerStockId);
	}

	@Test
	@Order(7)
	public void testDeleteBeerFailure() {
		assertEquals(wholesalerStockService.getCountByBeerId(beer.getId()), 1);
	}

	@Test
	@Order(8)
	public void testDeleteBeerSuccess() {
		wholesalerStockService.deleteById(wholesalerStock.getId());
		assertEquals(wholesalerStockService.getCountByBeerId(beer.getId()), 0);
		beerService.deleteById(beer.getId());
		assertFalse(beerService.existsById(beer.getId()));
	}

	@AfterAll
	public void cleanData() {
		wholesalerService.deleteById(wholesaler.getId());
		breweryService.deleteById(brewery.getId());
	}
}
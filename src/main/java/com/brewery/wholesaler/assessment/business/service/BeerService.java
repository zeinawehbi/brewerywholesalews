package com.brewery.wholesaler.assessment.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brewery.wholesaler.assessment.data.entity.Beer;
import com.brewery.wholesaler.assessment.data.repository.BeerRepository;

@Service
public class BeerService extends BaseService<Beer, String> {

	@Autowired
	BeerRepository beerRepository;

	public Iterable<Beer> getBeerListByBreweryId(String breweryId) {
		return beerRepository.findAllByBreweryId(breweryId);
	}
}
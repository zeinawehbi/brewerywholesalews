package com.brewery.wholesaler.assessment.data.repository;

import org.springframework.stereotype.Repository;

import com.brewery.wholesaler.assessment.data.entity.Beer;

@Repository
public interface BeerRepository extends BaseRepository<Beer, String> {

	Iterable<Beer> findAllByBreweryId(String breweryId);
}
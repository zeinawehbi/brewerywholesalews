package com.brewery.wholesaler.assessment.data.repository;

import org.springframework.stereotype.Repository;

import com.brewery.wholesaler.assessment.data.entity.Brewery;

@Repository
public interface BreweryRepository extends BaseRepository<Brewery, String> {

}
package com.brewery.wholesaler.assessment.data.repository;

import org.springframework.stereotype.Repository;
import com.brewery.wholesaler.assessment.data.entity.Wholesaler;

@Repository
public interface WholesalerRepository extends BaseRepository<Wholesaler, String> {

}
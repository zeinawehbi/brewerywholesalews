package com.brewery.wholesaler.assessment.data.repository;

import org.springframework.stereotype.Repository;
import com.brewery.wholesaler.assessment.data.entity.WholesalerStock;

@Repository
public interface WholesalerStockRepository extends BaseRepository<WholesalerStock, String> {

	WholesalerStock findByWholesalerIdAndBeerId(String id, String beerId);
}
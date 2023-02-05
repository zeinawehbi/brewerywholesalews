package com.brewery.wholesaler.assessment.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brewery.wholesaler.assessment.data.entity.WholesalerStock;
import com.brewery.wholesaler.assessment.data.repository.WholesalerStockRepository;

@Service
public class WholesalerStockService extends BaseService<WholesalerStock, String> {

	@Autowired
	WholesalerStockRepository wholesalerStockRepository;

	public WholesalerStock getWholesalerStockByWholesalerIdAndBeerId(String id, String beerId) {
		return wholesalerStockRepository.findByWholesalerIdAndBeerId(id, beerId);
	}
}
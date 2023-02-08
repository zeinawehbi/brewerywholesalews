package com.brewery.wholesaler.assessment.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brewery.wholesaler.assessment.business.request.PatchWholesalerStockRequest;
import com.brewery.wholesaler.assessment.business.request.WholesalerStockRequest;
import com.brewery.wholesaler.assessment.data.entity.Beer;
import com.brewery.wholesaler.assessment.data.entity.Wholesaler;
import com.brewery.wholesaler.assessment.data.entity.WholesalerStock;
import com.brewery.wholesaler.assessment.data.repository.WholesalerStockRepository;

@Service
public class WholesalerStockService extends BaseService<WholesalerStock, String> {

	@Autowired
	WholesalerStockRepository wholesalerStockRepository;

	public WholesalerStock addWholesalerStock(String wholesalerId, String beerId, WholesalerStockRequest request) {
		return save(WholesalerStock.builder().stock(request.getStock()).beer(Beer.builder().id(beerId).build())
				.wholesaler(Wholesaler.builder().id(wholesalerId).build()).build());
	}

	public WholesalerStock patchWholesalerStock(WholesalerStock wholesalerStock, PatchWholesalerStockRequest request) {
		wholesalerStock.setStock(request.getStock());
		return save(wholesalerStock);
	}

	public WholesalerStock getWholesalerStockByWholesalerIdAndBeerId(String wholesalerId, String beerId) {
		return wholesalerStockRepository.findByWholesalerIdAndBeerId(wholesalerId, beerId);
	}

	public long getCountByWholesalerIdAndBeerId(String wholesalerId, String beerId) {
		return wholesalerStockRepository.countByWholesalerIdAndBeerId(wholesalerId, beerId);
	}

	public long getCountByBeerId(String beerId) {
		return wholesalerStockRepository.countByBeerId(beerId);
	}
}
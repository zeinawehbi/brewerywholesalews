package com.brewery.wholesaler.assessment.business.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.brewery.wholesaler.assessment.data.repository.BaseRepository;

@Service
abstract class BaseService<T, ID> {

	@Autowired
	private BaseRepository<T, ID> baseRepository;

	public Optional<T> findById(ID id) {
		return baseRepository.findById(id);
	}

	public boolean existsById(ID id) {
		return baseRepository.existsById(id);
	}

	public Page<T> findAll(Pageable pageable) {
		return baseRepository.findAll(pageable);
	}

	public Iterable<T> findAll() {
		return baseRepository.findAll();
	}

	public long count() {
		return baseRepository.count();
	}

	public T save(T entity) {
		return baseRepository.save(entity);
	}

	public <S extends T> Iterable<S> saveAll(Iterable<S> var1) {
		return baseRepository.saveAll(var1);
	}

	public void delete(T entity) {
		baseRepository.delete(entity);
	}

	public void deleteById(ID id) {
		baseRepository.deleteById(id);
	}

}

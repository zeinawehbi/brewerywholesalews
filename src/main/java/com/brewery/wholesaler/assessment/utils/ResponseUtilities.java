package com.brewery.wholesaler.assessment.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.brewery.wholesaler.assessment.business.response.beans.DataBaseResponse;

@Component
public class ResponseUtilities {

	public <T> DataBaseResponse<List<T>> getContent(List<T> content) {
		DataBaseResponse<List<T>> result = new DataBaseResponse<>();
		result.setContent(content);
		return result;
	}

	public <T> DataBaseResponse<T> getContent(T content) {
		DataBaseResponse<T> result = new DataBaseResponse<>();
		result.setContent(content);
		return result;
	}
}
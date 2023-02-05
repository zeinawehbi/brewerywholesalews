package com.brewery.wholesaler.assessment.business.response.beans;

import com.brewery.wholesaler.assessment.business.response.beans.MetaData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

	@Builder.Default
	private int status = 1;
	@Builder.Default
	private MetaData metaData = new MetaData();
	private DataBaseResponse<T> data;
}
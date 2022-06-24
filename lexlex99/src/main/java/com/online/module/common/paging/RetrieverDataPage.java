package com.online.module.common.paging;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public interface RetrieverDataPage<E> {

	@SuppressWarnings("rawtypes")
	List<E> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception;
	
	@SuppressWarnings("rawtypes")
	Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception;
	
}

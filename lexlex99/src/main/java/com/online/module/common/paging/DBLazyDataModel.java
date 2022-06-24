package com.online.module.common.paging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public class DBLazyDataModel<E> extends org.primefaces.model.LazyDataModel<E>{

	private static final long serialVersionUID = 2092539128743747995L;
	private RetrieverDataPage<E> retrieverDataPage;
	@SuppressWarnings("rawtypes")
	private List<? extends SearchObject> searchCriteria;
	private static Logger logger = Logger.getLogger(DBLazyDataModel.class);
	
	@SuppressWarnings("rawtypes")
	public DBLazyDataModel(RetrieverDataPage<E> retrieverDataPage, List<? extends SearchObject> searchCriteria, int pageSize) {
		this.retrieverDataPage = retrieverDataPage;
		setPageSize(pageSize);
		setSearchCriteria(searchCriteria);
	}
	
	public void updateRowCount() {
		try {
			Long totalRowCount = retrieverDataPage.searchCountData(searchCriteria);
			
			setRowCount(totalRowCount.intValue());
		} catch (Exception e) {
			logger.debug("Exception while searching row count, user 0 as result", e);
			setRowCount(0);
		}
	}
	
	@Override
	public List<E> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		try {
			List<E> results = retrieverDataPage.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
			setWrappedData(results);
			updateRowCount();
			return results;
		} catch (Exception e) {
			logger.debug("Exception while trying search, returning empty list", e);
			return new ArrayList<E>();
		}
	}
	
	public DBLazyDataModel(RetrieverDataPage<E> retrieverDataPage, int pageSize) {
		this(retrieverDataPage, null, pageSize);
	}

	public RetrieverDataPage<E> getRetrieverDataPage() {
		return retrieverDataPage;
	}

	public void setRetrieverDataPage(RetrieverDataPage<E> retrieverDataPage) {
		this.retrieverDataPage = retrieverDataPage;
	}

	@SuppressWarnings("rawtypes")
	public List<? extends SearchObject> getSearchCriteria() {
		return searchCriteria;
	}

	@SuppressWarnings("rawtypes")
	public void setSearchCriteria(List<? extends SearchObject> searchCriteria) {
		this.searchCriteria = searchCriteria;
		updateRowCount();
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DBLazyDataModel.logger = logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getRowKey(E object) {
		return null;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

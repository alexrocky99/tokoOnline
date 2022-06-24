package com.online.module.common.paging;

public interface SearchObject<T> {

	String ALL_COLUMNS = "*";
	String getSearchColumn();
	T getSearchValue();
	boolean isEmpty();
	String getSearchValueAsString();
	
}

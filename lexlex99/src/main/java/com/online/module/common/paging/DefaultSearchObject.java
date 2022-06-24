package com.online.module.common.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultSearchObject implements SearchObject<Object> {

	private String column;
	private Object value;
	
	public DefaultSearchObject(String column, Object value) {
		this.column = column;
		this.value = value;
	}
	
	@Override
	public String getSearchColumn() {
		return this.column;
	}

	@Override
	public Object getSearchValue() {
		return this.value;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public String getSearchValueAsString() {
		return (this.value != null) ? this.value.toString() : null;
	}

}

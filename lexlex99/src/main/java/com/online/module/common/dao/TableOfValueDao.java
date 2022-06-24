package com.online.module.common.dao;

import java.sql.SQLException;
import java.util.List;

public interface TableOfValueDao extends GenericDao<Object, Long> {

	public List<Object[]> getListTableOfValues(String sql) throws SQLException;
	public List<Object[]> getListTableOfValues(String sql, String searchStr) throws SQLException;
	
}

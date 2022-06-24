package com.online.module.common.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository("tableOfValueDao")
public class TableOfValueDaoImpl extends GenericDaoHibernate<Object, Long> implements TableOfValueDao, Serializable {

	private static final long serialVersionUID = -3975326092262869127L;

	@SuppressWarnings("unused")
	@Override
	public List<Object[]> getListTableOfValues(String sql) throws SQLException {
		Query query = getSession().createSQLQuery(sql);
		return null;
	}

	@Override
	public List<Object[]> getListTableOfValues(String sql, String searchStr) throws SQLException {
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("searchStr", "%"+searchStr+"%");
		return null;
	}

}

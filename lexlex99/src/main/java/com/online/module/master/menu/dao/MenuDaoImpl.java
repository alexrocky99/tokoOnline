package com.online.module.master.menu.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;

import com.online.module.common.constant.Constants;
import com.online.module.common.dao.GenericDaoHibernate;
import com.online.module.common.paging.SearchObject;
import com.online.module.common.util.MathUtil;
import com.online.module.master.menu.model.Menu;

@Repository("menuDao")
public class MenuDaoImpl extends GenericDaoHibernate<Menu, Long> implements Serializable, MenuDao {

	private static final long serialVersionUID = 3650308803891170519L;

	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_NAME)) {
						sb.append(" and upper(mm.name) like upper(:menuName) ");
					}
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void getQueryWhereString(Query query, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_NAME)) {
						query.setParameter("menuName", "%"+val+"%");
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Menu> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mm.menu_id ");
		sb.append(" 	,mm.name ");
		sb.append("     ,mm.description ");
		sb.append("     ,mm.menu_level ");
		sb.append("     ,mm.menu_order ");
		sb.append(" from mst_menu mm ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mm.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		sb.append(" order by mm.menu_id, mm.parent_id asc ");
		sb.append(" LIMIT " + first + " , " + pageSize + " ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<Menu> vo = new ArrayList<Menu>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				Menu data = new Menu();
				
				data.setMenuId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setName(obj[1] != null ? (String) obj[1] : null);
				data.setDescription(obj[2] != null ? (String) obj[2] : null);
				data.setMenuLevel(obj[3] != null ? MathUtil.returnIdObjToLong(obj[3]) : null);
				data.setMenuOrder(obj[4] != null ? MathUtil.returnIdObjToLong(obj[4]) : null);
				
				vo.add(data);
			}
		}
		
		return vo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select count(1) ");
		sb.append(" from mst_menu mm ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mm.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getAllParentMenu() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mm.menu_id ");
		sb.append(" from mst_menu mm ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mm.record_flag = 'Y' ");
		sb.append(" 	and mm.parent_id is null ");
		sb.append(" order by mm.name asc ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		
		List<Number> result = query.getResultList();
		List<Menu> vo = new ArrayList<Menu>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Number obj = (Number) result.get(i);
				Menu data = findById(obj.longValue());
				
				vo.add(data);
			}
		}
		
		return vo;
	}

}

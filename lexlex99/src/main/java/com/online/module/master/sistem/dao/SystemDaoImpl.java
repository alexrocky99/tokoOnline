package com.online.module.master.sistem.dao;

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
import com.online.module.master.sistem.model.System;

@Repository("systemDao")
public class SystemDaoImpl extends GenericDaoHibernate<System, Long> implements SystemDao, Serializable {

	private static final long serialVersionUID = 914487368758368088L;

	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_SISTEM_CODE)) {
						sb.append(" and ms.system_code = :systemCode ");
					}
					if (StringUtils.equals(col, Constants.WHERE_STATUS)) {
						sb.append(" and ms.status = :status ");
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
					if (StringUtils.equals(col, Constants.WHERE_SISTEM_CODE)) {
						query.setParameter("systemCode", val);
					}
					if (StringUtils.equals(col, Constants.WHERE_STATUS)) {
						query.setParameter("status", val);
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<System> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select ms.system_id ");
		sb.append(" 	,ms.system_code ");
		sb.append("     ,ms.system_value ");
		sb.append("     ,ms.system_description ");
		sb.append("     ,ms.status ");
		sb.append("     ,mpd.detail_value ");
		sb.append(" from mst_system ms ");
		sb.append(" 	left join mst_parameter_detail mpd on mpd.detail_code = ms.status ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and ms.record_flag = 'Y' ");
		
		setQueryWhereString(sb, searchCriteria);
		sb.append(" order by ms.system_value asc ");
		sb.append(" LIMIT " + first + " , " + pageSize + " ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<System> vo = new ArrayList<System>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				System data = new System();
				
				data.setSystemId(obj[0] != null ? (MathUtil.returnIdObjToLong(obj[0])) : null);
				data.setSystemCode(obj[1] != null ? (String) obj[1] : null);
				data.setSystemValue(obj[2] != null ? (String) obj[2] : null);
				data.setSystemDescription(obj[3] != null ? (String) obj[3] : null);
				data.setStatusCode(obj[4] != null ? (String) obj[4] : null);
				data.setStatus(obj[5] != null ? (String) obj[5] : null);
				data.setIsStatus(false);
				
				if (data.getStatusCode().equals(Constants.PARAM_DET_CODE_STATUS_ACTIVE)) {
					data.setIsStatus(true);
				}
				
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
		sb.append(" from mst_system ms ");
		sb.append(" 	left join mst_parameter_detail mpd on mpd.detail_code = ms.status ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and ms.record_flag = 'Y' ");
		
		setQueryWhereString(sb, searchCriteria);
		Query query = getSession().createSQLQuery(sb.toString());
		getQueryWhereString(query, searchCriteria);
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

}

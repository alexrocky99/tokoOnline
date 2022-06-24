package com.online.module.master.responsibility.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;

import com.online.module.common.constant.Constants;
import com.online.module.common.dao.GenericDaoHibernate;
import com.online.module.common.paging.SearchObject;
import com.online.module.common.util.MathUtil;
import com.online.module.master.responsibility.model.Responsibility;
import com.online.module.master.responsibility.model.ResponsibilityDetail;

@Repository("responsibilityDao")
public class ResponsibilityDaoImpl extends GenericDaoHibernate<Responsibility, Long> implements ResponsibilityDao, Serializable {

	private static final long serialVersionUID = -4216206065550307464L;
	private static final Logger logger = Logger.getLogger(ResponsibilityDaoImpl.class);

	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_NAME)) {
						sb.append(" and upper(mr.name) like (:name) ");
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
						query.setParameter("name", "%"+val+"%");
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Responsibility> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mr.responsibility_id ");
		sb.append(" 	,mr.name ");
		sb.append("     ,mr.description ");
		sb.append(" from mst_responsibility mr ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mr.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		sb.append(" order by mr.responsibility_id desc ");
		sb.append(" LIMIT " + first + " , " + pageSize + " ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<Responsibility> vo = new ArrayList<Responsibility>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				Responsibility data = new Responsibility();
				
				data.setResponsibilityId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setName(obj[1] != null ? (String) obj[1] : null);
				data.setDescription(obj[2] != null ? (String) obj[2] : null);
				
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
		sb.append(" from mst_responsibility mr ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mr.record_flag = 'Y' ");
		
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
	public List<Responsibility> getAllResponsibility() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mr.responsibility_id ");
		sb.append(" from mst_responsibility mr ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mr.record_flag = 'Y' ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		
		List<Number> result = query.getResultList();
		List<Responsibility> vo = new ArrayList<Responsibility>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Number obj = result.get(i);
				
				vo.add(findById(obj.longValue()));
			}
		}
		
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResponsibilityDetail> searchResponsibilityAllMenu(ResponsibilityDetail responsibilityDetail) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select distinct mm.menu_id ");
		sb.append(" 	,mm.name ");
		sb.append("     ,mm.parent_id ");
		sb.append(" 	,mrd.responsibility_id ");
		sb.append("     ,coalesce(mrd.responsibility_detail_id, 0) responsibility_detail_id ");
		sb.append("     ,case when mrd.responsibility_id is null then 'false' else 'true' end check2 ");
		sb.append("     ,mm.action ");
		sb.append(" from mst_menu mm ");
		sb.append(" 	left join mst_responsibility_detail mrd on mrd.menu_id = mm.menu_id ");
		if (responsibilityDetail != null 
				&& responsibilityDetail.getResponsibilityId() != null 
				&& responsibilityDetail.getResponsibilityId().longValue() > 0) {
			sb.append(" 		and mrd.responsibility_id = :responsibilityId ");
		} else {
			sb.append(" 		and mrd.responsibility_id is null ");
		}
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mm.record_flag = 'Y' ");
		
		if (responsibilityDetail != null 
				&& responsibilityDetail.getParentId() != null 
				&& responsibilityDetail.getParentId().longValue() > 0) {
			sb.append(" 		and mm.parent_id = :parentId ");
		} else {
			sb.append(" 		and mm.parent_id is null ");
		}
		
		Query query = getSession().createSQLQuery(sb.toString());
		
		if (responsibilityDetail != null 
				&& responsibilityDetail.getResponsibilityId() != null 
				&& responsibilityDetail.getResponsibilityId().longValue() > 0) {
			query.setParameter("responsibilityId", responsibilityDetail.getResponsibilityId());
		}
		if (responsibilityDetail != null 
				&& responsibilityDetail.getParentId() != null 
				&& responsibilityDetail.getParentId().longValue() > 0) {
			query.setParameter("parentId", responsibilityDetail.getParentId());
		}
		
		List<Object[]> result = query.getResultList();
		List<ResponsibilityDetail> vo = new ArrayList<ResponsibilityDetail>();
		
		if (responsibilityDetail != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				ResponsibilityDetail data = new ResponsibilityDetail();
				
				data.setMenuId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setMenuName(obj[1] != null ? (String) obj[1] : null);
				data.setParentId(obj[2] != null ? MathUtil.returnIdObjToLong(obj[2]) : null);
				data.setResponsibilityId(obj[3] != null ? MathUtil.returnIdObjToLong(obj[3]) : null);
				data.setResponsibilityDetailId(obj[4] != null ? MathUtil.returnIdObjToLong(obj[4]) : null);
				data.setCheck2(obj[5] != null ? (String) obj[5] : null);
				data.setMenuAction(obj[6] != null ? (String) obj[6] : null);
				
				ResponsibilityDetail tempData = new ResponsibilityDetail();
				tempData.setParentId(data.getMenuId());
				tempData.setResponsibilityId(data.getResponsibilityId());
				data.setChildResponsibilityMenu(searchResponsibilityAllMenu(tempData));
				
				vo.add(data);
			}
		}
		
		return vo;
	}

	@Override
	public void deleteInsertResponsibilityMenu(ResponsibilityDetail responsibilityDetail, String user) {
		if (responsibilityDetail != null) {
			String strDeleteQuery = "delete from mst_responsibility_detail where responsibility_id = :respId ";
			
			Query query = getSession().createSQLQuery(strDeleteQuery);
			query.setParameter("respId", responsibilityDetail.getResponsibilityId());
			
			int row = query.executeUpdate();
			
			logger.debug("Record " + row + " deleted for resp id " + responsibilityDetail.getResponsibilityId());
			logger.debug("Record " + responsibilityDetail.getChildResponsibilityMenu().size() + " will be re-inserted for resp id " + responsibilityDetail.getResponsibilityId());
		}
	}
	
}

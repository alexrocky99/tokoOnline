package com.online.module.master.parameter.dao;

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
import com.online.module.master.parameter.model.Parameter;
import com.online.module.master.parameter.model.ParameterDetail;

@Repository("parameterDetailDao")
public class ParameterDetailDaoImpl extends GenericDaoHibernate<ParameterDetail, Long> implements ParameterDetailDao, Serializable {

	private static final long serialVersionUID = -3129359130511531784L;

	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_PARAM_CODE)) {
						sb.append(" and mpd.code = :paramCode ");
					}
					if (StringUtils.equals(col, Constants.WHERE_PARAM_DETAIL_CODE)) {
						sb.append(" and upper(mpd.detail_code) like upper(:paramDetailCode) ");
					}
					if (StringUtils.equals(col, Constants.WHERE_VALUE)) {
						sb.append(" and upper(mpd.value) like (:value) ");
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
					if (StringUtils.equals(col, Constants.WHERE_PARAM_CODE)) {
						query.setParameter("paramCode", val);
					}
					if (StringUtils.equals(col, Constants.WHERE_PARAM_DETAIL_CODE)) {
						query.setParameter("paramDetailCode", "%"+val+"%");
					}
					if (StringUtils.equals(col, Constants.WHERE_VALUE)) {
						query.setParameter("value", "%"+val+"%");
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ParameterDetail> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mpd.parameter_detail_id ");
		sb.append(" 	,mpd.detail_code ");
		sb.append("     ,mpd.detail_value ");
		sb.append("     ,mpd.status ");
		sb.append("     ,mpdStat.detail_value status_value ");
		sb.append("     ,mp.code ");
		sb.append("     ,mp.value ");
		sb.append(" from mst_parameter_detail mpd ");
		sb.append(" 	inner join mst_parameter mp on mp.parameter_id = mpd.parameter_id ");
		sb.append(" 	left join mst_parameter_detail mpdStat on mpdStat.detail_code = mpd.status ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mpd.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		sb.append(" order by mpd.parameter_detail_id asc ");
		sb.append(" LIMIT " + first + " , " + pageSize + " ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<ParameterDetail> vo = new ArrayList<ParameterDetail>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				ParameterDetail data = new ParameterDetail();
				
				data.setParameterDetailId(obj[0] != null ? (MathUtil.returnIdObjToLong(obj[0])) : null);
				data.setDetailCode(obj[1] != null ? (String) obj[1] : null);
				data.setDetailValue(obj[2] != null ? (String) obj[2] : null);
				data.setStatusCode(obj[3] != null ? (String) obj[3] : null);
				data.setStatus(obj[4] != null ? (String) obj[4] : null);
				data.setParamCode(obj[5] != null ? (String) obj[5] : null);
				data.setParamValue(obj[6] != null ? (String) obj[6] : null);
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
		sb.append(" from mst_parameter_detail mpd ");
		sb.append(" 	inner join mst_parameter mp on mp.parameter_id = mpd.parameter_id ");
		sb.append(" 	left join mst_parameter_detail mpdStat on mpdStat.detail_code = mpd.status ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mpd.record_flag = 'Y' ");
		
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
	public List<Parameter> getParameterList() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mp.parameter_id ");
		sb.append(" 	,mp.code ");
		sb.append("     ,mp.value ");
		sb.append(" from mst_parameter mp ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mp.record_flag = 'Y' ");
		sb.append(" order by mp.value ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		
		List<Object[]> result = query.getResultList();
		List<Parameter> vo = new ArrayList<Parameter>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				Parameter data = new Parameter();
				
				data.setParameterId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setCode(obj[1] != null ? (String) obj[1] : null);
				data.setValue(obj[2] != null ? (String) obj[2] : null);
				
				vo.add(data);
			}
		}
		
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParameterDetail> getParameterDetaiListByCode(String code, Boolean orderBy) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mpd.parameter_detail_id ");
		sb.append(" 	,mpd.detail_code ");
		sb.append("     ,mpd.detail_value ");
		sb.append(" from mst_parameter_detail mpd ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mpd.record_flag = 'Y' ");
		sb.append("     and mpd.status = 'STATUS_ACTIVE' ");
		sb.append("     and mpd.code = :code ");
		
		if (orderBy) {
			sb.append(" order by mpd.detail_value asc ");
		} else {
			sb.append(" order by mpd.detail_value desc ");
		}
		
		Query query = getSession().createSQLQuery(sb.toString());
		query.setParameter("code", code);
		
		List<Object[]> result = query.getResultList();
		List<ParameterDetail> vo = new ArrayList<ParameterDetail>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				ParameterDetail data = new ParameterDetail();
				
				data.setParameterDetailId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setDetailCode(obj[1] != null ? (String) obj[1] : null);
				data.setDetailValue(obj[2] != null ? (String) obj[2] : null);
				
				vo.add(data);
			}
		}
		
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ParameterDetail getParameterDetailByDetailCode(String detailCode) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mpd.parameter_detail_id ");
		sb.append(" 	,mpd.detail_code ");
		sb.append("     ,mpd.detail_value ");
		sb.append(" from mst_parameter_detail mpd ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mpd.record_flag = 'Y' ");
		sb.append("     and mpd.status = 'STATUS_ACTIVE' ");
		sb.append("     and mpd.detail_code = :detailCode ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		query.setParameter("detailCode", detailCode);
		
		List<Object[]> result = query.getResultList();
		ParameterDetail data = new ParameterDetail();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
			
				data.setParameterDetailId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setDetailCode(obj[1] != null ? (String) obj[1] : null);
				data.setDetailValue(obj[2] != null ? (String) obj[2] : null);
				
			}
		}
		
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ParameterDetail getParameterDetailByDetailValue(String detailValue) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mpd.parameter_detail_id ");
		sb.append(" 	,mpd.detail_code ");
		sb.append("     ,mpd.detail_value ");
		sb.append(" from mst_parameter_detail mpd ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mpd.record_flag = 'Y' ");
		sb.append("     and mpd.status = 'STATUS_ACTIVE' ");
		sb.append("     and mpd.detail_value = :detailValue ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		query.setParameter("detailValue", detailValue);
		
		List<Object[]> result = query.getResultList();
		ParameterDetail data = new ParameterDetail();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
			
				data.setParameterDetailId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setDetailCode(obj[1] != null ? (String) obj[1] : null);
				data.setDetailValue(obj[2] != null ? (String) obj[2] : null);
				
			}
		}
		
		return data;
	}

}

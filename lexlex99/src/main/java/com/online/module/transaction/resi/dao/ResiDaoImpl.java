package com.online.module.transaction.resi.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import com.online.module.transaction.resi.model.Resi;

@Repository("resiDao")
public class ResiDaoImpl extends GenericDaoHibernate<Resi, Long> implements ResiDao, Serializable {

	private static final long serialVersionUID = 6319722717841400907L;

	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(Constants.WHERE_START_DATE, col)) {
						sb.append(" and DATE(tr.receipt_date) >= DATE(:startDate) ");
					}
					if (StringUtils.equals(Constants.WHERE_END_DATE, col)) {
						sb.append(" and DATE(tr.receipt_date) <= DATE(:endDate) ");
					}
					if (StringUtils.equals(Constants.WHERE_STATUS, col)) {
						sb.append(" and tr.payment_status = :status ");
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
					if (StringUtils.equals(Constants.WHERE_START_DATE, col)) {
						query.setParameter("startDate", val);
					}
					if (StringUtils.equals(Constants.WHERE_END_DATE, col)) {
						query.setParameter("endDate", val);
					}
					if (StringUtils.equals(Constants.WHERE_STATUS, col)) {
						query.setParameter("status", val);
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Resi> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select tr.receipt_id ");
		sb.append(" 	,tr.receipt_date ");
		sb.append(" 	,tr.payment_status payment_status_code ");
		sb.append(" 	,mpd.detail_value payment_status_value ");
		sb.append(" from trc_receipt tr ");
		sb.append(" 	left join mst_parameter_detail mpd on mpd.detail_code = tr.payment_status ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and tr.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		sb.append(" order by tr.receipt_id desc ");

		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<Resi> vo = new ArrayList<>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = result.get(i);
				Resi data = new Resi();
				
				data.setReceiptId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setReceiptDate(obj[1] != null ? (Date) obj[1] : null);
				data.setPaymentStatusCode(obj[2] != null ? (String) obj[2] : null);
				data.setPaymentStatus(obj[3] != null ? (String) obj[3] : null);
				
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
		sb.append(" from trc_receipt tr ");
		sb.append(" 	left join mst_parameter_detail mpd on mpd.detail_code = tr.payment_status ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and tr.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

}

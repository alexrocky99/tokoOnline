package com.online.module.transaction.penjualan.dao;

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
import com.online.module.transaction.penjualan.model.Penjualan;

@Repository("penjualanDao")
public class PenjualanDaoImpl extends GenericDaoHibernate<Penjualan, Long> implements PenjualanDao, Serializable {

	private static final long serialVersionUID = 3365480329051696653L;

	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotBlank(val)) {
					if (StringUtils.equals(Constants.WHERE_INVOICE, col)) {
						sb.append(" and upper(tp.invoice) like upper(:invoice) ");
					}
					if (StringUtils.equals(Constants.WHERE_NAME, col)) {
						sb.append(" and upper(tp.customer_name) like upper(:name) ");
					}
					if (StringUtils.equals(Constants.WHERE_START_DATE, col)) {
						sb.append(" and DATE(tp.transaction_date) >= DATE(:startDate) ");
					}
					if (StringUtils.equals(Constants.WHERE_END_DATE, col)) {
						sb.append(" and DATE(tp.transaction_date) <= DATE(:endDate) ");
					}
					if (StringUtils.equals(Constants.WHERE_RECEIPT_NUMBER, col)) {
						sb.append(" and upper(tp.receipt_number) like upper(:receiptNumber) ");
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
				
				if (StringUtils.isNotBlank(val)) {
					if (StringUtils.equals(Constants.WHERE_INVOICE, col)) {
						query.setParameter("invoice", "%"+val+"%");
					}
					if (StringUtils.equals(Constants.WHERE_NAME, col)) {
						query.setParameter("name", "%"+val+"%");
					}
					if (StringUtils.equals(Constants.WHERE_START_DATE, col)) {
						query.setParameter("startDate", val);
					}
					if (StringUtils.equals(Constants.WHERE_END_DATE, col)) {
						query.setParameter("endDate", val);
					}
					if (StringUtils.equals(Constants.WHERE_RECEIPT_NUMBER, col)) {
						query.setParameter("receiptNumber", "%"+val+"%");
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Penjualan> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select tp.penjualan_id ");
		sb.append(" 	,tp.invoice ");
		sb.append(" 	,tp.customer_name ");
		sb.append(" 	,tp.transaction_date ");
		sb.append(" 	,tp.receipt_number ");
		sb.append(" from trc_penjualan tp ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and tp.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		sb.append(" order by tp.transaction_date desc ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<Penjualan> vo = new ArrayList<Penjualan>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				Penjualan data = new Penjualan();
				
				data.setPenjualanId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setInvoice(obj[1] != null ? (String) obj[1] : null);
				data.setCustomerName(obj[2] != null ? (String) obj[2] : null);
				data.setTransactionDate(obj[3] != null ? (Date) obj[3] : null);
				data.setReceiptNumber(obj[4] != null ? (String) obj[4] : null);
				
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
		sb.append(" from trc_penjualan tp ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and tp.record_flag = 'Y' ");
		
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

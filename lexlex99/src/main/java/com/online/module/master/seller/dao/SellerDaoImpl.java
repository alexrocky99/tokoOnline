package com.online.module.master.seller.dao;

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
import com.online.module.master.seller.model.Seller;

@Repository("sellerDao")
public class SellerDaoImpl extends GenericDaoHibernate<Seller, Long> implements SellerDao, Serializable {

	private static final long serialVersionUID = -8565409151458943828L;

	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(Constants.WHERE_START_DATE, col)) {
						sb.append(" and DATE(ms.effective_start_date) >= DATE(:startDate) ");
					}
					if (StringUtils.equals(Constants.WHERE_END_DATE, col)) {
						sb.append(" and DATE(ms.effective_end_date) <= DATE(:endDate) ");
					}
					if (StringUtils.equals(Constants.WHERE_NAME, col)) {
						sb.append(" and upper(ms.seller_name) like upper(:sellerName) ");
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
					if (StringUtils.equals(Constants.WHERE_NAME, col)) {
						query.setParameter("sellerName", "%"+val+"%");
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Seller> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select ms.seller_id ");
		sb.append(" 	,ms.seller_name ");
		sb.append(" 	,ms.effective_start_date ");
		sb.append(" 	,ms.effective_end_date ");
		sb.append(" from mst_seller ms ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and ms.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		sb.append(" order by ms.seller_name asc ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<Seller> vo = new ArrayList<>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = result.get(i);
				Seller data = new Seller();
				
				data.setSellerId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setSellerName(obj[1] != null ? (String) obj[1] : null);
				data.setEffectiveStartDate(obj[2] != null ? (Date) obj[2] : null);
				data.setEffectiveEndDate(obj[3] != null ? (Date) obj[3] : null);
				
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
		sb.append(" from mst_seller ms ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and ms.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

	@Override
	public Boolean checkDataSellerById(Long sellerId, String sellerName) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select count(1) ");
		sb.append(" from mst_seller ms ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and upper(ms.seller_name) = upper(:sellerName) ");
		
		if (sellerId != null) {
			sb.append(" 	and ms.seller_id <> :sellerId ");
		}
		
		Query query = getSession().createSQLQuery(sb.toString());
		query.setParameter("sellerName", sellerName);
		
		if (sellerId != null) {
			query.setParameter("sellerId", sellerId);
		}
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			return false;
		} else {
			if (result.longValue() > 0) {
				return false;
			} else {
				return true;
			}
		}
	}

}

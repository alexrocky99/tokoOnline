package com.online.module.master.product.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.stereotype.Repository;

import com.online.module.common.constant.Constants;
import com.online.module.common.dao.GenericDaoHibernate;
import com.online.module.common.paging.SearchObject;
import com.online.module.common.util.MathUtil;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.model.ProductModalDetail;

@Repository("productDao")
public class ProductDaoImpl extends GenericDaoHibernate<Product, Long> implements ProductDao, Serializable {

	private static final long serialVersionUID = -58450056781333273L;
	private static final Logger logger = Logger.getLogger(ProductDaoImpl.class);
	
	@SuppressWarnings("rawtypes")
	private void setQueryWhereString(StringBuilder sb, List<? extends SearchObject> searchCriteria) {
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_NAME)) {
						sb.append(" and upper(mp.product_name) like upper(:name) ");
					}
					if (StringUtils.equals(col, Constants.WHERE_CODE)) {
						sb.append(" and upper(mp.product_code) like upper(:code) ");
					}
					if (StringUtils.equals(col, Constants.WHERE_SORT_BY)) {
						if (StringUtils.equals(val, Constants.PARAM_DET_CODE_SORT_BY_PRODUCT_PRICE_NULL)) {
							sb.append(" 	and exists ( ");
							sb.append(" 		select count(1) ");
							sb.append(" 		from mst_product_modal mpm ");
							sb.append(" 		where 1 = 1 ");
							sb.append(" 			and mpm.current_price is null ");
							sb.append(" 	) ");
						}
						if (StringUtils.equals(val, Constants.PARAM_DET_CODE_SORT_BY_PRODUCT_PRICE_NOT_NULL)) {
							sb.append(" 	and exists ( ");
							sb.append(" 		select count(1) ");
							sb.append(" 		from mst_product_modal mpm ");
							sb.append(" 		where 1 = 1 ");
							sb.append(" 			and mpm.current_price is not null ");
							sb.append(" 	) ");
						}
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
					if (StringUtils.equals(col, Constants.WHERE_CODE)) {
						query.setParameter("code", "%"+val+"%");
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Product> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mp.product_id ");
		sb.append(" 	,mp.product_code ");
		sb.append("     ,mp.sku ");
		sb.append("     ,mp.product_name ");
		sb.append("     ,mp.barcode ");
		sb.append("     ,mp.total_stock ");
		sb.append("     ,mp.price ");
		sb.append(" from mst_product mp ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mp.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		sb.append(" order by mp.product_name asc ");
		sb.append(" LIMIT " + first + " , " + pageSize + " ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		List<Object[]> result = query.getResultList();
		List<Product> vo = new ArrayList<>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				Product data = new Product();
				
				data.setProductId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setProductCode(obj[1] != null ? (String) obj[1] : null);
				data.setSku(obj[2] != null ? (String) obj[2] : null);
				data.setProductName(obj[3] != null ? (String) obj[3] : null);
				data.setBarcodeByte(obj[4] != null ? (byte[]) obj[4] : null);
				data.setTotalStock(obj[5] != null ? ((Number) obj[5]).intValue()  : null);
				data.setPrice(obj[6] != null ? new BigDecimal(((Number) obj[6]).doubleValue()) : null);
				
				if (data.getBarcodeByte() != null) {
					ImageIcon imageIcon = new ImageIcon(data.getBarcodeByte());
					data.setBarcodeImage(imageIcon.getImage());
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
		sb.append(" from mst_product mp ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mp.record_flag = 'Y' ");
		
		this.setQueryWhereString(sb, searchCriteria);
		Query query = getSession().createSQLQuery(sb.toString());
		this.getQueryWhereString(query, searchCriteria);
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

	public static Logger getLogger() {
		return logger;
	}

	@Override
	public Long totalProductInTable() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select count(1) from mst_product mp where 1 = 1 ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductModalDetail> getAllDataProductModalDetailByModalId(Long productModalId) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mpmd.price ");
		sb.append(" 	,mpmd.stock ");
		sb.append(" 	,mpmd.create_on ");
		sb.append(" from mst_product_modal_detail mpmd ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mpmd.record_flag = 'Y' ");
		sb.append("     and mpmd.product_modal_id = :productModalId ");
		sb.append(" order by mpmd.create_on desc ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		query.setParameter("productModalId", productModalId);
		
		List<Object[]> result = query.getResultList();
		List<ProductModalDetail> vo = new ArrayList<ProductModalDetail>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				ProductModalDetail data = new ProductModalDetail();
				
				data.setPrice(obj[0] != null ? new BigDecimal(((Number) obj[0]).doubleValue()) : null);
				data.setStock(obj[1] != null ? ((Number) obj[1]).intValue() : null);
				data.setCreateOnStr(obj[2] != null ? Constants.FORMAT_DATE_TIME_WITH_STRIP.format((Date) obj[2]) : null);
				     
				vo.add(data);
			}
		}
		
		return vo;
	}

	@Override
	public Long getProductIdByCode(String productCode) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mp.product_id from mst_product mp where 1 = 1 and mp.product_code = :productCode ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		query.setParameter("productCode", productCode);
		
		Number result = (Number) query.getSingleResult();
		
		if (result == null) {
			result = 0;
		}
		
		return result.longValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProduct() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mp.product_id ");
		sb.append(" 	,mp.product_name ");
		sb.append(" 	,mp.price ");
		sb.append(" 	,mp.total_stock ");
		sb.append(" from mst_product mp ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mp.record_flag = 'Y' ");
		sb.append(" order by mp.product_name asc ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		
		List<Object[]> result = query.getResultList();
		List<Product> vo = new ArrayList<Product>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				Product data = new Product();
				
				data.setProductId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setProductName(obj[1] != null ? (String) obj[1] : null);
				data.setPrice(obj[2] != null ? new BigDecimal(((Number) obj[2]).doubleValue()): null);
				data.setTotalStock(obj[3] != null ? ((Number) obj[3]).intValue() : null);
				vo.add(data);
			}
		}
		
		return vo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Product> searchDataProductDialog(List<? extends SearchObject> searchCriteria) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select mp.product_id ");
		sb.append(" 	,mp.product_name ");
		sb.append(" 	,mp.price ");
		sb.append(" 	,mp.total_stock ");
		sb.append(" from mst_product mp ");
		sb.append(" where 1 = 1 ");
		sb.append(" 	and mp.record_flag = 'Y' ");
		
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				String col = searchVal.getSearchColumn();
				String val = searchVal.getSearchValueAsString();
				
				if (StringUtils.isNotEmpty(val)) {
					if (StringUtils.equals(col, Constants.WHERE_NAME)) {
						sb.append(" and upper(mp.product_name) like upper(:name) ");
					}
				}
			}
		}
		
		sb.append(" order by mp.product_name asc ");
		
		Query query = getSession().createSQLQuery(sb.toString());
		
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
		
		List<Object[]> result = query.getResultList();
		List<Product> vo = new ArrayList<Product>();
		
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Object[] obj = (Object[]) result.get(i);
				Product data = new Product();
				
				data.setProductId(obj[0] != null ? MathUtil.returnIdObjToLong(obj[0]) : null);
				data.setProductName(obj[1] != null ? (String) obj[1] : null);
				data.setPrice(obj[2] != null ? new BigDecimal(((Number) obj[2]).doubleValue()): null);
				data.setTotalStock(obj[3] != null ? ((Number) obj[3]).intValue() : null);
				vo.add(data);
			}
		}
		
		return vo;
	}

}

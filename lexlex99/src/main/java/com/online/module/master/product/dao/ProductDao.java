package com.online.module.master.product.dao;

import java.util.List;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.common.paging.SearchObject;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.model.ProductModalDetail;

public interface ProductDao extends GenericDao<Product, Long>, RetrieverDataPage<Product> {

	Long totalProductInTable();
	
	List<ProductModalDetail> getAllDataProductModalDetailByModalId(Long productModalId);
	
	Long getProductIdByCode(String productCode);
	
	List<Product> getAllProduct();
	
	@SuppressWarnings("rawtypes")
	List<Product> searchDataProductDialog(List<? extends SearchObject> searchCriteria);
	
}

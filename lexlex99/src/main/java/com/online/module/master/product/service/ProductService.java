package com.online.module.master.product.service;

import java.util.List;

import org.primefaces.model.StreamedContent;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.common.paging.SearchObject;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.model.ProductModalDetail;

public interface ProductService extends RetrieverDataPage<Product> {

	public void save(Product product);
	public void update(Product product);
	public Product findById(Long productId);
	public StreamedContent downloadTemplateStep1();
	public Long totalProductInTable();
	public void saveProduct(List<Product> productList, Boolean isRaw) throws Exception;
	public List<ProductModalDetail> getAllDataProductModalDetailByModalId(Long productModalId);
	public StreamedContent downloadTemplateStep2();
	public Long getProductIdByCode(String productCode);
	public List<Product> getAllProduct();
	@SuppressWarnings("rawtypes")
	public List<Product> searchDataProductDialog(List<? extends SearchObject> searchCriteria);
	
}

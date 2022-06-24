package com.online.module.master.product.service;

import com.online.module.master.product.model.ProductModal;

public interface ProductModalService {

	public void update(ProductModal productModal);
	public ProductModal findById(Long productModalId);
	
}

package com.online.module.master.product.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.module.master.product.dao.ProductModalDao;
import com.online.module.master.product.model.ProductModal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Transactional
@Service("productModalService")
public class ProductModalServiceImpl implements ProductModalService, Serializable {

	private static final long serialVersionUID = -1770167907307136194L;

	@Autowired
	@Qualifier("productModalDao")
	private ProductModalDao productModalDao;
	
	@Override
	public ProductModal findById(Long productModalId) {
		return productModalDao.findById(productModalId);
	}

	@Override
	public void update(ProductModal productModal) {
		productModalDao.update(productModal);
	}

}

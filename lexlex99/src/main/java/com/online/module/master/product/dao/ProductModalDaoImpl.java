package com.online.module.master.product.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.online.module.common.dao.GenericDaoHibernate;
import com.online.module.master.product.model.ProductModal;

@Repository("productModalDao")
public class ProductModalDaoImpl extends GenericDaoHibernate<ProductModal, Long> implements ProductModalDao, Serializable {

	private static final long serialVersionUID = 7640313040929665449L;

}

package com.online.module.master.seller.dao;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.seller.model.Seller;

public interface SellerDao extends GenericDao<Seller, Long>, RetrieverDataPage<Seller> {

	public Boolean checkDataSellerById(Long sellerId, String sellerName);
	
}

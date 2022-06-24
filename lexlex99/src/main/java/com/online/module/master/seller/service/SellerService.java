package com.online.module.master.seller.service;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.seller.model.Seller;

public interface SellerService extends RetrieverDataPage<Seller> {

	public void save(Seller seller);
	public void update(Seller seller);
	public void delete(Seller seller);
	public Seller findById(Long sellerId);
	public Boolean checkDataSellerById(Long sellerId, String sellerName);
	
}

package com.online.module.master.seller.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.module.common.paging.SearchObject;
import com.online.module.master.seller.dao.SellerDao;
import com.online.module.master.seller.model.Seller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Transactional
@Service("sellerService")
public class SellerServiceImpl implements SellerService, Serializable {

	private static final long serialVersionUID = 4035751852198902018L;
	
	@Autowired
	@Qualifier("sellerDao")
	private SellerDao sellerDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Seller> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return sellerDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return sellerDao.searchCountData(searchCriteria);
	}

	@Override
	public void save(Seller seller) {
		sellerDao.save(seller);
	}

	@Override
	public void update(Seller seller) {
		sellerDao.update(seller);
	}

	@Override
	public void delete(Seller seller) {
		sellerDao.delete(seller);
	}

	@Override
	public Seller findById(Long sellerId) {
		return sellerDao.findById(sellerId);
	}

	@Override
	public Boolean checkDataSellerById(Long sellerId, String sellerName) {
		return sellerDao.checkDataSellerById(sellerId, sellerName);
	}

}

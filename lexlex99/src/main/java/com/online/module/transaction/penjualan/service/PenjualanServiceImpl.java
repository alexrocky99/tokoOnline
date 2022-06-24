package com.online.module.transaction.penjualan.service;

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
import com.online.module.transaction.penjualan.dao.PenjualanDao;
import com.online.module.transaction.penjualan.model.Penjualan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Transactional
@Service("penjualanService")
public class PenjualanServiceImpl implements PenjualanService, Serializable {

	private static final long serialVersionUID = 1992818283081962326L;
	
	@Autowired
	@Qualifier("penjualanDao")
	private PenjualanDao penjualanDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Penjualan> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return penjualanDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return penjualanDao.searchCountData(searchCriteria);
	}

	@Override
	public void save(Penjualan penjualan) {
		penjualanDao.save(penjualan);
	}

	@Override
	public void update(Penjualan penjualan) {
		penjualanDao.update(penjualan);
	}

	@Override
	public void delete(Penjualan penjualan) {
		penjualanDao.delete(penjualan);
	}

	@Override
	public Penjualan findById(Long penjualanId) {
		return penjualanDao.findById(penjualanId);
	}

}

package com.online.module.transaction.resi.service;

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
import com.online.module.transaction.resi.dao.ResiDao;
import com.online.module.transaction.resi.model.Resi;

import lombok.Getter;
import lombok.Setter;

@Transactional
@Service("resiService")
@Getter
@Setter
public class ResiServiceImpl implements ResiService, Serializable {

	private static final long serialVersionUID = 1050893166893665559L;

	@Autowired
	@Qualifier("resiDao")
	private ResiDao resiDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Resi> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return resiDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return resiDao.searchCountData(searchCriteria);
	}

	@Override
	public void save(Resi resi) {
		resiDao.save(resi);
	}

	@Override
	public void update(Resi resi) {
		resiDao.update(resi);
	}

	@Override
	public void delete(Resi resi) {
		resiDao.delete(resi);
	}

	@Override
	public Resi findById(Long resiId) {
		return resiDao.findById(resiId);
	}

}

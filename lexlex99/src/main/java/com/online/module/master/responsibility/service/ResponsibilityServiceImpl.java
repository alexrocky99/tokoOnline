package com.online.module.master.responsibility.service;

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
import com.online.module.master.responsibility.dao.ResponsibilityDao;
import com.online.module.master.responsibility.model.Responsibility;
import com.online.module.master.responsibility.model.ResponsibilityDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service("responsibilityService")
@Transactional
public class ResponsibilityServiceImpl implements ResponsibilityService, Serializable {

	private static final long serialVersionUID = -1211926394099090227L;

	@Autowired
	@Qualifier("responsibilityDao")
	private ResponsibilityDao responsibilityDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Responsibility> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return responsibilityDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return responsibilityDao.searchCountData(searchCriteria);
	}

	@Override
	public List<Responsibility> getAllResponsibility() {
		return responsibilityDao.getAllResponsibility();
	}

	@Override
	public void save(Responsibility responsibility) {
		responsibilityDao.save(responsibility);
	}

	@Override
	public void update(Responsibility responsibility) {
		responsibilityDao.update(responsibility);
	}

	@Override
	public Responsibility findById(Long responsibilityId) {
		return responsibilityDao.findById(responsibilityId);
	}

	@Override
	public List<ResponsibilityDetail> searchResponsibilityAllMenu(ResponsibilityDetail responsibilityDetail) {
		return responsibilityDao.searchResponsibilityAllMenu(responsibilityDetail);
	}

	@Override
	public void deleteInsertResponsibilityMenu(ResponsibilityDetail responsibilityDetail, String user) {
		responsibilityDao.deleteInsertResponsibilityMenu(responsibilityDetail, user);
	}

}

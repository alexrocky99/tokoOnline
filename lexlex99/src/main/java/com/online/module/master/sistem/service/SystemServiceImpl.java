package com.online.module.master.sistem.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.module.common.constant.Constants;
import com.online.module.common.paging.SearchObject;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.sistem.dao.SystemDao;
import com.online.module.master.sistem.model.System;

import lombok.Getter;
import lombok.Setter;

@Transactional
@Service("systemService")
@Getter
@Setter
public class SystemServiceImpl implements SystemService, Serializable {

	private static final long serialVersionUID = 1443220286808823177L;

	@Autowired
	@Qualifier("systemDao")
	private SystemDao systemDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<System> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return systemDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return systemDao.searchCountData(searchCriteria);
	}

	@Override
	public System getSistemDataBySistemCode(String systemCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public System findById(Long systemId) {
		return systemDao.findById(systemId);
	}

	@Override
	public void updateStatus(Long systemId, Boolean isStatus, String username) {
		System system = systemDao.findById(systemId);
		system.setStatus(isStatus ? Constants.PARAM_DET_CODE_STATUS_ACTIVE : Constants.PARAM_DET_CODE_STATUS_NON_ACTIVE);
		EntityUtil.setUpdateInfo(system, username);
		systemDao.update(system);
	}

}

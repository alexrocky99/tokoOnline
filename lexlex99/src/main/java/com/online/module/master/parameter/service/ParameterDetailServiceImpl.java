package com.online.module.master.parameter.service;

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
import com.online.module.master.parameter.dao.ParameterDetailDao;
import com.online.module.master.parameter.model.Parameter;
import com.online.module.master.parameter.model.ParameterDetail;

import lombok.Getter;
import lombok.Setter;

@Transactional
@Service("parameterDetailService")
@Getter
@Setter
public class ParameterDetailServiceImpl implements ParameterDetailService, Serializable {

	private static final long serialVersionUID = 317859263228533393L;
	
	@Autowired
	@Qualifier("parameterDetailDao")
	private ParameterDetailDao parameterDetailDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ParameterDetail> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return parameterDetailDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return parameterDetailDao.searchCountData(searchCriteria);
	}

	@Override
	public void save(ParameterDetail parameterDetail) {
		parameterDetailDao.save(parameterDetail);
	}

	@Override
	public void update(ParameterDetail parameterDetail) {
		parameterDetailDao.update(parameterDetail);
	}

	@Override
	public ParameterDetail findById(Long parameterDetailId) {
		return parameterDetailDao.findById(parameterDetailId);
	}

	@Override
	public void changeStatus(Boolean isStatus, Long parameterDetailId, String username) {
		ParameterDetail parameterDetail = parameterDetailDao.findById(parameterDetailId);
		parameterDetail.setStatus(isStatus ? Constants.PARAM_DET_CODE_STATUS_ACTIVE : Constants.PARAM_DET_CODE_STATUS_NON_ACTIVE);
		EntityUtil.setUpdateInfo(parameterDetail, username);
		parameterDetailDao.update(parameterDetail);
	}

	@Override
	public List<Parameter> getParameterList() {
		return parameterDetailDao.getParameterList();
	}

	@Override
	public List<ParameterDetail> getParameterDetaiListByCode(String code, Boolean orderBy) {
		return parameterDetailDao.getParameterDetaiListByCode(code, orderBy);
	}

	@Override
	public ParameterDetail getParameterDetailByDetailCode(String detailCode) {
		return parameterDetailDao.getParameterDetailByDetailCode(detailCode);
	}

	@Override
	public ParameterDetail getParameterDetailByDetailValue(String detailValue) {
		return parameterDetailDao.getParameterDetailByDetailValue(detailValue);
	}

}

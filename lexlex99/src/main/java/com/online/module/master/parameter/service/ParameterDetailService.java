package com.online.module.master.parameter.service;

import java.util.List;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.parameter.model.Parameter;
import com.online.module.master.parameter.model.ParameterDetail;

public interface ParameterDetailService extends RetrieverDataPage<ParameterDetail> {

	public void save(ParameterDetail parameterDetail);
	public void update(ParameterDetail parameterDetail);
	public ParameterDetail findById(Long parameterDetailId);
	public void changeStatus(Boolean isStatus, Long parameterDetailId, String username);
	public List<Parameter> getParameterList();
	public List<ParameterDetail> getParameterDetaiListByCode(String code, Boolean orderBy);
	public ParameterDetail getParameterDetailByDetailCode(String detailCode);
	public ParameterDetail getParameterDetailByDetailValue(String detailValue);
	
}

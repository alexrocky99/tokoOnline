package com.online.module.master.parameter.dao;

import java.util.List;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.parameter.model.Parameter;
import com.online.module.master.parameter.model.ParameterDetail;

public interface ParameterDetailDao extends GenericDao<ParameterDetail, Long>, RetrieverDataPage<ParameterDetail> {

	public List<Parameter> getParameterList();
	public List<ParameterDetail> getParameterDetaiListByCode(String code, Boolean orderBy);
	public ParameterDetail getParameterDetailByDetailCode(String detailCode);
	public ParameterDetail getParameterDetailByDetailValue(String detailValue);
	
}
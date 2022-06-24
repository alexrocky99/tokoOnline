package com.online.module.master.parameter.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.module.master.parameter.dao.ParameterDao;
import com.online.module.master.parameter.model.Parameter;

import lombok.Getter;
import lombok.Setter;

@Transactional
@Service("parameterService")
@Getter
@Setter
public class ParameterServiceImpl implements ParameterService, Serializable {

	private static final long serialVersionUID = 725137741980148042L;

	@Autowired
	@Qualifier("parameterDao")
	private ParameterDao parameterDao;
	
	@Override
	public Parameter findById(Long parameterId) {
		return parameterDao.findById(parameterId);
	}

}

package com.online.module.master.parameter.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.online.module.common.dao.GenericDaoHibernate;
import com.online.module.master.parameter.model.Parameter;

@Repository("parameterDao")
public class ParameterDaoImpl extends GenericDaoHibernate<Parameter, Long> implements ParameterDao, Serializable {

	private static final long serialVersionUID = 4558917713547320888L;

}

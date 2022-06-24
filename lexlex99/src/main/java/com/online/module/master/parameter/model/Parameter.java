package com.online.module.master.parameter.model;

import java.util.List;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameter extends BaseEntity {

	private Long parameterId;
	private String code;
	private String value;
	private String description;
	
	private List<ParameterDetail> parameterDetailList;
	
}

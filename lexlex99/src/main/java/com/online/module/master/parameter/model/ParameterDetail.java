package com.online.module.master.parameter.model;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterDetail extends BaseEntity {

	private Long parameterDetailId;
	private Parameter parameter;
	private String detailCode;
	private String detailValue;
	private String detailDescription;
	private String code;
	private String status;
	
	// helper
	private String paramCode;
	private String paramValue;
	private String statusCode;
	private Boolean isStatus;
}

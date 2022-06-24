package com.online.module.master.sistem.model;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class System extends BaseEntity {

	private Long systemId;
	private String systemCode;
	private String systemValue;
	private String systemDescription;
	private String status;
	
	// Helper Attribute
	private String statusCode;
	private Boolean isStatus;
	
}

package com.online.module.common.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {

	protected String createBy;
	protected Timestamp createOn;
	protected String updateBy;
	protected Timestamp updateOn;
	protected String recordFlag;
	
}

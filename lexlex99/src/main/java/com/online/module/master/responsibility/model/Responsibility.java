package com.online.module.master.responsibility.model;

import java.util.ArrayList;
import java.util.List;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Responsibility extends BaseEntity {

	private Long responsibilityId;
	private String name;
	private String description;

	private List<ResponsibilityDetail> responsibilityDetailList = new ArrayList<ResponsibilityDetail>();
	
}

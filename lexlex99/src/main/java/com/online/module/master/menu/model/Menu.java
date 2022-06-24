package com.online.module.master.menu.model;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menu extends BaseEntity {

	private Long menuId;
	private String name;
	private String action;
	private String fontawesome;
	private String description;
	private Long parentId;
	private Long menuLevel;
	private Long menuOrder;
	private String menuType;
	
}

package com.online.module.menuSession.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuSession implements Serializable {

	private static final long serialVersionUID = 5219426200259334515L;
	
	private Long menuId;
	private String menuName;
	private String menuAction;
	private Long parentId;
	private String fontawesome;
	private String description;
	private Integer menuLevel;
	private Integer menuOrder;

	private List<MenuSession> detail;

}

package com.online.module.master.responsibility.model;

import java.util.List;

import com.online.module.common.model.BaseEntity;
import com.online.module.master.menu.model.Menu;

public class ResponsibilityDetail extends BaseEntity {

	private Long responsibilityDetailId;
	private Responsibility responsibility;
	private Menu menu;
	
	private Long responsibilityId;
	private Long parentId;
	private Long menuId;
	private String menuName;
	private String check2;
	private String menuAction;
	private Boolean check;
	private int childResponsibilityMenuSize;
	
	private List<ResponsibilityDetail> childResponsibilityMenu;

	public Long getResponsibilityDetailId() {
		return responsibilityDetailId;
	}

	public void setResponsibilityDetailId(Long responsibilityDetailId) {
		this.responsibilityDetailId = responsibilityDetailId;
	}

	public Responsibility getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Responsibility responsibility) {
		this.responsibility = responsibility;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Long getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityId(Long responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getCheck2() {
		return check2;
	}

	public void setCheck2(String check2) {
		this.check2 = check2;
		setCheck(new Boolean(check2));
	}

	public String getMenuAction() {
		return menuAction;
	}

	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public int getChildResponsibilityMenuSize() {
		return childResponsibilityMenuSize;
	}

	public void setChildResponsibilityMenuSize(int childResponsibilityMenuSize) {
		this.childResponsibilityMenuSize = childResponsibilityMenuSize;
	}

	public List<ResponsibilityDetail> getChildResponsibilityMenu() {
		return childResponsibilityMenu;
	}

	public void setChildResponsibilityMenu(List<ResponsibilityDetail> childResponsibilityMenu) {
		this.childResponsibilityMenu = childResponsibilityMenu;
		if (childResponsibilityMenu != null) {
			setChildResponsibilityMenuSize(childResponsibilityMenu.size());
		}
	}
	
}

package com.online.module.master.menu.service;

import java.util.List;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.menu.model.Menu;

public interface MenuService extends RetrieverDataPage<Menu> {

	public Menu findById(Long menuId);
	public void save(Menu menu);
	public void edit(Menu menu);
	public List<Menu> getAllParentMenu();
	
}

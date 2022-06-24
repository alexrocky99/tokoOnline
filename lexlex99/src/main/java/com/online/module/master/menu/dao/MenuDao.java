package com.online.module.master.menu.dao;

import java.util.List;

import com.online.module.common.dao.GenericDao;
import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.menu.model.Menu;

public interface MenuDao extends GenericDao<Menu, Long>, RetrieverDataPage<Menu> {

	public List<Menu> getAllParentMenu();
	
}

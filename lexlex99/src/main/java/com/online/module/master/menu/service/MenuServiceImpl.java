package com.online.module.master.menu.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.module.common.paging.SearchObject;
import com.online.module.master.menu.dao.MenuDao;
import com.online.module.master.menu.model.Menu;

import lombok.Getter;
import lombok.Setter;

@Transactional
@Service("menuService")
@Getter
@Setter
public class MenuServiceImpl implements MenuService, Serializable {

	private static final long serialVersionUID = 3568624804652412564L;

	@Autowired
	@Qualifier("menuDao")
	private MenuDao menuDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Menu> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return menuDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return menuDao.searchCountData(searchCriteria);
	}

	@Override
	public Menu findById(Long menuId) {
		return menuDao.findById(menuId);
	}

	@Override
	public void save(Menu menu) {
		menuDao.save(menu);
	}

	@Override
	public void edit(Menu menu) {
		menuDao.update(menu);
	}

	@Override
	public List<Menu> getAllParentMenu() {
		return menuDao.getAllParentMenu();
	}

}

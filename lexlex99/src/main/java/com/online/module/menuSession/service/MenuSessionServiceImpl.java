package com.online.module.menuSession.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.online.module.menuSession.dao.MenuSessionDao;
import com.online.module.menuSession.model.MenuSession;

@Transactional
@Repository("menuSessionService")
public class MenuSessionServiceImpl implements MenuSessionService {

	@Autowired
	@Qualifier("menuSessionDao")
	private MenuSessionDao menuSessionDao;
	
	@Override
	public List<MenuSession> retrieveAllMenuAllowed(String userLogin) {
		List<MenuSession> result = new ArrayList<MenuSession>();
		List<MenuSession> listMenu = menuSessionDao.retrieveAllMenuAllowed(userLogin);
		Map<Long, MenuSession> root = new HashMap<Long, MenuSession>();
		List<Long> idList = new ArrayList<Long>();
		
		for (int i = 0; i < listMenu.size(); i++) {
			MenuSession ms = (MenuSession) listMenu.get(i);
			
			if (ms.getMenuLevel() != null && ms.getMenuLevel().equals(new Integer(1))) {
				root.put(ms.getMenuId(), ms);
				idList.add(ms.getMenuId());
			} else {
				MenuSession parent = (MenuSession) root.get(ms.getParentId());
				
				if (parent != null) {
					root.put(ms.getMenuId(), ms);
					if (parent.getDetail() == null) {
						List<MenuSession> parentList = new ArrayList<MenuSession>();
						parentList.add(ms);
						parent.setDetail(parentList);
					} else {
						parent.getDetail().add(ms);
					}
				}
			}
		}
		
		for (int i = 0; i < idList.size(); i++) {
			result.add(root.get(idList.get(i)));
		}
		
 		return result;
	}

	@Override
	public List<MenuSession> getListMenuAllowed(String userLogin) {
		return menuSessionDao.retrieveAllMenuAllowed(userLogin);
	}

	public MenuSessionDao getMenuSessionDao() {
		return menuSessionDao;
	}

	public void setMenuSessionDao(MenuSessionDao menuSessionDao) {
		this.menuSessionDao = menuSessionDao;
	}

}

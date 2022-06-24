package com.online.module.menuSession.dao;

import java.util.List;

import com.online.module.menuSession.model.MenuSession;

public interface MenuSessionDao {
	
	public List<MenuSession> retrieveAllMenuAllowed(String userLogin);

}

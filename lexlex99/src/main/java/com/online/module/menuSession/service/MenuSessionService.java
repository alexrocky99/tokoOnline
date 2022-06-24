package com.online.module.menuSession.service;

import java.util.List;

import com.online.module.menuSession.model.MenuSession;

public interface MenuSessionService {

	public List<MenuSession> retrieveAllMenuAllowed(String userLogin);
	public List<MenuSession> getListMenuAllowed(String userLogin);
}

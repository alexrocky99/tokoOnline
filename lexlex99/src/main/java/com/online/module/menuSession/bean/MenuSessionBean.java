package com.online.module.menuSession.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.online.module.common.constant.Constants;
import com.online.module.lov.bean.FacesUtil;
import com.online.module.menuSession.model.MenuSession;
import com.online.module.menuSession.service.MenuSessionService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuSessionBean implements Serializable{

	private static final long serialVersionUID = 6456901286703156648L;
	private static Logger logger = Logger.getLogger(MenuSessionBean.class);
	
	public MenuSessionService menuSessionService;
	
	public List<MenuSession> menuList;
	
	public FacesUtil facesUtil;
	
	public void initMenus() {
		
	}
	
	public List<MenuSession> getMenuList() {
		if (menuList == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
			
			if (session != null && (session.getAttribute(Constants.SESSION_USERNAME) != null)) {
				String userLogin = (String) session.getAttribute(Constants.SESSION_USERNAME);
				menuList = menuSessionService.retrieveAllMenuAllowed(userLogin);
			}
		}
		
		return menuList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		MenuSessionBean.logger = logger;
	}

}

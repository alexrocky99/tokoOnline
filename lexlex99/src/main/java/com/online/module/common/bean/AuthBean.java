package com.online.module.common.bean;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.online.module.common.constant.Constants;
import com.online.module.menuSession.model.MenuSession;

public class AuthBean {
	
	public boolean checkErrorPage(String viewId) {
		boolean isErrorPage = false;
		
		if (viewId.indexOf("401.jsf") >= 0) {
			isErrorPage = true;
		} else if (viewId.indexOf("403.jsf") >= 0) {
			isErrorPage = true;
		} else if (viewId.indexOf("404.jsf") >= 0) {
			isErrorPage = true;
		} else if (viewId.indexOf("500.jsf") >= 0) {
			isErrorPage = true;
		} else if (viewId.indexOf("view_expired_exception.jsf") >= 0) {
			isErrorPage = true;
		} else if (viewId.indexOf("document_generation_error.jsf") >= 0) {
			isErrorPage = true;
		}
		
		return isErrorPage;
	}
	
	public boolean checkExceptionPage(String viewId) {
		boolean isExceptionPage = false;
		
		if (viewId.indexOf("login.faces") >= 0) {
			isExceptionPage = true;
		} else if (viewId.indexOf("bd.faces") >= 0 && checkExceptionOther()) {
			isExceptionPage = true;
		} else if (viewId.indexOf("logout.jsf") >= 0) {
			isExceptionPage = true;
		} else if (viewId.indexOf(".css.jsf") >= 0) {
			isExceptionPage = true;
		}
		
		return isExceptionPage;
	}
	
	public boolean checkExceptionOther() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		
		String username = (String) session.getAttribute(Constants.SESSION_USERNAME);
		
		if (StringUtils.isNotBlank(username)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validateAccessRight(String viewId, List<MenuSession> menuSessions) {
		boolean isValid = false;
		
		if (checkErrorPage(viewId) || checkExceptionPage(viewId)) {
			return true;
		}
		
		if (menuSessions != null && menuSessions.size() > 0) {
			for (MenuSession m : menuSessions) {
				if (m.getMenuAction() != null && !m.getMenuAction().equals("-")) {
					String authorizedUrl = m.getMenuAction().split(".faces")[0];
					
					if (viewId.indexOf(authorizedUrl) >= 0) {
						isValid = true;
						break;
					}
				}
			}
		}
		
		return isValid;
	}
	
}

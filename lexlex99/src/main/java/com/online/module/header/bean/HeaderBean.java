package com.online.module.header.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.online.module.common.constant.Constants;
import com.online.module.lov.bean.FacesUtil;
import com.online.module.lov.bean.FacesUtil.FacesScope;
import com.online.module.master.people.model.People;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeaderBean implements Serializable {

	private static final long serialVersionUID = -4928991341284948865L;
	private static final Logger logger = Logger.getLogger(HeaderBean.class);
	
	private String username;
	private String menu;
	private String copyrigthYear;
	
	private FacesUtil facesUtil;
	
	@PostConstruct
	public void init() {
		if (FacesContext.getCurrentInstance() != null 
				&& FacesContext.getCurrentInstance().getExternalContext() != null
				&& FacesContext.getCurrentInstance().getExternalContext().getRequest() != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
			String yearBuild = "2020";
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			String yearInString = String.valueOf(year);
			
			if (yearBuild.equals(yearInString)) {
				copyrigthYear = yearBuild;
			} else {
				copyrigthYear = yearBuild + " - " + yearInString;
			}
			
			if (request != null && request.getSession(true) != null) {
				HttpSession session = request.getSession(true);
				People people = (People) session.getAttribute(Constants.SESSION_EMPLOYEE);
				if (people != null) {
					username = people.getFullName();
				}
				
				menu = (String) session.getAttribute(Constants.SESSION_MENU);
				if (menu == null) {
					showMenu();
				}
			}
		}
	}

	public void doHandleLogout() {
		logger.debug("loginBean doHandleLogout");
		
		facesUtil.removeAllSessionAttribute();
		facesUtil.removeAllManagedBeans(FacesScope.SESSION_SCOPE);
		
		try {
			facesUtil.redirect("/pages/login/login.faces?faces-redirect=true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void hideMenu() {
		menu = "sidebar-collapse";
		facesUtil.setSessionAttribute(Constants.SESSION_MENU, menu);
	}
	
	public void showMenu() {
		menu = "";
		facesUtil.setSessionAttribute(Constants.SESSION_MENU, menu);
	}

}

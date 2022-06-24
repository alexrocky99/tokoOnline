package com.online.module.login.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.online.module.common.constant.Constants;
import com.online.module.common.util.ConfigUtil;
import com.online.module.common.util.MD5Utils;
import com.online.module.lov.bean.FacesUtil;
import com.online.module.master.people.model.People;
import com.online.module.master.people.service.PeopleService;
import com.online.module.menuSession.model.MenuSession;
import com.online.module.menuSession.service.MenuSessionService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 440753168848192014L;
	private static final Logger logger = Logger.getLogger(LoginBean.class);
	
	private MenuSessionService menuSessionService;
	private PeopleService peopleService;
	
	private String username;
	private String password;
	private String token;
	private String menuId;
	private String nextUrl;
	private String copyrigthYear;
	
	private FacesUtil facesUtil;
	
	private List<MenuSession> menuList;

	private SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	
	@PostConstruct
	public void init() {
		token = facesUtil.retrieveRequestParam("token");
		menuId = facesUtil.retrieveRequestParam("menuId");
		
		String yearBuild = "2020";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		
		if (yearBuild.equals(yearInString)) {
			copyrigthYear = yearBuild;
		} else {
			copyrigthYear = yearBuild + " - " + yearInString;
		}
		
		facesUtil.setSessionAttribute(Constants.SESSION_LANGUAGE, "ID");
	}
	
	public Boolean isValidate() {
		Boolean flag = true;
		
		if (StringUtils.isEmpty(username)) {
			facesUtil.addErrMessage(facesUtil.retrieveRequestParam("textEmail") + " "
					+ facesUtil.retrieveRequestParam("validateRequired"));
			flag = false;
		}
		if (StringUtils.isEmpty(password)) {
			facesUtil.addErrMessage(facesUtil.retrieveRequestParam("textPassword") + " "
					+ facesUtil.retrieveRequestParam("validateRequired"));
			flag = false;
		}
		
		return flag;
	}
	
	public String doHandleLogin() {
		logger.debug("loginBean doHandleLogin");
		String language = facesUtil.retrieveRequestParam("LANGUAGE");
		String result = null;
		ConfigUtil cu = ConfigUtil.getInstance();
		
		try {
			
			if (isValidate()) {
				String message = doAuth(cu, username);
				
				String passMd5 = MD5Utils.b64_md5(password);
				passMd5 = passMd5.substring(0, passMd5.length() - 2);
				
				People people = peopleService.getUserLogin(username, passMd5 + "==");
				
				if (people == null) {
					facesUtil.addErrMessage(facesUtil.retrieveRequestParam("textLoginFail"));
					return result;
				} else {
					if (message != null && message.equals(Constants.MSG_LOGIN_SUCCESSFULL)) {
						
						logger.debug("login successfull");
						
						HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
						HttpSession session = request.getSession(false);
						
						if (session != null && !session.isNew()) {
							session.invalidate();
						}
						
						session = request.getSession(true);
						
						session.setAttribute(Constants.SESSION_EMPLOYEE, people);
						session.setAttribute(Constants.SESSION_USERNAME, username);
						session.setAttribute(Constants.SESSION_LANGUAGE, language);
						
						menuList = getMenuSessionService().getListMenuAllowed(username);
						session.setAttribute(Constants.SESSION_ALLOWED_MENU, menuList);
						
						if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(menuId)) {
							result = "/pages/dashboard/dashboard.faces";
						} else {
							result = "/pages/dashboard/dashboard.faces";
						}
						
						String baseContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
						FacesContext.getCurrentInstance().getExternalContext().redirect(baseContextPath + result);
						
						return null;
					} else {
						FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error" + message, result);
						FacesContext.getCurrentInstance().addMessage(null, errorMessage);
						logger.error(message);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error : " + e.getMessage(), e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}

	private String doAuth(ConfigUtil cu, String username) throws Exception {
		String message = null;
		if (cu.isAppTesting() && StringUtils.isNotBlank(username)) {
			message = Constants.MSG_LOGIN_SUCCESSFULL;
		}
		
		return message;
	}
	
}

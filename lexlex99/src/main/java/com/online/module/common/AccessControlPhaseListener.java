package com.online.module.common;

import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.online.module.common.bean.AuthBean;
import com.online.module.common.constant.Constants;
import com.online.module.menuSession.model.MenuSession;

public class AccessControlPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -2417883336443290415L;
	private static final Logger logger = Logger.getLogger(AccessControlPhaseListener.class);

	@SuppressWarnings("unchecked")
	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext fc = event.getFacesContext();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		HttpServletRequest hreq = (HttpServletRequest) fc.getExternalContext().getRequest();
		
		String viewId = hreq.getRequestURI();
		String originalUrl = getFullURL(hreq);
		String baseContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		
		AuthBean authBean = new AuthBean();
		
		if (!sessionLoginIsExist(session)) {
			if (authBean.checkExceptionPage(viewId) || authBean.checkErrorPage(viewId)) {
				
			} else {
				logger.error("Session is not available or user not logged in");
				String[] urlWithParam = originalUrl.split("\\?");
				
				if (urlWithParam != null && urlWithParam.length > 1 && urlWithParam[1].startsWith("token")) {
					try {
						fc.getExternalContext().redirect(baseContextPath + "/pages/login/login.faces?"+urlWithParam[1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						fc.getExternalContext().redirect(baseContextPath + "/pages/login/login.faces");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				return;
			}
		} else {
			String language = (String) session.getAttribute(Constants.SESSION_LANGUAGE);
			if (language != null && language.toUpperCase().contains("ENG")) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
			} else {
				Locale locale = new Locale("in", "ID");
				FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
			}
			
			List<MenuSession> menuSessions = (List<MenuSession>) session.getAttribute(Constants.SESSION_ALLOWED_MENU);
			boolean isValid = authBean.validateAccessRight(viewId, menuSessions);
			if (!isValid) {
//				if (originalUrl.contains(".faces?token=")) 
			}
			
			if (!isValid) {
				logger.debug("User Logged in but trying to access unauthorized page");
				for (MenuSession menuSession : menuSessions) {
					if (menuSession.getMenuAction() != null) {
						String url = menuSession.getMenuAction();
						try {
							fc.getExternalContext().redirect(url);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	public static String getFullURL(HttpServletRequest request) {
		StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
		String queryString = request.getQueryString();
		
		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append("?").append(queryString).toString();
		}
	}
	
	private boolean sessionLoginIsExist(HttpSession session) {
		if (session != null && (session.getAttribute(Constants.SESSION_EMPLOYEE) != null)) {
			return true;
		} else {
			return false;
		}
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

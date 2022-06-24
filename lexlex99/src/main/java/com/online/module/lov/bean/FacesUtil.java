package com.online.module.lov.bean;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.online.module.common.constant.Constants;

public class FacesUtil implements Serializable{

	private static final long serialVersionUID = -157236669783215694L;

	public static enum FacesScope {
		APPLICATION_SCOPE, FLASH_SCOPE, REQUEST_SCOPE, SESSION_SCOPE, VIEW_SCOPE
	}
	
	public static final String LEXLEX99_CONFIG_FILE = "/WEB-INF/lexlex99-config.xml";
	
	public String retrieveRequestParam(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
	}
	
	public UIComponent retrieveDataTable(String cliendId, String formName, String tableName) {
		return FacesContext.getCurrentInstance().getViewRoot().findComponent(cliendId).findComponent(formName).findComponent(tableName);
	}
	
	public Locale retrieveDefaultLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}
	
	public Locale retrieveDefaultLocale(String key) {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}
	
	public String retrieveContextPath() {
		return retrieveActiveServletRequest().getContextPath();
	}
	
	public HttpServletRequest retrieveActiveServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public String retrieveMessage(String key) {
		System.out.println("locale " + FacesContext.getCurrentInstance().getViewRoot().getLocale());
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		return bundle.getString(key);
	}
	
	public String retrieveUserLogin() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		
		if (session != null && (session.getAttribute(Constants.SESSION_USERNAME) != null)) {
			return (String) session.getAttribute(Constants.SESSION_USERNAME);
		} else {
			return null;
		}
	}
	
	public void addFacesMsg(FacesMessage.Severity severity, String forComp, String msg, String detail) {
		FacesMessage message = new FacesMessage(severity, msg, detail);
		FacesContext.getCurrentInstance().addMessage(forComp, message);
	}
	
	public void redirect(final String url) throws Exception {
		String baseContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		FacesContext.getCurrentInstance().getExternalContext().redirect(baseContextPath + url);
	}
	
	public Object getSessionAttribute(String attributeName) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if (session != null) {
			return session.getAttribute(attributeName);
		} else {
			return null;
		}
	}
	
	public void setSessionAttribute(String attributeName, Object attributeValue) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if (session != null) {
			session.removeAttribute(attributeName);
		}
	}
	
	public void removeAllSessionAttribute() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
	}
	
	public void removeAllManagedBeans(final FacesScope facesScope) {
		try {
			Set<String> keySet = null;
			
			switch (facesScope) {
			case FLASH_SCOPE:
				keySet = FacesContext.getCurrentInstance().getExternalContext().getFlash().keySet();
				break;
			case REQUEST_SCOPE:
				keySet = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().keySet();
				break;
			case VIEW_SCOPE:
				keySet = FacesContext.getCurrentInstance().getViewRoot().getViewMap().keySet();
				break;
			case SESSION_SCOPE:
				keySet = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().keySet();
				break;
			case APPLICATION_SCOPE:
				keySet = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().keySet();
				break;

			default:
				keySet = null;
				break;
			}
			
			if (keySet != null) {
				for (final String key : keySet) {
					if (!key.startsWith("SESSION")) {
						this.removeManagedBean(key, facesScope);
					}
				}
			}
			
		} catch (Exception e) {
			
		}
	}
	
	public boolean removeManagedBean(final String managedBeanName, final FacesScope facesScope) {
		boolean result = false;
		
		try {
			switch (facesScope) {
			case FLASH_SCOPE:
				if (FacesContext.getCurrentInstance().getExternalContext().getFlash().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			case REQUEST_SCOPE:
				if (FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			case VIEW_SCOPE:
				if (FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			case SESSION_SCOPE:
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			case APPLICATION_SCOPE:
				if (FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			default:
				result = false;
				break;
			}
		} catch (Exception e) {
			
		}
		
		return result;
	}
	
	public boolean removeManagedBean(final String managedBeanName) {
		boolean result = false;
		
		for (final FacesScope fs : FacesScope.values()) {
			if (this.removeManagedBean(managedBeanName, fs)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public void addErrMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void addWarnMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void addSuccMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}
	
}

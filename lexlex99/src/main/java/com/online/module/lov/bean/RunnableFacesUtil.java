package com.online.module.lov.bean;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class RunnableFacesUtil extends FacesUtil {

	private static final long serialVersionUID = -4589309516886917058L;
	private Locale defaultLocationBean;
	private static String BUNDLE_PROPERTIES_LOCATION = "com.online.resources.ApplicationResources";
	
	public RunnableFacesUtil(Locale defaultLocationBean) {
		this.defaultLocationBean = defaultLocationBean;
	}

	public String retrieveMessage(final String key) {
		return ResourceBundle.getBundle(BUNDLE_PROPERTIES_LOCATION, this.defaultLocationBean).getString(key);
	}
	
	public String retrieveLocaleMessage(String enValue, String inValue) {
		if (defaultLocationBean != null && defaultLocationBean.equals(Locale.ENGLISH)) {
			return StringUtils.isBlank(enValue) ? StringUtils.EMPTY : enValue;
		}
		
		return StringUtils.isBlank(inValue) ? StringUtils.EMPTY : inValue;
	}
	
	public Locale getDefaultLocationBean() {
		return defaultLocationBean;
	}

	public void setDefaultLocationBean(Locale defaultLocationBean) {
		this.defaultLocationBean = defaultLocationBean;
	}

	public static String getBUNDLE_PROPERTIES_LOCATION() {
		return BUNDLE_PROPERTIES_LOCATION;
	}

	public static void setBUNDLE_PROPERTIES_LOCATION(String bUNDLE_PROPERTIES_LOCATION) {
		BUNDLE_PROPERTIES_LOCATION = bUNDLE_PROPERTIES_LOCATION;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

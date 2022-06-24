package com.online.module.master.sistem.bean;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.online.module.common.constant.Constants;
import com.online.module.common.paging.DBLazyDataModel;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.lov.bean.FacesUtil;
import com.online.module.master.sistem.model.System;
import com.online.module.master.sistem.service.SystemService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemBean implements Serializable {

	private static final long serialVersionUID = 8571757095332981536L;
	private static final Logger logger = Logger.getLogger(SystemBean.class);
	
	private SystemService systemService;
	
	private String searchSystemCode;
	private String searchStatus;
	
	private DBLazyDataModel<System> tableModel;
	
	private FacesUtil facesUtil;
	
	@PostConstruct
	public void init() {
		tableModel = new DBLazyDataModel<System>(systemService, 10);	
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(
				new DefaultSearchObject(Constants.WHERE_SISTEM_CODE, searchSystemCode),
				new DefaultSearchObject(Constants.WHERE_STATUS, searchStatus)));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchSystemCode = null;
		searchStatus = null;
		
		search(actionEvent);
	}
	
	public void delete(Long systemId) {
		
	}

	public void messagesStatus(Boolean isStatus, Long systemId) {
		systemService.updateStatus(systemId, isStatus, facesUtil.retrieveUserLogin());
		String messages = isStatus ? "Aktif" : "Non-Aktif";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
	}
	
	public static Logger getLogger() {
		return logger;
	}

}

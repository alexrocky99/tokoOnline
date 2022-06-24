package com.online.module.master.responsibility.bean;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.paging.DBLazyDataModel;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.responsibility.model.Responsibility;
import com.online.module.master.responsibility.service.ResponsibilityService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsibilityBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 1302814689918634117L;
	private static final Logger logger = Logger.getLogger(ResponsibilityBean.class);
	private static final String NAVIGATE_EDIT = Constants.NAVIGATE_RESPONSIBILITY_EDIT;
	
	private ResponsibilityService responsibilityService;
	
	private String searchResponsibility;
	
	private DBLazyDataModel<Responsibility> tableModel;
	
	@PostConstruct
	public void init() {
		super.init();
		tableModel = new DBLazyDataModel<Responsibility>(responsibilityService, paging);
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(
				new DefaultSearchObject(Constants.WHERE_NAME, searchResponsibility)));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchResponsibility = null;
		
		search(actionEvent);
	}
	
	public void delete(Long deleteId) {
		try {
			Responsibility responsibility = responsibilityService.findById(deleteId);
			EntityUtil.setUpdateDeleteWithFlag(responsibility, facesUtil.retrieveUserLogin());
			responsibilityService.update(responsibility);
			
			facesUtil.addSuccMessage(facesUtil.retrieveMessage("deleteSuccess"));
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation failed : " + e.getMessage(), "");
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static String getNavigateEdit() {
		return NAVIGATE_EDIT;
	}
	
}

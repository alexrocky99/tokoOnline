package com.online.module.master.parameter.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.online.module.common.constant.Constants;
import com.online.module.common.paging.DBLazyDataModel;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.common.util.EntityUtil;
import com.online.module.lov.bean.FacesUtil;
import com.online.module.master.parameter.model.Parameter;
import com.online.module.master.parameter.model.ParameterDetail;
import com.online.module.master.parameter.service.ParameterDetailService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterDetailBean implements Serializable {

	private static final long serialVersionUID = 4706250447150895712L;
	private static final Logger logger = Logger.getLogger(ParameterDetailBean.class);
	private static final String NAVIGATE_EDIT = Constants.NAVIGATE_MENU_PARAMETER_EDIT;

	private ParameterDetailService parameterDetailService;
	
	private String searchDetailCode;
	private String searchCode;
	private String searchValue;
	
	private List<SelectItem> codeList;
	
	private DBLazyDataModel<ParameterDetail> tableModel;
	
	private FacesUtil facesUtil;
	
	@PostConstruct
	public void init() {
		
		initSelectCode();
		
		tableModel = new DBLazyDataModel<ParameterDetail>(parameterDetailService, Constants.DEFAULT_PAGING_NUMBER);
	}
	
	private void initSelectCode() {
		List<Parameter> paramHeaderList = parameterDetailService.getParameterList();
		codeList = new ArrayList<SelectItem>();
		
		try {
			if (paramHeaderList != null) {
				for (int i = 0; i < paramHeaderList.size(); i++) {
					Parameter data = paramHeaderList.get(i);
					SelectItem si = new SelectItem();
					
					si.setLabel(data.getValue());
					si.setValue(data.getCode());
					
					codeList.add(si);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(
				new DefaultSearchObject(Constants.WHERE_PARAM_CODE, searchCode),
				new DefaultSearchObject(Constants.WHERE_PARAM_DETAIL_CODE, searchDetailCode),
				new DefaultSearchObject(Constants.WHERE_VALUE, searchValue)));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchCode = null;
		searchDetailCode = null;
		searchValue = null;
		
		search(actionEvent);
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void delete(Long deleteId) {
		try {
			ParameterDetail parameterDetail = parameterDetailService.findById(deleteId);
			EntityUtil.setUpdateDeleteWithFlag(parameterDetail, facesUtil.retrieveUserLogin());
			parameterDetailService.update(parameterDetail);
			
			facesUtil.addSuccMessage(facesUtil.retrieveMessage("deleteSuccess"));
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation failed : " + e.getMessage(), "");
		}
	}
	
	public void messagesStatus(Boolean isStatus, Long parameterDetailId) {
		parameterDetailService.changeStatus(isStatus, parameterDetailId, facesUtil.retrieveUserLogin());
		String messages = isStatus ? "Aktif" : "Non-Aktif";
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messages));
	}

	public static String getNavigateEdit() {
		return NAVIGATE_EDIT;
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

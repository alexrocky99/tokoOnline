package com.online.module.master.parameter.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.online.module.common.constant.Constants;
import com.online.module.common.util.EntityUtil;
import com.online.module.lov.bean.FacesUtil;
import com.online.module.master.parameter.model.Parameter;
import com.online.module.master.parameter.model.ParameterDetail;
import com.online.module.master.parameter.service.ParameterDetailService;
import com.online.module.master.parameter.service.ParameterService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterDetailEditBean implements Serializable {

	private static final long serialVersionUID = -7343806045443638634L;
	private static final Logger logger = Logger.getLogger(ParameterDetailEditBean.class);
	
	private ParameterDetailService parameterDetailService;
	private ParameterService parameterService;
	
	private ParameterDetail parameterDetail;
	
	private String actionMode;
	private String editId;
	
	private List<SelectItem> codeList;
	
	private FacesUtil facesUtil;

	@PostConstruct
	public void init() {
		initSelectCode();
		
		checkNewOrEdit();
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
					si.setValue(data.getParameterId());
					
					codeList.add(si);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	private void checkNewOrEdit() {
		editId = facesUtil.retrieveRequestParam("editId");
		
		if (StringUtils.isEmpty(editId)) {
			handleNew();
		} else {
			handleEdit(editId);
		}
	}
	
	private void handleNew() {
		actionMode = Constants.ACTION_MODE_ADD;
		
		parameterDetail = new ParameterDetail();
		parameterDetail.setParameter(new Parameter());
	}
	
	private void handleEdit(String editId) {
		actionMode = Constants.ACTION_MODE_EDIT;
		Long parseId = Long.parseLong(editId);
		
		parameterDetail = parameterDetailService.findById(parseId);
	}
	
	private Boolean isValidate() {
		boolean flag = true;
		
		if (StringUtils.isEmpty(parameterDetail.getDetailCode())) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formParameterDetailDetailCode") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		if (parameterDetail.getParameter().getParameterId() == null) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formParameterDetailCode") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		if (StringUtils.isEmpty(parameterDetail.getDetailValue())) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formParameterDetailValue") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		return flag;
	}
	
	public void save() {
		try {
			if (Boolean.TRUE.equals(isValidate())) {
				Parameter parameter = parameterService.findById(parameterDetail.getParameter().getParameterId());
				
				parameterDetail.setCode(parameter.getCode());
				parameterDetail.setStatus(Constants.PARAM_DET_CODE_STATUS_ACTIVE);
				
				if (parameterDetail.getParameterDetailId() != null) {
					EntityUtil.setUpdateInfo(parameterDetail, facesUtil.retrieveUserLogin());
					parameterDetailService.update(parameterDetail);
				} else {
					EntityUtil.setCreationInfo(parameterDetail, facesUtil.retrieveUserLogin());
					parameterDetailService.save(parameterDetail);
				}
				
				facesUtil.redirect("/pages/master/parameter/" + Constants.NAVIGATE_MENU_PARAMETER);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage());
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation Failed : " + e.getMessage(), "");
		}
	}
	
	public void cancel() {
		try {
			facesUtil.redirect("/pages/master/parameter/" + Constants.NAVIGATE_MENU_PARAMETER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

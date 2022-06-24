package com.online.module.master.people.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.paging.DBLazyDataModel;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.parameter.model.ParameterDetail;
import com.online.module.master.people.model.People;
import com.online.module.master.people.service.PeopleService;
import com.online.module.master.responsibility.model.Responsibility;
import com.online.module.master.responsibility.service.ResponsibilityService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeopleBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -5276086856012718700L;
	private static final Logger logger = Logger.getLogger(PeopleBean.class);
	private static final String NAVIGATE_EDIT = Constants.NAVIGATE_PEOPLE_EDIT;
	
	private PeopleService peopleService;
	private ResponsibilityService responsibilityService;
	
	private String searchName;
	private String searchEmail;
	private String searchGender;
	private String searchPeopleNumber;
	private Long searchResponsibility;

	private DBLazyDataModel<People> tableModel;
	
	private List<SelectItem> genderList;
	private List<SelectItem> responsibilityList;
	
	@PostConstruct
	public void init() {
		super.init();
		
		initGenderList();
		initResponsibilityList();
		
		tableModel = new DBLazyDataModel<People>(peopleService, paging);
	}
	
	private void initGenderList() {
		genderList = new ArrayList<SelectItem>();
		
		try {
			List<ParameterDetail> getGender = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_GENDER, true);
			
			for (int i = 0; i < getGender.size(); i++) {
				ParameterDetail data = getGender.get(i);
				SelectItem si = new SelectItem();
				
				si.setLabel(data.getDetailValue());
				si.setValue(data.getDetailCode());
				
				genderList.add(si);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initResponsibilityList() {
		responsibilityList = new ArrayList<SelectItem>();
		
		try {
			List<Responsibility> getResponsibility = responsibilityService.getAllResponsibility();
			
			for (int i = 0; i < getResponsibility.size(); i++) {
				Responsibility data = getResponsibility.get(i);
				SelectItem si = new SelectItem();
				
				si.setValue(data.getResponsibilityId());
				si.setLabel(data.getName());
				
				responsibilityList.add(si);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(
				new DefaultSearchObject(Constants.WHERE_NAME, searchName),
				new DefaultSearchObject(Constants.WHERE_EMAIL, searchEmail),
				new DefaultSearchObject(Constants.WHERE_GENDER, searchGender),
				new DefaultSearchObject(Constants.WHERE_PEOPLE_NUMBER, searchPeopleNumber),
				new DefaultSearchObject(Constants.WHERE_RESPONSIBILITY, searchResponsibility)));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchName = null;
		searchEmail = null;
		searchGender = null;
		searchPeopleNumber = null;
		searchResponsibility = null;
		
		search(actionEvent);
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void delete(Long peopleId) {
		try {
			People people = peopleService.findById(peopleId);
			EntityUtil.setUpdateDeleteWithFlag(people, facesUtil.retrieveUserLogin());
			peopleService.update(people);
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

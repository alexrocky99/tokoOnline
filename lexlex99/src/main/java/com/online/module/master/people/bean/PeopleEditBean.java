package com.online.module.master.people.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
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
public class PeopleEditBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 6888992813535043597L;
	private static final Logger logger = Logger.getLogger(PeopleEditBean.class);
	
	private PeopleService peopleService;
	private ResponsibilityService responsibilityService;
	
	private People people;
	
	private String actionMode;
	private String editId;
	
	private List<SelectItem> genderList;
	private List<SelectItem> responsibilityList;
	
	@PostConstruct
	public void init() {
		super.init();
		
		initGenderList();
		initResponsibilityList();
		
		checkNewOrEdit();
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
		people = new People();
	}

	private void handleEdit(String editId) {
		actionMode = Constants.ACTION_MODE_EDIT;
		Long parseId = Long.parseLong(editId);
		
		people = peopleService.findById(parseId);
	}
	
	private Boolean isValidate() {
		boolean flag = true;
		
		return flag;
	}
	
	public void save() {
		try {
			if (isValidate()) {
				if (people.getPeopleId() != null) {
					EntityUtil.setUpdateInfo(people, facesUtil.retrieveUserLogin());
					peopleService.update(people);
				} else {
					createPeopleNumber(people);
					
					EntityUtil.setCreationInfo(people, facesUtil.retrieveUserLogin());
					peopleService.save(people);
				}
				facesUtil.redirect("/pages/master/people/" + Constants.NAVIGATE_PEOPLE);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage());
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation Failed : " + e.getMessage(), "");
		}
	}
	
	public void cancel() {
		try {
			facesUtil.redirect("/pages/master/people/" + Constants.NAVIGATE_PEOPLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createPeopleNumber(People people) {
		String yearStr = Constants.FORMAT_2_DIGIT_YEAR.format(Calendar.getInstance().getTime());
		int nextval = peopleService.getNexVal().intValue();
		
		if (nextval < 1 || nextval > 10) {
			people.setPeopleNumber(yearStr+"000000"+nextval);
		} else if (nextval < 10 || nextval > 100) {
			people.setPeopleNumber(yearStr+"00000"+nextval);
		} else if (nextval < 1000 || nextval > 10000) {
			people.setPeopleNumber(yearStr+"0000"+nextval);
		} else if (nextval < 10000 || nextval > 100000) {
			people.setPeopleNumber(yearStr+"000"+nextval);
		} else if (nextval < 100000 || nextval > 1000000) {
			people.setPeopleNumber(yearStr+"00"+nextval);
		} else if (nextval < 1000000 || nextval > 10000000) {
			people.setPeopleNumber(yearStr+"0"+nextval);
		} else if (nextval < 10000000 || nextval > 100000000) {
			people.setPeopleNumber(yearStr+nextval);
		}
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

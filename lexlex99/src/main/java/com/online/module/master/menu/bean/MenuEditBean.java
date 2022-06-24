package com.online.module.master.menu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.springframework.util.StringUtils;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.menu.model.Menu;
import com.online.module.master.menu.service.MenuService;
import com.online.module.master.parameter.model.ParameterDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuEditBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 1292659030247421054L;
	private static final Logger logger = Logger.getLogger(MenuEditBean.class);
	
	private MenuService menuService;
	
	private Menu menu;
	
	private String actionMode;
	private String editId;

	private List<SelectItem> menuTypeList;
	private List<SelectItem> menuParentList;
	
	@PostConstruct
	public void init() {
		super.init();
		
		initMenuTypeList();
		initMenuParentList();
		
		checkNewOrEdit();
	}
	
	private void initMenuTypeList() {
		menuTypeList = new ArrayList<SelectItem>();
		
		List<ParameterDetail> getMenuType = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_MENU_TYPE, true);
		
		for (int i = 0; i < getMenuType.size(); i++) {
			ParameterDetail data = getMenuType.get(i);
			SelectItem si = new SelectItem();
			
			si.setLabel(data.getDetailValue());
			si.setValue(data.getDetailCode());
			
			menuTypeList.add(si);
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	private void initMenuParentList() {
		menuParentList = new ArrayList<SelectItem>();
		
		List<Menu> getAllParentMenu = menuService.getAllParentMenu();
		
		for (int i = 0; i < getAllParentMenu.size(); i++) {
			Menu menu = getAllParentMenu.get(i);
			SelectItem si = new SelectItem();
			
			si.setLabel(menu.getName());
			si.setValue(menu.getMenuId());
			
			menuParentList.add(si);
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
		menu = new Menu();
	}
	
	private void handleEdit(String editId) {
		actionMode = Constants.ACTION_MODE_EDIT;
		Long parseMenuId = Long.parseLong(editId);
		
		menu = menuService.findById(parseMenuId);
	}
	
	private Boolean isValidate() {
		boolean flag = true;
		
		if (StringUtils.isEmpty(menu.getName())) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formMenuName") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		if (menu.getMenuOrder() == null) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formMenuOrder") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		return flag;
	}
	
	public void save() {
		try {
			if (isValidate()) {
				
				if (menu.getMenuId() != null) {
					EntityUtil.setUpdateInfo(menu, facesUtil.retrieveUserLogin());
					menuService.edit(menu);
				} else {
					EntityUtil.setCreationInfo(menu, facesUtil.retrieveUserLogin());
					menuService.save(menu);
				}
				facesUtil.redirect("/pages/master/menu/" + Constants.NAVIGATE_MENU);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage());
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation Failed : " + e.getMessage(), "");
		}
	}
	
	public void cancel() {
		try {
			facesUtil.redirect("/pages/master/menu/" + Constants.NAVIGATE_MENU);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

package com.online.module.master.menu.bean;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.paging.DBLazyDataModel;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.master.menu.model.Menu;
import com.online.module.master.menu.service.MenuService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 8147386127062622237L;
	private static final Logger logger = Logger.getLogger(MenuBean.class);
	private static final String NAVIGATE_EDIT = Constants.NAVIGATE_MENU_EDIT;

	private MenuService menuService;
	
	private String searchMenuName;
	
	private DBLazyDataModel<Menu> tableModel;
	
	@PostConstruct
	public void init() {
		super.init();
		
		tableModel = new DBLazyDataModel<Menu>(menuService, paging);
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(
				new DefaultSearchObject(Constants.WHERE_NAME, searchMenuName)));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchMenuName = null;
		
		search(actionEvent);
	}

	public static String getNavigateEdit() {
		return NAVIGATE_EDIT;
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

package com.online.module.common.bean;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlInputHidden;

import org.apache.log4j.Logger;

import com.online.module.common.dao.TableOfValueDao;
import com.online.module.common.vo.TableOfValuesVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableOfValueBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -8586778922358811198L;
	private static final Logger logger = Logger.getLogger(TableOfValueBean.class);
	
	private TableOfValueDao tableOfValueDao;
	
	private String searchStr;
	private String column1;
	private String column2;
	private String column3;
	private String column4;
	private String column5;
	private String column6;
	private String column7;
	private String column8;
	
	private boolean showCol1;
	private boolean showCol2;
	private boolean showCol3;
	private boolean showCol4;
	private boolean showCol5;
	private boolean showCol6;
	private boolean showCol7;
	private boolean showCol8;
	
	private List<TableOfValuesVO> listValues;
	
	private TableOfValuesVO selectedTableOfValue;
	
	public HtmlInputHidden componentToUpdate;
	public HtmlInputHidden hdnQuery;
	public HtmlInputHidden hdnColumns;
	public HtmlInputHidden hdnParameters;
	
	@PostConstruct
	public void init() {
		super.init();
		
		listValues = new ArrayList<TableOfValuesVO>();
		selectedTableOfValue = new TableOfValuesVO();
		searchStr = "";
		
		componentToUpdate = new HtmlInputHidden();
		hdnQuery = new HtmlInputHidden();
		hdnColumns = new HtmlInputHidden();
		hdnParameters = new HtmlInputHidden();
		
		
	}
	
	@SuppressWarnings("unused")
	public void search(ActionEvent actionEvent) {
		String query = hdnQuery.getValue().toString();
		listValues.clear();
		
		try {
			if (hdnParameters.getValue() != null && !hdnParameters.getValue().toString().equals("")) {
				String parameters = hdnParameters.getValue().toString();
				String[] split = parameters.split(";");
				
				for (int i = 0; i < split.length; i++) {
					split[i] = split[i].trim();
				}
			}
		} catch (Exception e) {
		}
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

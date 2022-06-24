package com.online.module.transaction.resi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.online.module.transaction.resi.model.Resi;
import com.online.module.transaction.resi.service.ResiService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResiBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 8245728521941333019L;
	private static final Logger logger = Logger.getLogger(ResiBean.class);
	private static final String NAVIGATE_EDIT = Constants.NAVIGATE_RESI_EDIT;
	
	private ResiService resiService;
	
	private Date searchStartDate;
	private Date searchEndDate;
	private String searchPaymentStatus;
	
	private List<SelectItem> paymentStatusList;
	
	private DBLazyDataModel<Resi> tableModel;
	
	@PostConstruct
	public void init() {
		super.init();
		
		selectPaymentStatus();
		
		tableModel = new DBLazyDataModel<>(resiService, paging);
	}
	
	private void selectPaymentStatus() {
		paymentStatusList = new ArrayList<>();
		
		try {
			List<ParameterDetail> getPaymentStatus = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_PAYMENT_STATUS, true);
			for (ParameterDetail parameterDetail : getPaymentStatus) {
				SelectItem si = new SelectItem();
				
				si.setValue(parameterDetail.getDetailCode());
				si.setLabel(parameterDetail.getDetailValue());
				
				paymentStatusList.add(si);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(
				new DefaultSearchObject(Constants.WHERE_STATUS, searchPaymentStatus),
				new DefaultSearchObject(Constants.WHERE_START_DATE, searchStartDate != null ? Constants.FORMAT_DATE_YYYYMMdd.format(searchStartDate) : ""),
				new DefaultSearchObject(Constants.WHERE_END_DATE, searchEndDate != null ? Constants.FORMAT_DATE_YYYYMMdd.format(searchEndDate) : "")));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchEndDate = null;
		searchStartDate = null;
		searchPaymentStatus = null;
		
		search(actionEvent);
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void delete(Long deleteId) {
		try {
			Resi resi = resiService.findById(deleteId);
			EntityUtil.setUpdateDeleteWithFlag(resi, facesUtil.retrieveUserLogin());
			resiService.update(resi);
			
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

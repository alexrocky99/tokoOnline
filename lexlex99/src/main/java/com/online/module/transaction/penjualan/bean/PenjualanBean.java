package com.online.module.transaction.penjualan.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.paging.DBLazyDataModel;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.common.util.EntityUtil;
import com.online.module.transaction.penjualan.model.Penjualan;
import com.online.module.transaction.penjualan.service.PenjualanService;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PenjualanBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 1989361768322986L;
	private static final Logger logger = Logger.getLogger(PenjualanBean.class);
	private static final String NAVIGATE_EDIT = Constants.NAVIGATE_PENJUALAN_EDIT;
	
	private PenjualanService penjualanService;
	
	private String searchInvoice;
	private String searchCustomerName;
	private Date searchStartDate;
	private Date searchEndDate;
	private String searchReceiptNumber;
	
	private DBLazyDataModel<Penjualan> tableModel;
	
	@PostConstruct
	public void init() {
		super.init();
		
		tableModel = new DBLazyDataModel<Penjualan>(penjualanService, paging);
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(
				new DefaultSearchObject(Constants.WHERE_NAME, searchCustomerName),
				new DefaultSearchObject(Constants.WHERE_RECEIPT_NUMBER, searchReceiptNumber),
				new DefaultSearchObject(Constants.WHERE_INVOICE, searchInvoice),
				new DefaultSearchObject(Constants.WHERE_START_DATE, searchStartDate != null ? Constants.FORMAT_DATE_YYYYMMdd.format(searchStartDate) : ""),
				new DefaultSearchObject(Constants.WHERE_END_DATE, searchEndDate != null ? Constants.FORMAT_DATE_YYYYMMdd.format(searchEndDate) : "")));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchCustomerName = null;
		searchEndDate = null;
		searchInvoice = null;
		searchReceiptNumber = null;
		searchStartDate = null;
		
		search(actionEvent);
	}
	
	public void delete(Long penjualanId) {
		try {
			Penjualan penjualan = penjualanService.findById(penjualanId);
			EntityUtil.setUpdateDeleteWithFlag(penjualan, facesUtil.retrieveUserLogin());
			penjualanService.update(penjualan);
			
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

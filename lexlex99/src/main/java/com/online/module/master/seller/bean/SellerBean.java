package com.online.module.master.seller.bean;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.paging.DBLazyDataModel;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.seller.model.Seller;
import com.online.module.master.seller.service.SellerService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -1147061407616760145L;
	private static final String NAVIGATE_EDIT = Constants.NAVIGATE_SELLER_EDIT;
	
	private SellerService sellerService;
	
	private String searchSellerName;
	
	private DBLazyDataModel<Seller> tableModel;
	
	@PostConstruct
	public void init() {
		super.init();
		
		tableModel = new DBLazyDataModel<>(sellerService, paging);
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(new DefaultSearchObject(Constants.WHERE_NAME, searchSellerName)));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchSellerName = null;
		
		search(actionEvent);
	}
	
	public void delete(Long deleteId) {
		try {
			Seller seller = sellerService.findById(deleteId);
			EntityUtil.setUpdateDeleteWithFlag(seller, facesUtil.retrieveUserLogin());
			sellerService.update(seller);
			
			facesUtil.addSuccMessage(facesUtil.retrieveMessage("deleteSuccess"));
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation failed : " + e.getMessage(), "");
		}
	}

	public static String getNavigateEdit() {
		return NAVIGATE_EDIT;
	}

}

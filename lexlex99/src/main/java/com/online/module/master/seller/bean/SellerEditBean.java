package com.online.module.master.seller.bean;

import lombok.Setter;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.util.EntityUtil;
import com.online.module.lov.bean.FacesUtil;
import com.online.module.master.seller.model.Seller;
import com.online.module.master.seller.service.SellerService;

import lombok.Getter;

@Getter
@Setter
public class SellerEditBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -5423131897473215160L;
	private static final Logger logger = Logger.getLogger(SellerEditBean.class);
	
	private SellerService sellerService;
	
	private Seller seller;
	
	private String actionMode;
	private String editId;
	
	private FacesUtil facesUtil;
	
	@PostConstruct
	public void init() {
		super.init();
		
		checkNewOrEdit();
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
		
		seller = new Seller();
	}
	
	private void handleEdit(String editId) {
		actionMode = Constants.ACTION_MODE_EDIT;
		Long parseId = Long.parseLong(editId);
		
		seller = sellerService.findById(parseId);
		
	}
	
	private Boolean isValidate() {
		boolean flag = true;
		
		if (StringUtils.isEmpty(seller.getSellerName())) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formSellerSellerName") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		} else {
			if (StringUtils.equals(actionMode, Constants.ACTION_MODE_ADD)) {
				if (Boolean.TRUE.equals(sellerService.checkDataSellerById(null, seller.getSellerName()))) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formSellerSellerAlreadyInTable"));
					flag = false;
				}
			} else if (StringUtils.equals(actionMode, Constants.ACTION_MODE_EDIT)) {
				if (Boolean.TRUE.equals(sellerService.checkDataSellerById(Long.parseLong(editId), seller.getSellerName()))) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formSellerSellerAlreadyInTable"));
					flag = false;
				}
			}
		}
		
		if (seller.getEffectiveStartDate() == null) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formSellerEffStartDate") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		return flag;
	}
	
	public void save() {
		try {
			if (Boolean.TRUE.equals(isValidate())) {
				
				if (seller.getSellerId() != null) {
					EntityUtil.setUpdateInfo(seller, facesUtil.retrieveUserLogin());
					sellerService.update(seller);
				} else {
					EntityUtil.setCreationInfo(seller, facesUtil.retrieveUserLogin());
					sellerService.save(seller);
				}
				
				facesUtil.redirect("/pages/master/seller/" + Constants.NAVIGATE_SELLER);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage());
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation Failed : " + e.getMessage(), "");
		}
	}
	
	public void cancel() {
		try {
			facesUtil.redirect("/pages/master/seller/" + Constants.NAVIGATE_SELLER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

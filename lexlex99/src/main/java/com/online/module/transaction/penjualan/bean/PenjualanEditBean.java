package com.online.module.transaction.penjualan.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.parameter.model.ParameterDetail;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.model.ProductModal;
import com.online.module.master.product.service.ProductModalService;
import com.online.module.master.product.service.ProductService;
import com.online.module.transaction.penjualan.model.Penjualan;
import com.online.module.transaction.penjualan.model.PenjualanDetail;
import com.online.module.transaction.penjualan.service.PenjualanService;
import com.online.module.transaction.penjualan.tableModel.PenjualanDetailTableModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PenjualanEditBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 8488518806887765998L;
	private static final Logger logger = Logger.getLogger(PenjualanEditBean.class);

	private PenjualanService penjualanService;
	private ProductService productService;
	private ProductModalService productModalService;
	
	private Penjualan penjualan;
	
	private String actionMode;
	private String editId;
	private String searchProductDialog;
	
	private Integer lastSeqOfPenjualanDetail;
	private Integer mandatoryResi;
	private Integer formPenjualan;
	private Integer indexProductDetail;
	
	private PenjualanDetailTableModel<PenjualanDetail> penjualanDetailTableModel;
	
	private PenjualanDetail[] selectedDataPenjualanDetail;
	
	private List<SelectItem> platformList;
	private List<SelectItem> expedisionList;
	private List<SelectItem> typePenjualanList;
	private List<Product> productList;
	private List<PenjualanDetail> removeDataPenjualanDetail;
	
	@PostConstruct
	public void init() {
		super.init();
		
		initComponent();
		
		checkNewOrEdit();
	}
	
	public void initProduct() {
		productList = new ArrayList<Product>();
		searchProductDialog = null;
		productList = productService.getAllProduct();
	}
	
	private void initComponent() {
		removeDataPenjualanDetail = new ArrayList<PenjualanDetail>();
		
		selectPlatformList();
		selectExpedisionList();
		selectTypePenjualan();
	}
	
	private void selectPlatformList() {
		platformList = new ArrayList<SelectItem>();
		
		try {
			List<ParameterDetail> getPlatform = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_PLATFORM, true);
			for (ParameterDetail parameterDetail : getPlatform) {
				SelectItem si = new SelectItem();
				
				si.setValue(parameterDetail.getDetailCode());
				si.setLabel(parameterDetail.getDetailValue());
				
				platformList.add(si);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	private void selectExpedisionList() {
		expedisionList = new ArrayList<SelectItem>();
		
		try {
			List<ParameterDetail> getExpedision = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_EXPEDISI, true);
			for (ParameterDetail parameterDetail : getExpedision) {
				SelectItem si = new SelectItem();
				
				si.setValue(parameterDetail.getDetailCode());
				si.setLabel(parameterDetail.getDetailValue());
				
				expedisionList.add(si);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	private void selectTypePenjualan() {
		typePenjualanList = new ArrayList<SelectItem>();
		
		try {
			List<ParameterDetail> getTypePenjualan = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_TYPE_PENJUALAN, true);
			for (ParameterDetail parameterDetail : getTypePenjualan) {
				SelectItem si = new SelectItem();
				
				si.setValue(parameterDetail.getDetailCode());
				si.setLabel(parameterDetail.getDetailValue());
				
				typePenjualanList.add(si);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void onChangePlatform() {
		expedisionList = new ArrayList<SelectItem>();
		
		if (StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA, penjualan.getPlatform())) {
			List<ParameterDetail> getExpedision = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_TOKOPEDIA_EXPEDISION, true);
			for (ParameterDetail parameterDetail : getExpedision) {
				SelectItem si = new SelectItem();
				
				si.setValue(parameterDetail.getDetailCode());
				si.setLabel(parameterDetail.getDetailValue());
				
				expedisionList.add(si);
			}
		}
		
		if (StringUtils.equals(Constants.PARAM_DET_CODE_SHOPEE, penjualan.getPlatform())) {
			List<ParameterDetail> getExpedision = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_SHOPEE_EXPEDISION, true);
			for (ParameterDetail parameterDetail : getExpedision) {
				SelectItem si = new SelectItem();
				
				si.setValue(parameterDetail.getDetailCode());
				si.setLabel(parameterDetail.getDetailValue());
				
				expedisionList.add(si);
			}
		}
		
		if (StringUtils.equals(Constants.PARAM_DET_CODE_LAZADA, penjualan.getPlatform())) {
			List<ParameterDetail> getExpedision = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_LAZADA_EXPEDISION, true);
			for (ParameterDetail parameterDetail : getExpedision) {
				SelectItem si = new SelectItem();
				
				si.setValue(parameterDetail.getDetailCode());
				si.setLabel(parameterDetail.getDetailValue());
				
				expedisionList.add(si);
			}
		}
		
		if (StringUtils.equals(Constants.PARAM_DET_CODE_JD_ID, penjualan.getPlatform())) {
			List<ParameterDetail> getExpedision = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_JD_ID_EXPEDISION, true);
			for (ParameterDetail parameterDetail : getExpedision) {
				SelectItem si = new SelectItem();
				
				si.setValue(parameterDetail.getDetailCode());
				si.setLabel(parameterDetail.getDetailValue());
				
				expedisionList.add(si);
			}
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void onChangeEkspedisi() {
		mandatoryResi = 0;
		if (StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA, penjualan.getPlatform())) {
			if (StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA_ANTARAJA, penjualan.getExpedision())
					|| StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA_JNE, penjualan.getExpedision())
					|| StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA_J_T, penjualan.getExpedision())
					|| StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA_LION_PARCEL, penjualan.getExpedision())
					|| StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA_SICEPAT, penjualan.getExpedision())) {
				mandatoryResi = 1;
			}
		}
		
		if (StringUtils.equals(Constants.PARAM_DET_CODE_LAZADA, penjualan.getPlatform())) {
			mandatoryResi = 1;
		}
		
		if (StringUtils.equals(Constants.PARAM_DET_CODE_SHOPEE, penjualan.getPlatform())) {
			mandatoryResi = 1;
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void onChangeTypePenjualan() {
		formPenjualan = null;
		
		if (StringUtils.equals(Constants.PARAM_DET_CODE_TRANSACTION_ONLINE, penjualan.getTypePenjualan())) {
			formPenjualan = 1;
		} else {
			formPenjualan = 0;
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void searchDialog(String widgetVar) {
		if (StringUtils.equals(Constants.WIDGET_VAR_PRODUCT_DIALOG, widgetVar)) {
			productList = productService.searchDataProductDialog(Arrays.asList(new DefaultSearchObject(Constants.WHERE_NAME, searchProductDialog)));
		}
		
		PrimeFaces.current().executeScript("initSelect2();");
	}
	
	public void select(Long id, String widgetVar) {
		if (StringUtils.equals(Constants.WIDGET_VAR_PRODUCT_DIALOG, widgetVar)) {
			Product product = productService.findById(id);
			
			if (indexProductDetail == null) {
				indexProductDetail = new Integer(0);
			}
			
			if (actionMode.equals(Constants.ACTION_MODE_ADD)) {
				penjualan.getPenjualanDetailList().get(indexProductDetail).setNamaProduct(product.getProductName());
				penjualan.getPenjualanDetailList().get(indexProductDetail).setProduct(product);
				penjualan.getPenjualanDetailList().get(indexProductDetail).setSellingPrice(product.getPrice().doubleValue());
			} else if (actionMode.equals(Constants.ACTION_MODE_EDIT)) {
				if (penjualan.getPenjualanDetailList().get(indexProductDetail).getProduct() != null
						&& (product.getProductId() != penjualan.getPenjualanDetailList().get(indexProductDetail).getProductTemp().getProductId()) ) {
					penjualan.getPenjualanDetailList().get(indexProductDetail).setChangeData(Constants.CHANGE_DATA);
				}
				penjualan.getPenjualanDetailList().get(indexProductDetail).setNamaProduct(product.getProductName());
				penjualan.getPenjualanDetailList().get(indexProductDetail).setProduct(product);
				penjualan.getPenjualanDetailList().get(indexProductDetail).setSellingPrice(product.getPrice().doubleValue());
			}
			
			penjualanDetailTableModel.setWrappedData(penjualan.getPenjualanDetailList());
		}
		
		PrimeFaces.current().executeScript("initSelect2();");
	}
	
	private void checkNewOrEdit() {
		editId = facesUtil.retrieveRequestParam("editId");
		
		if (StringUtils.isBlank(editId)) {
			handleNew();
		} else {
			handleEdit(editId);
		}
	}
	
	private void handleNew() {
		actionMode = Constants.ACTION_MODE_ADD;
		penjualan = new Penjualan();
		
		penjualan.setTransactionDate(new Date());
		
		penjualanDetailTableModel = new PenjualanDetailTableModel<PenjualanDetail>(penjualan.getPenjualanDetailList());
	}
	
	private void handleEdit(String editId) {
		actionMode = Constants.ACTION_MODE_EDIT;
		Long parseIdLong = Long.parseLong(editId);
		
		penjualan = penjualanService.findById(parseIdLong);
		
		onChangePlatform();
		onChangeEkspedisi();
		onChangeTypePenjualan();
		
		if (penjualan.getPenjualanDetailList() != null) {
			lastSeqOfPenjualanDetail = penjualan.getPenjualanDetailList().size();
			int penjualanDetailSeq = 0;
			for (int i = 0; i < penjualan.getPenjualanDetailList().size(); i++) {
				PenjualanDetail penjualanDetail = penjualan.getPenjualanDetailList().get(i);
				
				lastSeqOfPenjualanDetail = lastSeqOfPenjualanDetail + 1;
				penjualanDetail.setSequence(penjualanDetailSeq);
				penjualanDetail.setProductTemp(penjualanDetail.getProduct());
				penjualanDetail.setTotalBuyingTemp(penjualanDetail.getTotalBuying());
				penjualanDetail.setNamaProduct(penjualanDetail.getProduct().getProductName());
				
				penjualanDetailSeq++;
			}
		}
		
		lastSeqOfPenjualanDetail = penjualan.getPenjualanDetailList().size();
		penjualanDetailTableModel = new PenjualanDetailTableModel<PenjualanDetail>(penjualan.getPenjualanDetailList());
	}
	
	public void onAddNewPenjualanDetail() {
		if (penjualan.getPenjualanDetailList() == null || penjualan.getPenjualanDetailList().size() == 0) {
			penjualan.setPenjualanDetailList(new ArrayList<PenjualanDetail>());
			lastSeqOfPenjualanDetail = 0;
		}
		
		PenjualanDetail penjualanDetail = new PenjualanDetail();
		penjualanDetail.setPenjualan(penjualan);
		penjualanDetail.setSequence(lastSeqOfPenjualanDetail);
		
		penjualan.getPenjualanDetailList().add(penjualanDetail);
		penjualanDetailTableModel.setWrappedData(penjualan.getPenjualanDetailList());
		
		lastSeqOfPenjualanDetail++;
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void onDeleteRowPenjualanDetail() {
		if (selectedDataPenjualanDetail != null) {
			for (int i = 0; i < selectedDataPenjualanDetail.length; i++) {
				PenjualanDetail dataPenjualanDetail = selectedDataPenjualanDetail[i];
				
				if (dataPenjualanDetail.getProductTemp() != null) {
					removeDataPenjualanDetail.add(dataPenjualanDetail);
				}
			}
			
			penjualan.getPenjualanDetailList().removeAll(Arrays.asList(selectedDataPenjualanDetail));
			lastSeqOfPenjualanDetail = penjualan.getPenjualanDetailList().size();
			
			PrimeFaces.current().executeScript("reInitSelect2();");
		}
	}
	
	private Boolean isValidate() {
		Boolean flag = true;
		
		if (StringUtils.isEmpty(penjualan.getTypePenjualan())) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitleTypePenjualan") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		} else {
			if (StringUtils.equals(Constants.PARAM_DET_CODE_TRANSACTION_ONLINE, penjualan.getTypePenjualan())) {
				if (StringUtils.isEmpty(penjualan.getInvoice())) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitleInvoice") + " " + facesUtil.retrieveMessage("validateRequired"));
					flag = false;
				}
				
				if (StringUtils.isEmpty(penjualan.getPlatform())) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitlePlatform") + " " + facesUtil.retrieveMessage("validateRequired"));
					flag = false;
				}
				
				if (StringUtils.isEmpty(penjualan.getExpedision())) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitleExpedision") + " " + facesUtil.retrieveMessage("validateRequired"));
					flag = false;
				}
			}
		}
		
		if (StringUtils.isEmpty(penjualan.getCustomerName())) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitleCustomerName") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		if (penjualan.getTransactionDate() == null) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitleTransactionDate") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		if (penjualan.getPenjualanDetailList() == null) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitlePenjualanDetailTitle") + " " + facesUtil.retrieveMessage("validateMinOneData"));
			flag = false;
		} else {
			int a = 1;
			for (int i = 0; i < penjualan.getPenjualanDetailList().size(); i++) {
				PenjualanDetail dataPenjualanDetail = penjualan.getPenjualanDetailList().get(i);
				
				if (StringUtils.isEmpty(dataPenjualanDetail.getNamaProduct())) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitlePenjualanDetailProduct") + " " 
							+ facesUtil.retrieveMessage("textRow") + " " 
							+ a + " "
							+ facesUtil.retrieveMessage("validateRequired"));
					flag = false;
				}
				
				if (dataPenjualanDetail.getSellingPrice() == null) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitlePenjualanDetailHargaJual") + " " 
							+ facesUtil.retrieveMessage("textRow") + " " 
							+ a + " "
							+ facesUtil.retrieveMessage("validateRequired"));
					flag = false;
				}
				
				if (dataPenjualanDetail.getTotalBuying() == null) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formPenjualanTitlePenjualanDetailTotalBuying") + " " 
							+ facesUtil.retrieveMessage("textRow") + " " 
							+ a + " "
							+ facesUtil.retrieveMessage("validateRequired"));
					flag = false;
				}
			}
		}
		
		return flag;
	}
	
	private void settingProductPenjualan(Product product, PenjualanDetail penjualanDetail) {
		if (product.getProductModalList() != null) {
			for (int j = 0; j < product.getProductModalList().size(); j++) {
				ProductModal dataProductModal = product.getProductModalList().get(j);
				
				if (dataProductModal.getCurrentStock() != null) {
					dataProductModal.setCurrentStock(dataProductModal.getCurrentStock() - penjualanDetail.getTotalBuying().intValue());
					penjualanDetail.setProductModal(dataProductModal);
					
					EntityUtil.setUpdatePenjualan(dataProductModal);
					break;
				}
			}
			
			// Setting Current Stock Product
			int totalCurrentStock = 0;
			for (int j = 0; j < product.getProductModalList().size(); j++) {
				ProductModal dataProductModal = product.getProductModalList().get(j);
				
				totalCurrentStock = totalCurrentStock + dataProductModal.getCurrentStock();
			}
			
			product.setTotalStock(totalCurrentStock);
			EntityUtil.setUpdatePenjualan(product);
			
			productService.update(product);
			// Setting Current Stock Product
		}
	}
	
	private void reverseStock(PenjualanDetail penjualanDetail, Product product) {
		ProductModal updateDataProductModal = productModalService.findById(penjualanDetail.getProductModal().getProductModalId());
		updateDataProductModal.setCurrentStock(updateDataProductModal.getCurrentStock() + penjualanDetail.getTotalBuyingTemp().intValue());
		EntityUtil.setUpdatePenjualan(updateDataProductModal);
		productModalService.update(updateDataProductModal);
		
		// Setting Current Stock Product
		int totalCurrentStock = 0;
		for (int j = 0; j < product.getProductModalList().size(); j++) {
			ProductModal dataProductModal = product.getProductModalList().get(j);

			totalCurrentStock = totalCurrentStock + dataProductModal.getCurrentStock();
		}

		product.setTotalStock(totalCurrentStock);
		EntityUtil.setUpdatePenjualan(product);

		productService.update(product);
		// Setting Current Stock Product
	}
	
	private void settingMarginPenjualan(Penjualan penjualan, PenjualanDetail penjualanDetail) {
		if (StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA, penjualan.getPlatform())) {
			if (StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA_ANTARAJA, penjualan.getExpedision())
					|| StringUtils.equals(Constants.PARAM_DET_CODE_TOKOPEDIA_SICEPAT, penjualan.getExpedision())) {
				Double calcBebasOngkir = Double.valueOf(((Constants.TOKOPEDIA_BEBAS_ONGKIR/100) * penjualanDetail.getSellingPrice().intValue()) / 100);
				penjualanDetail.setMargin(calcBebasOngkir);
			} else {
				Double calcBebasOngkir = Double.valueOf(((Constants.TOKOPEDIA_NON_BEBAS_ONGKIR/100) * penjualanDetail.getSellingPrice()) / 100);
				penjualanDetail.setMargin(calcBebasOngkir);
			}
		} else if (StringUtils.equals(Constants.PARAM_DET_CODE_SHOPEE, penjualan.getPlatform())) {
			Double calcBebasOngkir = Double.valueOf(((Constants.SHOPEE_ADMIN/100) * penjualanDetail.getSellingPrice().intValue()) / 100);
			penjualanDetail.setMargin(calcBebasOngkir);
		} else if (StringUtils.equals(Constants.PARAM_DET_CODE_LAZADA, penjualan.getPlatform())) {
			Double calcBebasOngkir = Double.valueOf(((Constants.LAZADA_ADMIN/100) * penjualanDetail.getSellingPrice().intValue()) / 100);
			penjualanDetail.setMargin(calcBebasOngkir);
		} else if (StringUtils.equals(Constants.PARAM_DET_CODE_JD_ID, penjualan.getPlatform())) {
			Double calcBebasOngkir = Double.valueOf(((Constants.JD_ID_ADMIN/100) * penjualanDetail.getSellingPrice().intValue()) / 100);
			penjualanDetail.setMargin(calcBebasOngkir);
		}
	}
	
	public void save() {
		try {
			if (isValidate()) {
				
				if (penjualan.getPenjualanDetailList() != null) {
					for (int i = 0; i < penjualan.getPenjualanDetailList().size(); i++) {
						PenjualanDetail dataPenjualanDetail = penjualan.getPenjualanDetailList().get(i);
						Product product =  productService.findById(dataPenjualanDetail.getProduct().getProductId());
						
						dataPenjualanDetail.setPenjualan(penjualan);
						settingMarginPenjualan(penjualan, dataPenjualanDetail);
						
						// Ketika Add
						if (actionMode.equals(Constants.ACTION_MODE_ADD)) {
							settingProductPenjualan(product, dataPenjualanDetail);
							
							if (StringUtils.isEmpty(dataPenjualanDetail.getCreateBy())) {
								EntityUtil.setCreationInfo(dataPenjualanDetail, facesUtil.retrieveUserLogin());
							} else {
								EntityUtil.setUpdateInfo(dataPenjualanDetail, facesUtil.retrieveUserLogin());								
							}
						}
						// Ketika Add
						
						// Ketika Edit
						if (actionMode.equals(Constants.ACTION_MODE_EDIT)) {
							// Remove Data
							if (removeDataPenjualanDetail != null) {
								for (int j = 0; j < removeDataPenjualanDetail.size(); j++) {
									PenjualanDetail dataRemovePenjualanDetail = removeDataPenjualanDetail.get(i);
									Product productTemp = productService.findById(dataRemovePenjualanDetail.getProductTemp().getProductId());
									
									reverseStock(dataRemovePenjualanDetail, productTemp);
								}
							}
							// Remove Data
							
							if (StringUtils.isNotEmpty(dataPenjualanDetail.getChangeData()) 
									&& dataPenjualanDetail.getChangeData().equals(Constants.CHANGE_DATA)) {
								Product productTemp = productService.findById(dataPenjualanDetail.getProductTemp().getProductId());
								
								reverseStock(dataPenjualanDetail, productTemp);
								
								settingProductPenjualan(product, dataPenjualanDetail);
							} else {
								if (dataPenjualanDetail.getTotalBuyingTemp() == null) {
									settingProductPenjualan(product, dataPenjualanDetail);
								} else {
									if (dataPenjualanDetail.getTotalBuyingTemp() == dataPenjualanDetail.getTotalBuying()) {
										// do nothing
									} else {
										reverseStock(dataPenjualanDetail, product);
										
										settingProductPenjualan(product, dataPenjualanDetail);
									}
								}
							}
							
							if (StringUtils.isEmpty(dataPenjualanDetail.getCreateBy())) {
								EntityUtil.setCreationInfo(dataPenjualanDetail, facesUtil.retrieveUserLogin());
							} else {
								EntityUtil.setUpdateInfo(dataPenjualanDetail, facesUtil.retrieveUserLogin());								
							}
						}
						// Ketika Edit
					}
				}
				
				if (penjualan.getPenjualanId() != null) {
					EntityUtil.setUpdateInfo(penjualan, facesUtil.retrieveUserLogin());
					penjualanService.update(penjualan);
				} else {
					penjualan.setStatus(Constants.PARAM_DET_CODE_TRANSACTION_PACKING);
					
					EntityUtil.setCreationInfo(penjualan, facesUtil.retrieveUserLogin());
					penjualanService.save(penjualan);
				}
				
				facesUtil.redirect("/pages/transaction/penjualan/" + Constants.NAVIGATE_PENJUALAN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation Failed : " + e.getMessage(), "");
		}
	}
	
	public void cancel() {
		try {
			facesUtil.redirect("/pages/transaction/penjualan/" + Constants.NAVIGATE_PENJUALAN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

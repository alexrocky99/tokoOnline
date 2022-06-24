package com.online.module.master.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.parameter.model.ParameterDetail;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.model.ProductModal;
import com.online.module.master.product.model.ProductModalDetail;
import com.online.module.master.product.service.ProductService;
import com.online.module.master.product.tableModel.ProductModalTableModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEditBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -2010117292239851054L;
	private static final Logger logger = Logger.getLogger(ProductEditBean.class);

	private ProductService productService;
	
	private Product product;
	
	private String editId;
	private String actionMode;
	
	private Integer lastSeqOfProductModal;
	private Long productModalId;
	
	private List<SelectItem> productFromList;
	private List<ProductModalDetail> productModalDetailList;
	
	private ProductModalTableModel<ProductModal> tableDataProductModal;
	
	private ProductModal[] selectedDataProductModal;
	
	@PostConstruct
	public void init() {
		super.init();
		
		productModalDetailList = new ArrayList<ProductModalDetail>();
		
		selectProductFromList();
		
		checkNewOrEdit();
	}
	
	private void selectProductFromList() {
		productFromList = new ArrayList<SelectItem>();
		
		try {
			List<ParameterDetail> getProductFrom = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_DISTRIBUTOR, true);
			for (ParameterDetail parameterDetail : getProductFrom) {
				SelectItem si = new SelectItem();
				
				si.setLabel(parameterDetail.getDetailValue());
				si.setValue(parameterDetail.getDetailCode());
				
				productFromList.add(si);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void initProductModalDetail() {
		productModalDetailList = productService.getAllDataProductModalDetailByModalId(Long.parseLong(facesUtil.retrieveRequestParam("productModalId")));
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
		
		product = new Product();
		generateSKU(product);
		
		tableDataProductModal = new ProductModalTableModel<ProductModal>(product.getProductModalList());
	}
	
	private void handleEdit(String editId) {
		actionMode = Constants.ACTION_MODE_EDIT;
		Long parseIdLong = Long.parseLong(editId);
		
		product = productService.findById(parseIdLong);
		
		if (product.getProductModalList() != null) {
			lastSeqOfProductModal = product.getProductModalList().size();
			int productModalSeq = 0;
			for (int i = 0; i < product.getProductModalList().size(); i++) {
				ProductModal productModal = product.getProductModalList().get(i);
				
				lastSeqOfProductModal = lastSeqOfProductModal + 1;
				productModal.setSequence(productModalSeq);
				
				if (productModal.getProductModalDetailList() != null && productModal.getProductModalDetailList().size() > 0) {
					productModal.setIsModalDetail(true);
				}
				
				productModalSeq++;
			}
		}
		
		lastSeqOfProductModal = product.getProductModalList().size();
		
		tableDataProductModal = new ProductModalTableModel<ProductModal>(product.getProductModalList());
	}
	
	public void onAddNewProductModal() {
		if (product.getProductModalList() == null || product.getProductModalList().size() == 0) {
			product.setProductModalList(new ArrayList<ProductModal>());
			lastSeqOfProductModal = 0;
		}
		
		ProductModal productModal = new ProductModal();
		productModal.setProduct(product);
		productModal.setSequence(lastSeqOfProductModal);
		
		product.getProductModalList().add(productModal);
		tableDataProductModal.setWrappedData(product.getProductModalList());
		
		lastSeqOfProductModal++;
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void onDeleteRowProductModal() {
		if (selectedDataProductModal != null) {
			product.getProductModalList().removeAll(Arrays.asList(selectedDataProductModal));
			
			lastSeqOfProductModal = product.getProductModalList().size();
			
			PrimeFaces.current().executeScript("reInitSelect2();");
		}
	}
	
	private Boolean isValidate() {
		Boolean flag = true;
		
		if (StringUtils.isEmpty(product.getProductName())) {
			facesUtil.addErrMessage(facesUtil.retrieveMessage("formProductName") + " " + facesUtil.retrieveMessage("validateRequired"));
			flag = false;
		}
		
		if (product.getProductModalList() != null) {
			int a = 1;
			for (int i = 0; i < product.getProductModalList().size(); i++) {
				ProductModal productModal = product.getProductModalList().get(i);
				
				if (StringUtils.isEmpty(productModal.getProductFrom())) {
					facesUtil.addErrMessage(facesUtil.retrieveMessage("formProductModalDistributor") + " Baris Ke " + (a)  + " " + facesUtil.retrieveMessage("validateRequired"));
					flag = false;
				}
			}
		}
		
		return flag;
	}
	
	public void save() {
		try {
			if (isValidate()) {
			
				product.setStatus(Constants.PARAM_DET_CODE_STATUS_ACTIVE);
				
				Integer totalAllStock = 0;
				if (product.getProductModalList() != null) {
					for (int i = 0; i < product.getProductModalList().size(); i++) {
						ProductModal dataProductModal = product.getProductModalList().get(i);
						ProductModalDetail newDataProductModalDetail = new ProductModalDetail();
						
						Integer totalStock = 0;
						
						dataProductModal.setProduct(product);
						
						// Add Product Modal Detail
						if (dataProductModal.getUpdatePrice() != null || dataProductModal.getUpdateStock() != null) {
							if (dataProductModal.getUpdatePrice() != null)
								dataProductModal.setCurrentPrice(dataProductModal.getUpdatePrice());
							
							newDataProductModalDetail.setProductModal(dataProductModal);
							newDataProductModalDetail.setPrice(dataProductModal.getUpdatePrice() == null ? dataProductModal.getCurrentPrice() : dataProductModal.getUpdatePrice());
							newDataProductModalDetail.setStock(dataProductModal.getUpdateStock());
							
							EntityUtil.setCreationInfo(newDataProductModalDetail, facesUtil.retrieveUserLogin());
							
							dataProductModal.getProductModalDetailList().add(newDataProductModalDetail);
						}
						// Add Product Modal Detail
						
						// Setting current Stock
						for (int j = 0; j < dataProductModal.getProductModalDetailList().size(); j++) {
							ProductModalDetail dataProductModalDetail = dataProductModal.getProductModalDetailList().get(j);
							
							totalStock = totalStock + (dataProductModalDetail.getStock() != null ? dataProductModalDetail.getStock().intValue() : 0);
						}
						dataProductModal.setCurrentStock(totalStock);
						// Setting current Stock
					
						totalAllStock = totalAllStock + dataProductModal.getCurrentStock();
						
						product.setTotalStock(totalAllStock);
						
						if (StringUtils.isEmpty(dataProductModal.getCreateBy())) {
							EntityUtil.setCreationInfo(dataProductModal, facesUtil.retrieveUserLogin());
						} else {
							EntityUtil.setUpdateInfo(dataProductModal, facesUtil.retrieveUserLogin());
						}
					}
				}
				
				if (product.getProductId() != null) {
					EntityUtil.setUpdateInfo(product, facesUtil.retrieveUserLogin());
					productService.update(product);
				} else {
					generateProductCode(product);
					product.setBarcode(new String(Constants.getQrCodeImage(product.getProductCode(), 250, 250)));
					
					EntityUtil.setCreationInfo(product, facesUtil.retrieveUserLogin());
					productService.save(product);
				}
				
				facesUtil.redirect("/pages/master/product/" + Constants.NAVIGATE_PRODUCT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation Failed : " + e.getMessage(), "");
		}
	}
	
	public void cancel() {
		try {
			facesUtil.redirect("/pages/master/product/" + Constants.NAVIGATE_PRODUCT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void generateProductCode(Product product) {
		String productCode = null;
		String totalProductTemp = null;
		Long totalProduct = productService.totalProductInTable();
		if (totalProduct == 0) totalProduct = totalProduct + 1;
		
		if (totalProduct > 1 || totalProduct < 10) {
			totalProductTemp = "000000" + String.valueOf(totalProduct);
		} else if (totalProduct > 10 || totalProduct < 100) {
			totalProductTemp = "00000" + String.valueOf(totalProduct);
		} else if (totalProduct > 1000 || totalProduct < 10000) {
			totalProductTemp = "0000" + String.valueOf(totalProduct);
		} else if (totalProduct > 10000 || totalProduct < 100000) {
			totalProductTemp = "000" + String.valueOf(totalProduct);
		} else if (totalProduct > 100000 || totalProduct < 1000000) {
			totalProductTemp = "00" + String.valueOf(totalProduct);
		} else if (totalProduct > 1000000 || totalProduct < 10000000) {
			totalProductTemp = "0" + String.valueOf(totalProduct);
		} else {
			totalProductTemp = String.valueOf(totalProduct);
		}
		
		if (product.getProductName().contains(" ")) {
			String[] arrSplit = product.getProductName().split(" ");
			
			for (int i = 0; i < arrSplit.length; i++) {
				if (i < 3) {
					String a = arrSplit[i];
					
					for (int j = 0; j < a.length(); j++) {
						char aa = a.charAt(j);
						productCode = StringUtils.isEmpty(productCode) ? String.valueOf(aa) : productCode.concat(String.valueOf(aa));
						break;
					}
				}
			}
			productCode = productCode.concat(totalProductTemp);
		} else {
			for (int i = 0; i < product.getProductName().length(); i++) {
				char a = product.getProductName().charAt(i);
				
				productCode = String.valueOf(a).concat(totalProductTemp);
				break;
			}
		}
		
		product.setProductCode(productCode.toUpperCase());
	}
	
	private void generateSKU(Product product) {
		String formatSku = Constants.FORMAT_DATE_TIME_NOT_SPACE.format(Calendar.getInstance().getTime());
		product.setSku(formatSku);
	}

	public static Logger getLogger() {
		return logger;
	}
	
}

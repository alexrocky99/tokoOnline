package com.online.module.master.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.online.module.common.bean.CommonBean;
import com.online.module.common.constant.Constants;
import com.online.module.common.excel.DataXlsHandler;
import com.online.module.common.paging.DBLazyDataModel;
import com.online.module.common.paging.DefaultSearchObject;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.parameter.model.ParameterDetail;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.service.ProductService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 631806182727094854L;
	private static final Logger logger = Logger.getLogger(ProductBean.class);
	private static final String NAVIGATE_EDIT = Constants.NAVIGATE_PRODUCT_EDIT;
	
	private ProductService productService;
	
	private String searchProductName;
	private String searchProductCode;
	private String searchProductSortBy;
	
	private List<SelectItem> sortByProduct;
	
	private DBLazyDataModel<Product> tableModel;
	
	@PostConstruct
	public void init() {
		super.init();
		
		selectSortByProduct();
		
		tableModel = new DBLazyDataModel<>(productService, paging);
	}
	
	private void selectSortByProduct() {
		List<ParameterDetail> getSortByProduct = parameterDetailService.getParameterDetaiListByCode(Constants.PARAM_CODE_SORT_BY_PRODUCT, true);
		sortByProduct = new ArrayList<>();
		
		try {
			if (getSortByProduct != null) {
				for (int i = 0; i < getSortByProduct.size(); i++) {
					ParameterDetail data = getSortByProduct.get(i);
					SelectItem si = new SelectItem();
					
					si.setLabel(data.getDetailValue());
					si.setValue(data.getDetailCode());
					
					sortByProduct.add(si);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrimeFaces.current().executeScript("reInitSelect2();");
	}
	
	public void search(ActionEvent actionEvent) {
		tableModel.setSearchCriteria(Arrays.asList(
				new DefaultSearchObject(Constants.WHERE_NAME, searchProductName),
				new DefaultSearchObject(Constants.WHERE_CODE, searchProductCode),
				new DefaultSearchObject(Constants.WHERE_SORT_BY, searchProductSortBy)));
	}
	
	public void reset(ActionEvent actionEvent) {
		searchProductName = null;
		searchProductCode = null;
		searchProductCode = null;
		
		search(actionEvent);
	}
	
	public void delete(Long deleteId) {
		try {
			Product product = productService.findById(deleteId);
			EntityUtil.setUpdateDeleteWithFlag(product, facesUtil.retrieveUserLogin());
			productService.update(product);
			
			facesUtil.addSuccMessage(facesUtil.retrieveMessage("deleteSuccess"));
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.addFacesMsg(FacesMessage.SEVERITY_ERROR, null, "Operation failed : " + e.getMessage(), "");
		}
	}

	public StreamedContent getTemplateStep1() {
		return productService.downloadTemplateStep1();
	}
	
	public StreamedContent getTemplateStep2() {
		return productService.downloadTemplateStep2();	
	}
	
	public void uploadFileStep1(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			List<Product> productList = readAllProductStep1(file);
			
			if (productList != null) {
				productService.saveProduct(productList, true);
				search(null);
				
				facesUtil.addSuccMessage(facesUtil.retrieveMessage("successMessageUpload"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void uploadFileStep2(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			List<Product> productList = readAllProductStep2(file);
			
			if (productList != null) {
				productService.saveProduct(productList, true);
				search(null);
				
				facesUtil.addSuccMessage(facesUtil.retrieveMessage("successMessageUpload"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Product> readAllProductStep1(UploadedFile file) throws Exception {
		List<Product> productList = new ArrayList<Product>();
		DataXlsHandler handler = new DataXlsHandler();
		
		Workbook workbook = handler.createWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> rowIter = sheet.rowIterator();
		
		rowIter = handler.skipFirstRow(rowIter);
		
		while (rowIter.hasNext()) {
			Row row = (Row) rowIter.next();
			
			Product data = new Product();
			
			data = handler.insertDataProductStep1(data, row);
			
			EntityUtil.setCreationInfo(data, facesUtil.retrieveUserLogin());
			
			productList.add(data);
		}
		
		return productList;
	}
	
	private List<Product> readAllProductStep2(UploadedFile file) throws Exception {
		List<Product> productList = new ArrayList<Product>();
		DataXlsHandler handler = new DataXlsHandler();
		
		Workbook workbook = handler.createWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> rowIter = sheet.rowIterator();
		
		rowIter = handler.skipFirstRow(rowIter);
		
		while (rowIter.hasNext()) {
			Row row = (Row) rowIter.next();
			Long productId = productService.getProductIdByCode(row.getCell(0).getStringCellValue());
			
			Product data = productService.findById(productId);
			
			data = handler.insertDataProductStep2(data, row, facesUtil.retrieveUserLogin(), parameterDetailService);
			
			EntityUtil.setUpdateInfo(data, facesUtil.retrieveUserLogin());
			
			productList.add(data);
		}
		
		return productList;
	}
	
	public static String getNavigateEdit() {
		return NAVIGATE_EDIT;
	}

	public static Logger getLogger() {
		return logger;
	}

}

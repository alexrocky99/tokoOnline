package com.online.module.common.excel;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.file.UploadedFile;

import com.online.module.common.constant.Constants;
import com.online.module.common.util.EntityUtil;
import com.online.module.master.parameter.model.ParameterDetail;
import com.online.module.master.parameter.service.ParameterDetailService;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.model.ProductModal;
import com.online.module.master.product.model.ProductModalDetail;

public class DataXlsHandler {

	public Workbook createWorkbook(UploadedFile uploadedFile) throws Exception {
		String ext = Constants.getExtensionFromFileName(uploadedFile.getFileName());
		if (StringUtils.equalsIgnoreCase("xls", ext)) {
			return new HSSFWorkbook(new ByteArrayInputStream(uploadedFile.getContent()));
		} else if (StringUtils.equalsIgnoreCase("xlsx", ext)) {
			return new XSSFWorkbook(new ByteArrayInputStream(uploadedFile.getContent()));
		} else {
			throw new RuntimeException("Extension not valid");
		}
	}
	
	public Iterator<Row> skipFirstRow(Iterator<Row> rowIter) {
		if (rowIter.hasNext()) {
			rowIter.next();
		}
		
		return rowIter;
	}
	
	public Product insertDataProductStep1(Product data, Row row) throws Exception {
		if (row.getCell(0) != null && row.getCell(0).getStringCellValue() != null && !row.getCell(0).getStringCellValue().isEmpty()) {
			String sku = row.getCell(0).getStringCellValue();
			data.setSku(sku);
		}
		
		if (row.getCell(1) != null && row.getCell(1).getStringCellValue() != null && !row.getCell(1).getStringCellValue().isEmpty()) {
			String productName = row.getCell(1).getStringCellValue();
			data.setProductName(productName);
		}
		
		if (row.getCell(2) != null && row.getCell(2).getStringCellValue() != null && !row.getCell(2).getStringCellValue().isEmpty()) {
			String description = row.getCell(2).getStringCellValue();
			data.setDescription(description);
		}
		
		if (row.getCell(3) != null) {
			Double price = row.getCell(3).getNumericCellValue();
			data.setPrice(new BigDecimal(price));
		}
		
		data.setStatus(Constants.PARAM_DET_CODE_STATUS_ACTIVE);
		
		return data;
	}
	
	public Product insertDataProductStep2(Product data, Row row, String username, ParameterDetailService parameterDetailService) throws Exception {
		if (row.getCell(1) != null && row.getCell(1).getStringCellValue() != null && !row.getCell(1).getStringCellValue().isEmpty()) {
			ProductModal newProductModal = new ProductModal();
			
			newProductModal.setProduct(data);
			
			ParameterDetail pdDistributor = parameterDetailService.getParameterDetailByDetailValue(row.getCell(1).getStringCellValue());
			
			newProductModal.setProductFrom(pdDistributor.getDetailCode());
			
			if (row.getCell(2) != null) {
				newProductModal.setCurrentStock((int) row.getCell(2).getNumericCellValue());
			}
			
			if (row.getCell(3) != null) {
				newProductModal.setCurrentPrice(new BigDecimal(row.getCell(3).getNumericCellValue()));
			}
			
			EntityUtil.setCreationInfo(newProductModal, username);
			
			if (newProductModal.getCurrentPrice() != null || newProductModal.getCurrentStock() != null) {
				ProductModalDetail newProductModalDetail = new ProductModalDetail();
				
				newProductModalDetail.setProductModal(newProductModal);
				newProductModalDetail.setPrice(newProductModal.getCurrentPrice());
				newProductModalDetail.setStock(newProductModal.getCurrentStock());
				
				EntityUtil.setCreationInfo(newProductModalDetail, username);
				
				newProductModal.getProductModalDetailList().add(newProductModalDetail);
			}
			
			data.getProductModalList().add(newProductModal);
		}
		
		// SETTING CURRENT STOCK
		int totalCurrentStock = 0;
		
		if (data.getProductModalList() != null) {
			for (int i = 0; i < data.getProductModalList().size(); i++) {
				ProductModal dataProductModal = data.getProductModalList().get(i);
				
				totalCurrentStock = totalCurrentStock + dataProductModal.getCurrentStock();
			}
		}
		data.setTotalStock(totalCurrentStock);
		// SETTING CURRENT STOCK
		
		return data;
	}
}

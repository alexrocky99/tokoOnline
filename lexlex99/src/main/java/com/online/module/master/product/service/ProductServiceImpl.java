package com.online.module.master.product.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.online.module.common.constant.Constants;
import com.online.module.common.paging.SearchObject;
import com.online.module.master.parameter.dao.ParameterDetailDao;
import com.online.module.master.parameter.model.ParameterDetail;
import com.online.module.master.product.dao.ProductDao;
import com.online.module.master.product.model.Product;
import com.online.module.master.product.model.ProductModalDetail;

@Transactional
@Service("productService")
public class ProductServiceImpl implements ProductService, Serializable {

	private static final long serialVersionUID = -918175208002466804L;
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;

	@Autowired
	@Qualifier("parameterDetailDao")
	private ParameterDetailDao parameterDetailDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Product> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) throws Exception {
		return productDao.searchData(searchCriteria, first, pageSize, sortBy, filterBy);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return productDao.searchCountData(searchCriteria);
	}

	@Override
	public void save(Product product) {
		productDao.save(product);
	}

	@Override
	public void update(Product product) {
		productDao.update(product);
	}

	@Override
	public Product findById(Long productId) {
		return productDao.findById(productId);
	}

	public static Logger getLogger() {
		return logger;
	}

	@SuppressWarnings("static-access")
	@Override
	public StreamedContent downloadTemplateStep1() {
		StreamedContent streamContent = null;
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_FILE_PATH_PRODUCT_STEP_1);

		streamContent = new DefaultStreamedContent()
				.builder()
				.contentType("application/xls")
				.name("templateProductStep1.xlsx").stream(() -> is).build();
		
		return streamContent;
	}

	@Override
	public Long totalProductInTable() {
		return productDao.totalProductInTable();
	}

	@Override
	public void saveProduct(List<Product> productList, Boolean isRaw) throws Exception {
		try {
			for (Product product : productList) {
				if (StringUtils.isEmpty(product.getSku())) {
					generateSKU(product);
				}
				if (StringUtils.isEmpty(product.getProductCode())) {
					generateProductCode(product);					
				}
				if (product.getProductId() != null) {
					productDao.update(product);
				} else {
					productDao.save(product);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unused")
	private void generateProductCode(Product product) {
		String productCode = null;
		String totalProductTemp = null;
		Long totalProduct = productDao.totalProductInTable();
		
		totalProduct = totalProduct + 1;
		
		if (totalProduct < 1 || totalProduct <= 9) {
			totalProductTemp = "000000" + String.valueOf(totalProduct);
		} else if (totalProduct < 10 || totalProduct <= 99) {
			totalProductTemp = "00000" + String.valueOf(totalProduct);
		} else if (totalProduct < 100 || totalProduct <= 999) {
			totalProductTemp = "0000" + String.valueOf(totalProduct);
		} else if (totalProduct < 10000 || totalProduct <= 9999) {
			totalProductTemp = "000" + String.valueOf(totalProduct);
		} else if (totalProduct < 100000 || totalProduct <= 99999) {
			totalProductTemp = "00" + String.valueOf(totalProduct);
		} else if (totalProduct < 1000000 || totalProduct <= 999999) {
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

	@Override
	public List<ProductModalDetail> getAllDataProductModalDetailByModalId(Long productModalId) {
		return productDao.getAllDataProductModalDetailByModalId(productModalId);
	}

	@SuppressWarnings({ "static-access", "resource", "unused" })
	@Override
	public StreamedContent downloadTemplateStep2() {
		Workbook wb = null;
		ByteArrayInputStream bais = null;
		ByteArrayOutputStream baos = null;
		StreamedContent streamContent = null;
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_FILE_PATH_PRODUCT_STEP_2);

		try {
			wb = new XSSFWorkbook(is);
			Sheet sheet;
			
			sheet = wb.getSheet("listDistributor");
			int distributorCount = this.templateWriteListDistributor(sheet);
			
			sheet = wb.getSheet("sheet1");
			this.templateCreatePickList(sheet, distributorCount);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			baos = new ByteArrayOutputStream();
			wb.write(baos);
		} catch (Exception e) {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			return null;
		}
		
		try {
			bais = new ByteArrayInputStream(baos.toByteArray());
			InputStream newIs = new ByteArrayInputStream(baos.toByteArray());
			
			streamContent = new DefaultStreamedContent()
					.builder()
					.contentType("application/xls")
					.name("templateProductStep2.xlsx").stream(() -> newIs).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return streamContent;
	}
	
	private void templateCreatePickList(Sheet sheet, int distributorCount) {
		Name namedRange;
		String colName;
		String formula;
		
		// Distributor
		namedRange = sheet.getWorkbook().createName();
		namedRange.setNameName("listDistributor");
		colName = Constants.excelColumnIndexName(0);
		formula = String.format("%s!$%s$%d:$%s$%d", "listDistributor", colName, 1 + 1, colName, 1 + distributorCount);
		
		namedRange.setRefersToFormula(formula);
		
		Constants.createPickListXlsxFromFormula(sheet, 1, 1, "listDistributor");
		// Distributor
	}
	
	private int templateWriteListDistributor(Sheet sheet) throws Exception {
		Row row = null;
		Cell cell = null;
		CellStyle style = null;
		
		int rowNum = 1;
		List<ParameterDetail> listDistributor = parameterDetailDao.getParameterDetaiListByCode(Constants.PARAM_CODE_DISTRIBUTOR, true);
		
		for (ParameterDetail parameterDetail : listDistributor) {
			if (rowNum == 1) {
				row = sheet.getRow(rowNum);
				
				cell = row.getCell(0);
				style = cell.getCellStyle();
				cell.setCellValue(parameterDetail.getDetailValue());
			} else {
				row = sheet.createRow(rowNum);
				
				cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(parameterDetail.getDetailValue());
			}
			
			rowNum++;
		}
		
		return listDistributor.size();
	}

	@Override
	public Long getProductIdByCode(String productCode) {
		return productDao.getProductIdByCode(productCode);
	}

	@Override
	public List<Product> getAllProduct() {
		return productDao.getAllProduct();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Product> searchDataProductDialog(List<? extends SearchObject> searchCriteria) {
		return productDao.searchDataProductDialog(searchCriteria);
	}
	
}

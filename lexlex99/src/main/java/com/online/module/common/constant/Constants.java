package com.online.module.common.constant;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public abstract class Constants {

	public static final Double TOKOPEDIA_BEBAS_ONGKIR = Double.valueOf(300);
	public static final Double TOKOPEDIA_NON_BEBAS_ONGKIR = Double.valueOf(150);
	public static final Double SHOPEE_ADMIN = Double.valueOf(300);
	public static final Double LAZADA_ADMIN = Double.valueOf(200);
	public static final Double JD_ID_ADMIN = Double.valueOf(100);
	
	public static final String SESSION_EMPLOYEE = "SESSION_EMPLOYEE";
	public static final String SESSION_USERNAME = "SESSION_USERNAME";
	public static final String SESSION_LANGUAGE = "SESSION_LANGUAGE";
	public static final String SESSION_MENU = "SESSION_MENU";
	public static final String SESSION_ALLOWED_MENU = "SESSION_ALLOWED_MENU";
	
	public static final String LOGIN_HOME_URL = "/pages/login/login.faces";
	
	public static final String calg = "Blowfish";
	
	public static final String MSG_LOGIN_SUCCESSFULL = "LOGIN_SUCCESSFULL";
	
	public static final String MAP_KEY_CELL_FORMAT_DATA_DTL_NUMBER = "cellFormatNumber";
	public static final String MAP_KEY_CELL_FORMAT_DATA_DTL_DATE = "cellFormatDate";
	public static final String MAP_KEY_CELL_FORMAT_DATA_DTL_STRING = "cellFormatString";
	public static final String MAP_KEY_CELL_FORMAT_DATA_DTL_WRAP_STRING = "cellFormatWrapString";
	public static final String MAP_KEY_CELL_FORMAT_DATA_DTL_WRAP_NO_BOTTOM_STRING = "cellFormatWrapNoBottomString";
	public static final String MAP_KEY_CELL_FORMAT_DATA_DTL_WRAP_BORDER_TOP_ONLY_STRING = "cellFormatWrapTopOnlyString";
	public static final String MAP_KEY_CELL_FORMAT_HEADER_VALUE = "cellFormatHeaderValue";
	public static final String MAP_KEY_CELL_FORMAT_HEADER_LABEL = "cellFormatHeaderLabel";
	public static final String MAP_KEY_CELL_FORMAT_HEADER_TITLE = "cellFormatHeaderTitle";
	public static final String MAP_KEY_CELL_FORMAT_COLUMN_HEADER = "cellFormatColumnHeader";
	public static final String MAP_KEY_CELL_FORMAT_COLUMN_WRAP_HEADER = "cellFormatColumnWrapHeader";
	public static final String MAP_KEY_CELL_FORMAT_COLUMN_HEADER_NUMBER = "cellFormatColumnHeaderNumber";
	
	public static final String CONSTANT_YES = "Y";
	public static final String CONSTANT_NO = "N";
	public static final String ACTION_MODE_ADD = "ADD";
	public static final String ACTION_MODE_VIEW = "VIEW";
	public static final String ACTION_MODE_EDIT = "EDIT";
	
	public static final String CHANGE_DATA = "CHANGE_DATA";
	public static final String TRANSACTION = "TRANSACTION";
	
	public static final String TEMPLATE_FILE_PATH_PRODUCT_STEP_1 = "com/online/module/template/templateProductStep1.xlsx";
	public static final String TEMPLATE_FILE_PATH_PRODUCT_STEP_2 = "com/online/module/template/templateProductStep2.xlsx";
	
	public static final int DEFAULT_PAGING_NUMBER = 10;
	
	public static final String NAVIGATE_MENU_PARAMETER = "parameterDetail.faces";
	public static final String NAVIGATE_MENU_PARAMETER_EDIT = "parameterDetailEdit.faces";
	public static final String NAVIGATE_MENU = "menu.faces";
	public static final String NAVIGATE_MENU_EDIT = "menuEdit.faces";
	public static final String NAVIGATE_PEOPLE = "people.faces";
	public static final String NAVIGATE_PEOPLE_EDIT = "peopleEdit.faces";
	public static final String NAVIGATE_RESPONSIBILITY = "responsibility.faces";
	public static final String NAVIGATE_RESPONSIBILITY_EDIT = "responsibilityEdit.faces";
	public static final String NAVIGATE_PRODUCT = "product.faces";
	public static final String NAVIGATE_PRODUCT_EDIT = "productEdit.faces";
	public static final String NAVIGATE_PENJUALAN = "penjualan.faces";
	public static final String NAVIGATE_PENJUALAN_EDIT = "penjualanEdit.faces";
	public static final String NAVIGATE_SELLER = "seller.faces";
	public static final String NAVIGATE_SELLER_EDIT = "sellerEdit.faces";
	public static final String NAVIGATE_RESI = "resi.faces";
	public static final String NAVIGATE_RESI_EDIT = "resiEdit.faces";
	
	public static final String SISTEM_DET_CODE_ATTACHMENT_FILE_PATH = "ATTACHMENT_FILE_PATH";
	
	public static final SimpleDateFormat FORMAT_2_DIGIT_YEAR = new SimpleDateFormat("yy");
	public static final SimpleDateFormat FORMAT_DATE_TIME_NOT_SPACE = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat FORMAT_DATE_TIME_WITH_STRIP = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	public static final SimpleDateFormat FORMAT_DATE_YYYYMMdd = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final String WIDGET_VAR_PRODUCT_DIALOG = "productDialog";
	
	public static final String PARAM_CODE_MENU_TYPE = "MENU_TYPE";
	public static final String PARAM_CODE_GENDER = "GENDER";
	public static final String PARAM_CODE_DISTRIBUTOR = "DISTRIBUTOR";
	public static final String PARAM_CODE_PLATFORM = "PLATFORM";
	public static final String PARAM_CODE_EXPEDISI = "EXPEDISI";
	public static final String PARAM_CODE_TOKOPEDIA_EXPEDISION = "TOKOPEDIA_EXPEDISION";
	public static final String PARAM_CODE_LAZADA_EXPEDISION = "LAZADA_EXPEDISION";
	public static final String PARAM_CODE_SHOPEE_EXPEDISION = "SHOPEE_EXPEDISION";
	public static final String PARAM_CODE_JD_ID_EXPEDISION = "JD_ID_EXPEDISION";
	public static final String PARAM_CODE_TYPE_PENJUALAN = "TYPE_PENJUALAN";
	public static final String PARAM_CODE_PAYMENT_STATUS = "PAYMENT_STATUS";
	public static final String PARAM_CODE_SORT_BY_PRODUCT = "SORT_BY_PRODUCT";
	
	public static final String PARAM_DET_CODE_STATUS_ACTIVE = "STATUS_ACTIVE";
	public static final String PARAM_DET_CODE_STATUS_NON_ACTIVE = "STATUS_NON_ACTIVE";
	public static final String PARAM_DET_CODE_TOKOPEDIA = "TOKOPEDIA";
	public static final String PARAM_DET_CODE_SHOPEE = "SHOPEE";
	public static final String PARAM_DET_CODE_JD_ID = "JD_ID";
	public static final String PARAM_DET_CODE_LAZADA = "LAZADA";
	public static final String PARAM_DET_CODE_TOKOPEDIA_SICEPAT = "TOKOPEDIA_SICEPAT";
	public static final String PARAM_DET_CODE_TOKOPEDIA_J_T = "TOKOPEDIA_J&T";
	public static final String PARAM_DET_CODE_TOKOPEDIA_JNE = "TOKOPEDIA_JNE";
	public static final String PARAM_DET_CODE_TOKOPEDIA_LION_PARCEL = "TOKOPEDIA_LION_PARCEL";
	public static final String PARAM_DET_CODE_TOKOPEDIA_ANTARAJA = "TOKOPEDIA_ANTARAJA";
	public static final String PARAM_DET_CODE_TRANSACTION_ONLINE = "TRANSACTION_ONLINE";
	public static final String PARAM_DET_CODE_TRANSACTION_OFFLINE = "TRANSACTION_OFFLINE";
	public static final String PARAM_DET_CODE_TRANSACTION_PACKING = "TRANSACTION_PACKING";
	public static final String PARAM_DET_CODE_SORT_BY_PRODUCT_PRICE_NULL = "SORT_BY_PRODUCT_PRICE_NULL";
	public static final String PARAM_DET_CODE_SORT_BY_PRODUCT_PRICE_NOT_NULL = "SORT_BY_PRODUCT_PRICE_NOT_NULL";
	
	public static final String WHERE_SISTEM_CODE = "WHERE_SISTEM_CODE";
	public static final String WHERE_STATUS = "WHERE_STATUS";
	public static final String WHERE_PARAM_CODE = "WHERE_PARAM_CODE";
	public static final String WHERE_PARAM_DETAIL_CODE = "WHERE_PARAM_DETAIL_CODE";
	public static final String WHERE_NAME = "WHERE_NAME";
	public static final String WHERE_EMAIL = "WHERE_EMAIL";
	public static final String WHERE_PEOPLE_NUMBER = "WHERE_PEOPLE_NUMBER";
	public static final String WHERE_GENDER = "WHERE_GENDER";
	public static final String WHERE_RESPONSIBILITY = "WHERE_RESPONSIBILITY";
	public static final String WHERE_VALUE = "WHERE_VALUE";
	public static final String WHERE_CODE = "WHERE_CODE";
	public static final String WHERE_INVOICE = "WHERE_INVOICE";
	public static final String WHERE_DATE = "WHERE_DATE";
	public static final String WHERE_START_DATE = "WHERE_START_DATE";
	public static final String WHERE_END_DATE = "WHERE_END_DATE";
	public static final String WHERE_RECEIPT_NUMBER = "WHERE_RECEIPT_NUMBER";
	public static final String WHERE_SORT_BY = "WHERE_SORT_BY";
	
	protected static int keylen = 128;
	
	public static String encryptString(String str) {
		SecretKeySpec key = readKey();
		byte[] messb = str.getBytes();
		byte[] ct = encrypt(messb, key);
		
		return bintohex(ct);
	}
	
	public static String decryptString(String str) {
		SecretKeySpec key = readKey();
		byte[] pt = decrypt(hextobin(str), key);
		return new String(pt);
	}
	
	public static byte[] encrypt(byte[] t, SecretKeySpec k) {
		try {
			Cipher c = Cipher.getInstance(calg);
			c.init(Cipher.ENCRYPT_MODE, k);
			return c.doFinal(t);
		} catch (Exception e) {
			System.err.println("Encryption failed: " + e);
		}
		
		return new byte[0];
	}
	
	public static byte[] decrypt(byte[] t, SecretKeySpec k) {
		try {
			Cipher c = Cipher.getInstance(calg);
			
			c.init(Cipher.DECRYPT_MODE, k);
			
			return c.doFinal(t);
		} catch (Exception e) {
			System.err.println("Decryption failed: " + e);
		}
		
		return new byte[0];
	}
	
	public static SecretKeySpec readKey() {
		SecretKeySpec secretKeySpec = null;
		String line;
		byte[] bin = null;
		
		try {
			line = "417b8b2d167b9046a4041e83509b2610";
			
			boolean isHex = true;
			for (int i = 0; i < line.length(); i++) {
				if (Character.digit(line.charAt(i), 16) < 0) {
					isHex = false;
					break;
				}
			}
			
			if (isHex && line.length() != keylen/4) {
				System.err.println("Wrong hex Let Length (" + line.length() + "/" + keylen/4 + ")");
			}
			
			if (isHex) {
				bin = hextobin(line);
			} else {
				bin = asciitobin(line);
			}
			
			secretKeySpec = new SecretKeySpec(bin, calg);
			
			System.out.println("Key= |" + bintohex(secretKeySpec.getEncoded()) + "|");
		} catch (Exception e) {
			System.err.println("Key Generation Failed" + e);
		}
		
		return secretKeySpec;
	}
	
	public static byte[] hextobin(String s) {
		int len = (s.length()+1) / 2;
		byte[] a = new byte[len];
		for (int i = 0; i < len; i++) {
			a[i] = Integer.valueOf(s.substring(i*2, i*2+2), 16).byteValue();
		}
		
		return a;
	}
	
	public static byte[] asciitobin(String s) {
		byte[] a = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			a = md.digest(s.getBytes());
		} catch (Exception e) {
			System.err.println("Digest Failed" + e);
		}
		
		return a;
	}
	
	public static String bintohex(byte[] a) {
		int len = a.length;
		StringBuffer sb = new StringBuffer(len*2);
		for (int i = 0; i < len; i++) {
			if ((a[i] & 0xFF) < 0X10) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(a[i] & 0xFF));
		}
		
		return sb.toString();
	}
	
	public static byte[] getQrCodeImage(String text, int width, int height) throws Exception {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002,0xFFFFC041);
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
		return pngOutputStream.toByteArray();
	}
	
	public static String getExtensionFromFileName(String filename) {
		int lastIdx = StringUtils.lastIndexOf(filename, ".");
		if (lastIdx >= 0) {
			return StringUtils.substring(filename, lastIdx+1);
		} else {
			return "";
		}
	}
	
	public static String excelColumnIndexName(int index) {
		StringBuilder sb = new StringBuilder();
		
		while (index >= 26) {
			sb.insert(0, (char) ('A' + index % 26));
			index = index / 26 - 1;
		}
		sb.insert(0, (char) ('A' + index));
		
		return sb.toString();
	}
	
	public static void createPickListXlsxFromFormula(Sheet sheet, int startRow, int col, String formulaName) {
		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formulaName);
		CellRangeAddressList addressList = new CellRangeAddressList(startRow, SpreadsheetVersion.EXCEL2007.getMaxRows() - 1, col, col);
		XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
		validation.setShowErrorBox(true);
		sheet.addValidationData(validation);
	}
}

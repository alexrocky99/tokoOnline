package com.online.module.common.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.online.module.common.constant.Constants;

public class CommonReportUtil {

	private static Logger logger = Logger.getLogger(CommonReportUtil.class);
	
	public String generateFileName(String fileNamePrefix, String nik) {
		String tempFileNamePrefix = StringUtils.EMPTY;
		
		if (fileNamePrefix != null && !fileNamePrefix.isEmpty()) {
			tempFileNamePrefix = fileNamePrefix + "-";
		}
		
		String fileName = tempFileNamePrefix + System.currentTimeMillis() + ".xlsx";
		
		return fileName;
	}
	
	public void createExcelCellFormat(XSSFWorkbook book, Map<String, CellStyle> mapCellFormat) throws Exception {
		
		// Cell Format Column Header
		CellStyle styleHeader = book.createCellStyle();
		
		styleHeader.setAlignment(HorizontalAlignment.CENTER);
		styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeader.setBorderBottom(BorderStyle.MEDIUM);
		styleHeader.setBorderTop(BorderStyle.MEDIUM);
		styleHeader.setBorderLeft(BorderStyle.MEDIUM);
		styleHeader.setBorderRight(BorderStyle.MEDIUM);
		styleHeader.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFFont headerFont = book.createFont();
		
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setFontName("Arial");
		headerFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		headerFont.setBold(true);
		
		styleHeader.setFont(headerFont);
		// Cell Format Column Header
		
		// Cell Format Data Detail String
		CellStyle styleString = book.createCellStyle();
		
		styleString.setAlignment(HorizontalAlignment.LEFT);
		styleString.setBorderBottom(BorderStyle.THIN);
		styleString.setBorderLeft(BorderStyle.THIN);
		styleString.setBorderRight(BorderStyle.THIN);
		
		XSSFFont stringFont = book.createFont();
		
		stringFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		
		styleString.setFont(stringFont);
		// Cell Format Data Detail String
		
		// Cell Format Data Detail Number
		CellStyle styleNumber = book.createCellStyle();
		XSSFDataFormat fmt = book.createDataFormat();
		
		styleNumber.setDataFormat(fmt.getFormat("#,##0"));
		styleNumber.setAlignment(HorizontalAlignment.RIGHT);
		styleNumber.setBorderBottom(BorderStyle.THIN);
		styleNumber.setBorderLeft(BorderStyle.THIN);
		styleNumber.setBorderRight(BorderStyle.THIN);
		
		XSSFFont numberFont = book.createFont();
		numberFont.setFontHeightInPoints((short) 10);
		numberFont.setFontName("Arial");
		numberFont.setColor(IndexedColors.BLACK.getIndex());
		
		styleNumber.setFont(numberFont);
		// Cell Format Data Detail Number
		
		// Cell Format Data Detail Date
		CellStyle styleDate = book.createCellStyle();
		
		styleDate.setAlignment(HorizontalAlignment.LEFT);
		styleDate.setBorderBottom(BorderStyle.THIN);
		styleDate.setBorderLeft(BorderStyle.THIN);
		styleDate.setBorderRight(BorderStyle.THIN);
		
		XSSFFont dateFont = book.createFont();
		
		dateFont.setFontHeightInPoints((short) 10);
		dateFont.setFontName("Arial");
		dateFont.setColor(IndexedColors.BLACK.getIndex());
		dateFont.setBold(false);
		dateFont.setItalic(false);
		
		styleDate.setFont(dateFont);
		// Cell Format Data Detail Date
		
		// Cell Format Header Title
		CellStyle styleHeaderTitle = book.createCellStyle();
		
		styleHeaderTitle.setAlignment(HorizontalAlignment.LEFT);
		styleHeaderTitle.setAlignment(HorizontalAlignment.CENTER);
		styleHeaderTitle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		Font fontHeaderTitle = book.createFont();
		
		fontHeaderTitle.setFontHeightInPoints((short) 10);
		fontHeaderTitle.setFontName("Arial");
		fontHeaderTitle.setColor(HSSFColorPredefined.BLACK.getIndex());
		fontHeaderTitle.setBold(true);
		
		styleHeaderTitle.setFont(fontHeaderTitle);
		// Cell Format Header Title
		
		// Cell Format Header Value
		CellStyle styleHeaderValue =  book.createCellStyle();
		
		styleHeaderValue.setAlignment(HorizontalAlignment.LEFT);
		styleHeaderValue.setVerticalAlignment(VerticalAlignment.CENTER);
		
		Font font = book.createFont();
		
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setColor(HSSFColorPredefined.BLACK.getIndex());
		
		styleHeaderValue.setFont(font);
		// Cell Format Header Value
		
		// Cell Format Header Label
		CellStyle styleHeaderLabel = book.createCellStyle();
		XSSFFont headerFontLabel = book.createFont();
		
		headerFontLabel.setFontHeightInPoints((short) 10);
		headerFontLabel.setFontName("Arial");
		headerFontLabel.setColor(IndexedColors.BLACK.getIndex());
		
		styleHeaderLabel.setFont(headerFontLabel);
		// Cell Format Header Label
		
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_HEADER_VALUE, styleHeaderValue);
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_HEADER_LABEL, styleHeaderLabel);
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_HEADER_TITLE, styleHeaderTitle);
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_COLUMN_HEADER, styleHeader);
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_NUMBER, styleNumber);
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_DATE, styleDate);
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_STRING, styleString);
		
		// Cell Format Column Header Number
		CellStyle styleHeaderNumber = book.createCellStyle();
		
		styleHeaderNumber.setDataFormat(fmt.getFormat("#,##0"));
		styleHeaderNumber.setAlignment(HorizontalAlignment.RIGHT);
		styleHeaderNumber.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeaderNumber.setBorderBottom(BorderStyle.MEDIUM);
		styleHeaderNumber.setBorderTop(BorderStyle.MEDIUM);
		styleHeaderNumber.setBorderLeft(BorderStyle.MEDIUM);
		styleHeaderNumber.setBorderRight(BorderStyle.MEDIUM);
		styleHeaderNumber.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		styleHeaderNumber.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFFont headerNumberFont = book.createFont();
		
		headerNumberFont.setFontHeightInPoints((short) 10);
		headerNumberFont.setFontName("Arial");
		headerNumberFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		headerNumberFont.setBold(true);

		styleHeaderNumber.setFont(headerNumberFont);
		// Cell Format Column Header Number
		
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_COLUMN_HEADER_NUMBER, styleHeaderNumber);
		
		// Cell Format Data Detail String
		CellStyle styleWrapString = book.createCellStyle();
		
		styleWrapString.setAlignment(HorizontalAlignment.LEFT);
		styleWrapString.setBorderBottom(BorderStyle.THIN);
		styleWrapString.setBorderLeft(BorderStyle.THIN);
		styleWrapString.setBorderRight(BorderStyle.THIN);
		
		XSSFFont stringWrapFont = book.createFont();
		
		stringWrapFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		styleWrapString.setFont(stringWrapFont);
		styleWrapString.setWrapText(true);
		// Cell Format Data Detail String
		
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_WRAP_STRING, styleWrapString);
		
		// Cell Format Column Header
		CellStyle styleWrapHeader = book.createCellStyle();
		
		styleWrapHeader.setVerticalAlignment(VerticalAlignment.CENTER);
		styleWrapHeader.setBorderBottom(BorderStyle.MEDIUM);
		styleWrapHeader.setBorderTop(BorderStyle.MEDIUM);
		styleWrapHeader.setBorderLeft(BorderStyle.MEDIUM);
		styleWrapHeader.setBorderRight(BorderStyle.MEDIUM);
		styleWrapHeader.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		styleWrapHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFFont headerWrapFont = book.createFont();
		
		headerWrapFont.setFontHeightInPoints((short) 10);
		headerWrapFont.setFontName("Arial");
		headerWrapFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		headerWrapFont.setBold(true);

		styleWrapHeader.setFont(headerWrapFont);
		styleWrapHeader.setWrapText(true);
		// Cell Format Column Header
		
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_COLUMN_WRAP_HEADER, styleWrapHeader);
		
		// Cell Format Data Detail String
		CellStyle styleWrapNoBottomString = book.createCellStyle();
		
		styleWrapNoBottomString.setAlignment(HorizontalAlignment.LEFT);
		styleWrapNoBottomString.setBorderLeft(BorderStyle.THIN);
		styleWrapNoBottomString.setBorderRight(BorderStyle.THIN);
		
		XSSFFont stringWrapNoBottomFont = book.createFont();
		
		stringWrapNoBottomFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		styleWrapNoBottomString.setFont(stringWrapNoBottomFont);
		styleWrapNoBottomString.setWrapText(true);
		// Cell Format Data Detail String
		
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_WRAP_NO_BOTTOM_STRING, styleWrapNoBottomString);
		
		// Cell Format Data Detail String
		CellStyle styleWrapBorderTopOnlyString = book.createCellStyle();
		
		styleWrapBorderTopOnlyString.setAlignment(HorizontalAlignment.LEFT);
		styleWrapBorderTopOnlyString.setBorderTop(BorderStyle.THIN);

		XSSFFont stringWrapBorderTopOnlyFont = book.createFont();
		
		stringWrapBorderTopOnlyFont.setColor(HSSFColorPredefined.BLACK.getIndex());
		
		styleWrapBorderTopOnlyString.setFont(stringWrapBorderTopOnlyFont);
		styleWrapBorderTopOnlyString.setWrapText(true);
		
		mapCellFormat.put(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_WRAP_BORDER_TOP_ONLY_STRING, styleWrapBorderTopOnlyString);
		
		// Cell Format Data Detail String
	}
	
	public void writeCell(XSSFSheet sheet, int row, int column, Object value, CellStyle cellStyle) throws Exception {
		Row rowTemp = sheet.getRow(row);
		
		if (rowTemp == null) {
			rowTemp = sheet.createRow(row);
		}
		
		Cell cell = rowTemp.createCell(column);
		
		cell.setCellStyle(cellStyle);
		
		if (value == null) {
			cell.setCellValue(StringUtils.EMPTY);
		} else if (value instanceof String) {
			cell.setCellValue(value != null ? (String) value : StringUtils.EMPTY);
		} else if (value instanceof Integer) {
			cell.setCellValue(value != null ? (Integer) value : 0);
		} else if (value instanceof Double) {
			cell.setCellValue(value != null ? (Double) value : 0);
		} else if (value instanceof BigDecimal) {
			cell.setCellValue(value != null ? ((BigDecimal) value).doubleValue() : 0);
		} else if (value instanceof BigInteger) {
			cell.setCellValue(value != null ? ((BigInteger) value).doubleValue() : 0);
		} else if (value instanceof Long) {
			cell.setCellValue(value != null ? (Long) value : 0);
		} else if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			cell.setCellValue(sdf.format((Date) value));
		} else if (value instanceof java.sql.Date) {
			value = new Date(((java.sql.Date) value).getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			cell.setCellValue(sdf.format((Date) value));
		} else {
			throw new Exception("Unmapped Excel value Type");
		}
	}
	
	public void writeCellDetail(XSSFSheet sheet, int row, int column, Object value, Map<String, CellStyle> mapCellFormat) throws Exception {
		CellStyle cellFormatNumber = (CellStyle) mapCellFormat.get(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_NUMBER);
		CellStyle cellFormatDate = (CellStyle) mapCellFormat.get(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_DATE);
		CellStyle cellFormatString = (CellStyle) mapCellFormat.get(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_STRING);
		
		if (value == null) {
			this.writeCell(sheet, row, column, value, cellFormatString);
		} else if (StringUtils.isNotBlank(value.toString()) && value.toString().charAt(value.toString().length() - 1) == '%') {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof String) {
			this.writeCell(sheet, row, column, value, cellFormatString);
		} else if (value instanceof Integer) {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof Double) {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof BigDecimal) {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof Long) {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof Date) {
			this.writeCell(sheet, row, column, value, cellFormatDate);
		} else if (value instanceof java.sql.Date) {
			this.writeCell(sheet, row, column, value, cellFormatDate);
		} else {
			this.writeCell(sheet, row, column, value, cellFormatString);
		}
	}

	public void writeWrapCellDetail(XSSFSheet sheet, int row, int column, Object value, Map<String, CellStyle> mapCellFormat) throws Exception {
		CellStyle cellFormatNumber = (CellStyle) mapCellFormat.get(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_NUMBER);
		CellStyle cellFormatDate = (CellStyle) mapCellFormat.get(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_DATE);
		CellStyle cellFormatString = (CellStyle) mapCellFormat.get(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_STRING);
		CellStyle cellFormatWrapString = (CellStyle) mapCellFormat.get(Constants.MAP_KEY_CELL_FORMAT_DATA_DTL_WRAP_STRING);
		
		if (value == null) {
			this.writeCell(sheet, row, column, value, cellFormatString);
		} else if (StringUtils.isNotBlank(value.toString()) && value.toString().charAt(value.toString().length() - 1) == '%') {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof String) {
			this.writeCell(sheet, row, column, value, cellFormatWrapString);
		} else if (value instanceof Integer) {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof Double) {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof BigDecimal) {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof Long) {
			this.writeCell(sheet, row, column, value, cellFormatNumber);
		} else if (value instanceof Date) {
			this.writeCell(sheet, row, column, value, cellFormatDate);
		} else if (value instanceof java.sql.Date) {
			this.writeCell(sheet, row, column, value, cellFormatDate);
		} else {
			this.writeCell(sheet, row, column, value, cellFormatString);
		}
	}

	public void writeCellTitle(XSSFSheet sheet, int mergeColumn, String title, Map<String, CellStyle> mapCellFormat) throws Exception {
		CellStyle headerTitle = mapCellFormat.get(Constants.MAP_KEY_CELL_FORMAT_HEADER_TITLE);
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		
		cell.setCellValue(title);
		cell.setCellStyle(headerTitle);
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,mergeColumn));
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CommonReportUtil.logger = logger;
	}
	
}

package com.online.module.common.util;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.online.module.common.constant.Constants;
import com.online.module.master.sistem.service.SystemService;
import com.online.module.master.sistem.model.System;

public class CallApiManager {

	private static final Logger logger = LoggerFactory.getLogger(CallApiManager.class);
	
	public static void deleteFile(String fileId, SystemService systemService, FileUtil fileUtil) throws Exception {
		if (fileId != null) {
			System sdFilePath = systemService.getSistemDataBySistemCode(Constants.SISTEM_DET_CODE_ATTACHMENT_FILE_PATH);
			String filePath = sdFilePath.getSystemValue();
			fileUtil.deleteFile(filePath);
		}
	}
	
	@SuppressWarnings("unused")
	public static void downloadFile(String fileId, String fileName, byte[] content, SystemService systemService) throws Exception {
		String data = null;
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		if (fileName != null && (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))) {
			response.setContentType("image/jpeg");
		} else if (fileName != null && fileName.endsWith(".pdf")) {
			response.setContentType("application/pdf");
		} else if (fileName != null && fileName.endsWith(".xls")) {
			response.setContentType("application/vnd.ms-excel");
		} else if (fileName != null && fileName.endsWith(".xlsx")) {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		} else if (fileName != null && fileName.endsWith(".png")) {
			response.setContentType("image/png");
		} else if (fileName != null && fileName.endsWith(".zip")) {
			response.setContentType("application/zip");
		} else if (fileName != null && fileName.endsWith(".gif")) {
			response.setContentType("image/gif");
		} else if (fileName != null && fileName.endsWith(".doc")) {
			response.setContentType("application/msword");
		} else if (fileName != null && fileName.endsWith(".docx")) {
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		} else if (fileName != null && fileName.endsWith(".ppt")) {
			response.setContentType("application/vnd.ms-powerpoint");
		}
		
		if (fileId != null) {
			System sdFilePath = systemService.getSistemDataBySistemCode(Constants.SISTEM_DET_CODE_ATTACHMENT_FILE_PATH);
			String fullPath = sdFilePath.getSystemValue().concat(fileId);
			File file = new File(fullPath);
			byte[] fileByte = Files.readAllBytes(file.toPath());
			response.setHeader("Content-Disposition", "filename=\""+fileName+"\"");
			response.getOutputStream().write(fileByte);
		} else {
			response.setHeader("Content-Disposition", "filename=\""+fileName+"\"");
			response.getOutputStream().write(content);
		}
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public static String insertFile(UploadedFile uploadedFile, String docType, SystemService systemService, Boolean isApi, FileUtil fileUtil) throws Exception {
		System sdFilePath = systemService.getSistemDataBySistemCode(Constants.SISTEM_DET_CODE_ATTACHMENT_FILE_PATH);
		UUID uuid = UUID.randomUUID();
		return fileUtil.handleFileUpload(uploadedFile.getInputStream(), uploadedFile.getFileName(), sdFilePath.getSystemValue(), uuid.toString());
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	
}

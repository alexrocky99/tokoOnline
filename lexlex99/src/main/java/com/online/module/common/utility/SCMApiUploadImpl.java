package com.online.module.common.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import org.springframework.util.Base64Utils;

import com.online.module.common.constant.Constants;
import com.online.module.common.model.UploadedFile;
import com.online.module.common.util.FileUtil;
import com.online.module.master.sistem.service.SystemService;
import com.online.module.master.sistem.model.System;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SCMApiUploadImpl implements SCMApiUpload {

	private String absoluteFilePath;
	private String docType;
	private String encodedBase64File;
	
	private SystemService systemService;
	
	private FileUtil fileUtil;
	private File file;
	private UploadedFile uploadedFile;
	
	public SCMApiUploadImpl() {
		fileUtil = FileUtil.getInstance();
	}
	
	public SCMApiUploadImpl(String absoluteFilePath, SystemService systemService, String docType) {
		super();
		this.absoluteFilePath = absoluteFilePath;
		this.systemService = systemService;
		this.docType = docType;
		fileUtil = FileUtil.getInstance();
	}
	
	@SuppressWarnings("unused")
	@Override
	public String upload() throws Exception {
		uploadedFile = new UploadedFile();
		file = new File(absoluteFilePath);
		uploadedFile.setFile(file);
		
		if (file == null) {
			throw new FileNotFoundException("File Not exists in " + absoluteFilePath);
		}
		
		uploadedFile.setFileSize(file.length());
		uploadedFile.setFileName(file.getName());
		
		byte[] byteFile = FileUtil.readBytesFromFile(file);
		encodedBase64File = Base64Utils.encodeToString(byteFile);
		uploadedFile.setEncodeBase64(encodedBase64File);
		
		String result = "";
		
		uploadedFile.setFileId(uploadToLocal());
		
		return uploadedFile.getFileId();
	}

	@Override
	public UploadedFile getAsUploadedFile() throws Exception {
		return getUploadedFile();
	}
	
	private String uploadToLocal() throws Exception {
		System filePath = systemService.getSistemDataBySistemCode(Constants.SISTEM_DET_CODE_ATTACHMENT_FILE_PATH);
		UUID uuid = UUID.randomUUID();
		return fileUtil.handleFileUpload(new FileInputStream(file), file.getName(), filePath.getSystemValue(), uuid.toString());
	}
}

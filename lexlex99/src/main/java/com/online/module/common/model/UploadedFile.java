package com.online.module.common.model;

import java.io.File;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadedFile implements Serializable {

	private static final long serialVersionUID = -1652150429847240805L;

	private String fileId;
	private String fileName;
	private String contentType;
	private Long fileSize;
	private Long fileSizeKB;
	private boolean isNew;
	private File file;
	private String encodeBase64;
	
	public UploadedFile() {
		
	}
	
	public UploadedFile(String fileId, String fileName, String contentType, Long fileSize) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
	}
	
	public UploadedFile(String fileId, String fileName, String contentType, Long fileSize, Boolean isNew) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.isNew = isNew;
	}
	
}

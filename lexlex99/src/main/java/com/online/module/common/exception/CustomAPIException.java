package com.online.module.common.exception;

import com.online.module.common.model.UploadedFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomAPIException extends RuntimeException {

	private static final long serialVersionUID = 539235337539355719L;

	private UploadedFile uploadedFile;
	
	public CustomAPIException() {
		super();
	}
	
	public CustomAPIException(String message) {
		super(message);
	}
	
	public CustomAPIException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CustomAPIException(Throwable cause) {
		super(cause);
	}
	
	public CustomAPIException(String message, UploadedFile uploadedFile) {
		super(message);
		this.uploadedFile = uploadedFile;
	}
	
}

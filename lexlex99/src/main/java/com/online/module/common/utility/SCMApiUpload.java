package com.online.module.common.utility;

import com.online.module.common.model.UploadedFile;

public interface SCMApiUpload {

	public String upload() throws Exception;
	public UploadedFile getAsUploadedFile() throws Exception;
	
}

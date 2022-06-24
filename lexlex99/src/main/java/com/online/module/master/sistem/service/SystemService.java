package com.online.module.master.sistem.service;

import com.online.module.common.paging.RetrieverDataPage;
import com.online.module.master.sistem.model.System;

public interface SystemService extends RetrieverDataPage<System> {

	System getSistemDataBySistemCode(String systemCode);
	
	public System findById(Long systemId);
	
	public void updateStatus(Long systemId, Boolean isStatus, String username);
	
}

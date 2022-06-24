package com.online.module.common.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.online.module.common.constant.Constants;
import com.online.module.lov.bean.FacesUtil;
import com.online.module.master.parameter.service.ParameterDetailService;
import com.online.module.master.sistem.service.SystemService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonBean implements Serializable {

	private static final long serialVersionUID = -764130489880627604L;
	private static final Logger logger = Logger.getLogger(CommonBean.class);
	
	protected int paging;
	
	public String maxAttachmentSize;
	public String standartAttachmentSize;
	
	public SystemService systemService;
	public ParameterDetailService parameterDetailService;
	
	protected FacesUtil facesUtil;
	
	public void init() {
		setPaging(Constants.DEFAULT_PAGING_NUMBER);
		
	}

	public static Logger getLogger() {
		return logger;
	}
}

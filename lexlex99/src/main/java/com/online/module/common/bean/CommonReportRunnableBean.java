package com.online.module.common.bean;

import org.apache.log4j.Logger;

import com.online.module.lov.bean.FacesUtil;
import com.online.module.lov.bean.RunnableFacesUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonReportRunnableBean extends CommonBean {

	private static final long serialVersionUID = 3244669612100156465L;
	private static final Logger logger = Logger.getLogger(CommonReportRunnableBean.class);
	
	public FacesUtil facesUtil;
	public RunnableFacesUtil runnableFacesUtil;
	
	public void init() {
		super.init();
		runnableFacesUtil = new RunnableFacesUtil(facesUtil.retrieveDefaultLocale());
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

}

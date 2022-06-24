package com.online.module.common.bean;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskExecutorBean {

	private static final Logger logger = Logger.getLogger(TaskExecutorBean.class);
	
	private ExecutorService executorService;
	
	private int corePoolSize = 3;
	private int maxPoolSize = 5;
	private int keepAliveTime = 30 * 60;
	
	@PostConstruct
	public void perApplicantConstructed() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		
		try {
			if (StringUtils.isNotBlank(externalContext.getInitParameter("corePoolSize"))) {
				corePoolSize = Integer.parseInt(externalContext.getInitParameter("corePoolSize"));
			}
		} catch (Exception e) {
			logger.error("Unable to get param corePoolSize from web.xml config, use default ", e);
		}
		
		try {
			if (StringUtils.isNotBlank(externalContext.getInitParameter("maxPoolSize"))) {
				maxPoolSize = Integer.parseInt(externalContext.getInitParameter("maxPoolSize"));
			}
		} catch (Exception e) {
			logger.error("Unable to get param maxPoolSize from web.xml config, use default ", e);
		}
		
		try {
			if (StringUtils.isNotBlank(externalContext.getInitParameter("keepAliveTime"))) {
				keepAliveTime = Integer.parseInt(externalContext.getInitParameter("keepAliveTime"));
			}
		} catch (Exception e) {
			logger.error("Unable to get param keepAliveTime from web.xml config, use default ", e);
		}
		
		this.createExecutor();
	}
	
	@PreDestroy
	public void preApplicationDestroyed() {
		executorService.shutdown();
		logger.info("Task Executor Service been shutdown...");
	}
	
	public synchronized void submitTask(Runnable runnable) {
		if (executorService == null) {
			this.createExecutor();
		}
		executorService.submit(runnable);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized Future<String> submitTask(Callable callable) {
		if (executorService == null) {
			this.createExecutor();
		}
		return executorService.submit(callable);
	}

	private void createExecutor() {
		executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
	}
	
}

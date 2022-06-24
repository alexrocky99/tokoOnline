package com.online.module.common.listener;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class ApplicationListener implements SystemEventListener {

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {	
	}

	@Override
	public boolean isListenerForSource(Object source) {
		boolean result = false;
		result = (source instanceof Application);
		return result;
	}

}

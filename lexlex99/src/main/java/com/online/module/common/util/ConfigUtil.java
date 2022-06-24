package com.online.module.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.online.module.lov.bean.FacesUtil;

public class ConfigUtil {

	private static ConfigUtil configUtil;
	private static Logger logger = Logger.getLogger(ConfigUtil.class);
	
	private boolean appTesting;
	
	public static ConfigUtil getInstance()	{
		if (ConfigUtil.configUtil == null) {
			ConfigUtil.configUtil = new ConfigUtil();
		}
		
		return ConfigUtil.configUtil;
	}
	
	static {
		Document doc;
		
		try {
			if (FacesContext.getCurrentInstance() != null) {
				ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				doc = ConfigUtil.parseXML(servletContext.getResourceAsStream(FacesUtil.LEXLEX99_CONFIG_FILE));
				
				ConfigUtil.getInstance().buildConfiguration(doc);
			}
		} catch (Exception e) {
			logger.error("Error when load Config file, error message : " + e.getMessage(), e);
		}
		
		doc = null;
	}
	
	public ConfigUtil() {
	}
	
	private static final Document parseXML(InputStream is) throws Exception {
		BufferedInputStream bis = null;
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			
			DocumentBuilder parser = factory.newDocumentBuilder();
			bis = new BufferedInputStream(is);
			return parser.parse(bis);
		} catch (Throwable te) {
			throw new Exception(te);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					
				}
				
				bis = null;
			}
		}
	}
	
	private final void buildConfiguration(Document doc) throws Exception {
		updateAppConfig(doc);
	}
	
	private void updateAppConfig(Document doc) {
		Node node = doc.getElementsByTagName("lexlex99-app").item(0);
		NamedNodeMap attributes = node.getAttributes();
		
		if (attributes.getNamedItem("app-testing") != null) {
			this.appTesting = "true".equalsIgnoreCase(attributes.getNamedItem("app-testing").getNodeValue());
		} else {
			this.appTesting = false;
		}
		
	}

	public boolean isAppTesting() {
		return appTesting;
	}

	public void setAppTesting(boolean appTesting) {
		this.appTesting = appTesting;
	}
	
	
}

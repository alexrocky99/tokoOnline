package com.online.module.common.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionFactoryConfigBasedOther {

	private SessionFactory sessionFactory;
	private static Configuration configuration;
	private static String _configFilePath = "/hibernate.cfg.xml";
	
	public SessionFactoryConfigBasedOther() {
		sessionFactory = new Configuration().configure(_configFilePath).buildSessionFactory();
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration configuration) {
		SessionFactoryConfigBasedOther.configuration = configuration;
	}

	public static String get_configFilePath() {
		return _configFilePath;
	}

	public static void set_configFilePath(String _configFilePath) {
		SessionFactoryConfigBasedOther._configFilePath = _configFilePath;
	}
	
}

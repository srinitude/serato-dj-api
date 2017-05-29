package org.ssldev.api.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ssldev.core.utils.Logger;
import org.ssldev.core.utils.SysInfo;

/**
 * manages app configuration.  App should not run if configuration cannot be loaded
 */
public class AppConfig {
	private final static Properties config = new Properties();
	private final static String appStorageName = ".sslapi";
	private final static String EMPTY = "";
	private String configAsString;
	private static AppConfig instance;
	
	protected static enum Property {
		ENABLE_DEBUG("enableLoggerDebug"),
		ENABLE_FINEST("enableLoggerFinest"),
		ENABLE_TRACE("enableLoggerTrace"),
		ENABLE_LOG_TO_FILE("enableLogToFile"),
		SHOW_TIME("enableLoggerTimeStamp"),
		START_GUI("startGui"),
		LOG_FILE_PATH("logFilePath"),
		DOM_FILE_PATH("domFilePath"),
		SSL_DIR_PATH("sslFolderPath");
		
		String key;
		private Property(String val) {
			key = val;
		}
	}
	
	/**
	 * load the app configuration file
	 * 
	 * @param configFileName name of the configuration file to be loaded by the classloader
	 * @throws IllegalStateException if the configuration file cannot be found or loaded
	 */
	private AppConfig(String configFileName) {
		InputStream in = AppConfig.class.getClassLoader().getResourceAsStream(configFileName);
		if (null == in) {
			throw new IllegalStateException("cannot find app configuration file " + configFileName);
		}
		
		try {
			config.load(in);
		} catch (IOException e) {
			Logger.warn(this, "could not load the app configuration file: "+configFileName+".  Will attempt to use default values instead");
		}
		
		// set default values for empty props
		defaultifyIfAbsent();
	}
	
	private void defaultifyIfAbsent() {
		// TODO these are OSX specific
		config.putIfAbsent(Property.LOG_FILE_PATH.key, SysInfo.getUserHomeLoc()+"Desktop"+File.separator+"log.txt");
		config.putIfAbsent(Property.DOM_FILE_PATH.key, SysInfo.getUserHomeLoc()+appStorageName+File.separator+"ssldom.txt");
		config.putIfAbsent(Property.SSL_DIR_PATH.key, SysInfo.getUserHomeLoc() + "Music" + File.separator + "_Serato_" + File.separator);
	}

	/**
	 * load all configuration properties found in given file
	 * @param configFileName
	 * @throws IllegalStateException if file cannot be found or loaded
	 */
	public static void load(String configFileName) {
		if(null == instance) {
			instance = new AppConfig(configFileName);
		}
	}

	public static boolean getBooleanProperty(Property prop) {
		String val = config.getProperty(prop.key);
		if(null == val) {
			Logger.warn(AppConfig.class, "could not find property "+ prop.key) ;
			return false;
		}
		
		return Boolean.parseBoolean(val);
	}
	
	public static String getStringProperty(Property prop) {
		String val = config.getProperty(prop.key);
		if(null == val) {
			Logger.warn(AppConfig.class, "could not find property "+ prop.key) ;
			return EMPTY;
		}
		
		return val;
	}

	@Override
	public String toString() {
		if(null == configAsString) {
			StringBuilder sb = new StringBuilder(getClass().getSimpleName());
			sb.append("\n").append(config);
			configAsString = sb.toString();
		}
		return configAsString;
	}
}

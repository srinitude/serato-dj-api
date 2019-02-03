package org.ssldev.api.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Collectors;

import org.ssldev.core.utils.Logger;
import org.ssldev.core.utils.SysInfo;

/**
 * manages app configuration.  App should not run if configuration cannot be loaded
 */
public class SslApiConfig {
	private final static Properties config = new Properties();
	private final static String sslApiDir = ".sslapi";
	private final static String EMPTY = "";
	private final static String defaultConfig = "sslApiDefaultConfiguration.properties";
	private String configAsString;
	public static SslApiConfig instance;
	
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
	private SslApiConfig(String configFileName) {
		// load default configuration
		InputStream defaultStream = SslApiConfig.class.getClassLoader().getResourceAsStream(defaultConfig);
		if (null == defaultStream) {
			throw new IllegalStateException("cannot find default configuration file " + defaultConfig);
		}
		try {
			config.load(defaultStream);
		} catch (IOException e) {
			Logger.warn(this, "could not load the app configuration file: "+configFileName+".  Will attempt to use default values instead");
		}
		
		// load app configuration - mappings override default values
		InputStream appConfigStream = SslApiConfig.class.getClassLoader().getResourceAsStream(configFileName);
		if (null == appConfigStream) {
			throw new IllegalStateException("cannot find app configuration file " + configFileName);
		}
		try {
			config.load(appConfigStream);
		} catch (IOException e) {
			Logger.warn(this, "could not load the app configuration file: "+configFileName+".  Will attempt to use default values instead");
		}
		
		// cleanup IO
		try {
			appConfigStream.close();
			defaultStream.close();
		} catch (IOException e) {
			Logger.error(this, "could not close the configuration IO stream due to " + e);
			e.printStackTrace();
		}
		
		// defaultify properties not set in default config
		defaultifyIfAbsent();
	}
	
	private void defaultifyIfAbsent() {
		// default paths - could not be set via default config prop file
		if(config.getProperty(Property.LOG_FILE_PATH.key) == null) {
			config.setProperty(Property.LOG_FILE_PATH.key, SysInfo.getUserHomeLoc()+sslApiDir+File.separator+"log.txt");
		}
		if(config.getProperty(Property.DOM_FILE_PATH.key) == null) {
			config.setProperty(Property.DOM_FILE_PATH.key, SysInfo.getUserHomeLoc()+sslApiDir+File.separator+"ssldom.txt");
		}
		if(config.getProperty(Property.SSL_DIR_PATH.key) == null) {
			config.setProperty(Property.SSL_DIR_PATH.key, SysInfo.getUserHomeLoc() + "Music" + File.separator + "_Serato_" + File.separator);
		}
	}

	/**
	 * load all configuration properties found in given file
	 * @param configFileName
	 * @throws IllegalStateException if file cannot be found or loaded
	 */
	public static void load(String configFileName) {
		if(null == instance) {
			instance = new SslApiConfig(configFileName);
		}
	}

	public static boolean getBooleanProperty(Property prop) {
		String val = config.getProperty(prop.key); 
		if(null == val) {
			Logger.warn(SslApiConfig.class, "could not find property "+ prop.key) ;
			return false;
		}
		
		return Boolean.parseBoolean(val);
	}
	
	public static String getStringProperty(Property prop) {
		String val = config.getProperty(prop.key);
		if(null == val) {
			Logger.warn(SslApiConfig.class, "could not find property "+ prop.key) ;
			return EMPTY;
		}
		
		return val;
	}
	
	
	@Override
	public String toString() {
		if(null == configAsString) {
			configAsString = config.getClass().getName() + System.lineSeparator();
			configAsString += config.entrySet().stream()
									 		  .map(e -> String.format( "  %-24s : [%s]", e.getKey(), e.getValue()))
									 		  .collect(Collectors.joining(System.lineSeparator()));
			
		}
		return configAsString;
	}
}

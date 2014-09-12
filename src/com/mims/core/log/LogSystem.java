package com.mims.core.log;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogSystem {

	 private static URL url = null;
	 
	/**
	 * @ÃèÊö£º.<p>
	 * 
	 * @author ÀîÍþ
	 *
	 * @Date£º2011-2-26
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		URL url = LogSystem.class.getClassLoader().getResource("com/mims/core/log/log4j.properties");
		PropertyConfigurator.configure(url);
		Logger logger = Logger.getLogger(LogSystem.class);
		logger.error("Mandy logger liwei");
	}

	
	public static Logger getLogger(Class<?> class1){
		if (url == null){
			url = LogSystem.class.getClassLoader().getResource("com/mims/core/log/log4j.properties");
			PropertyConfigurator.configure(url);
		}
		Logger logger = Logger.getLogger(LogSystem.class);
		return logger;
	}
}

package com.mims.log;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogSystem {

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
		URL url = LogSystem.class.getClassLoader().getResource("log4j.properties");
		PropertyConfigurator.configure(url);
		Logger logger = Logger.getLogger(LogSystem.class);
		logger.error("Mandy logger");
	}

}

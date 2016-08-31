package com.huice.base;



import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;



public class log {
	
	static final Logger logger = LogManager.getLogger(log.class.getName());
	
	static{
		DOMConfigurator.configure("log4j.xml");
	}
	
	/**
	 * Debug级别LOG
	 * @param msg 用户赋值，期望打印内容
	 */
	
	public synchronized static void debug(String msg){
		logger.log(log.class.getName(),	Level. DEBUG, msg, null);
	}
	
	/**
	 * Info级别LOG
	 * @param msg 用户赋值，期望打印内容
	 */
	
	public synchronized static void info(String msg){
		logger.log(log.class.getName(), Level. INFO, msg, null);
	}
	
	/**
	 * Warn级别LOG
	 * @param msg 用户赋值，期望打印内容
	 */
	
	public synchronized static void warn(String msg){
		logger.log(log.class.getName(), Level. WARN, msg ,null);
	}
	
	/**
	 * Error级别LOG
	 * @param msg 用户赋值，期望打印内容
	 */
	
	public synchronized static void error(String msg){
		logger.log(log.class.getName(), Level. ERROR, msg, null);
	}

}

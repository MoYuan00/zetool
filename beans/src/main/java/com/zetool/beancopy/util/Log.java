package com.zetool.beancopy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日志打印接口，提供日志打印接口，方便统一管理和以后接入三方日志工具
 * @author Rnti
 *
 */
public abstract class Log {
	
	public static final int ANY = 0;
	
	public static final int DEBUG = 1;
	
	public static final int INFO = 2;
	
	public static final int WORN = 3;
	
	public static final int ERROR = 4;

	/**
	 * 使用单例模式
	 * @return
	 */
	public static Log getInstance(){
		if(logger == null){
			synchronized (Log.class){
				if(logger == null){
					logger = new StdLog();// 默认使用STD打印日志
				}
			}
		}
		return logger;
	}

	/**
	 * 设置要使用的日志类型
	 * @param logger
	 */
	public static void setLogger(Log logger){
		Log.logger = logger;
		Log.info(Log.class, "use logger with:" + logger.getClass());
	}

	public static void setLEVEL(int level){
		LEVEL = level;
	}
	
	/**
	 * 当前使用的日志实现类
	 */
	private static Log logger;

	/**
	 * 标识当前日志等级
	 */
	private static int LEVEL = ANY;
	
	public static String[] LEVEL_STRING = {"any", "debug", "info", "worn", "error"};
	
	public static void any(Class<?> clazz, Object msg) {
		if(LEVEL <= ANY)
			getInstance()._any(clazz, msg);
	}
	
	
	public static void info(Class<?> clazz, Object msg) {
		if(LEVEL <= INFO)
			getInstance()._info(clazz, msg);
	}
	
	
	public static void debug(Class<?> clazz, Object msg) {
		if(LEVEL <= DEBUG)
			getInstance()._debug(clazz, msg);
	}
	
	
	public static void worn(Class<?> clazz, Object msg) {
		if(LEVEL <= WORN)
			getInstance()._worn(clazz, msg);
	}
	
	
	public static void error(Class<?> clazz, Object msg) {
		if(LEVEL <= ERROR)
			getInstance()._error(clazz, msg);
	}
	
	/*********************************
	 * 接口方法
	 */
	public String format(int level, Class<?> clazz, Object msg) {
		return String.format("%-5s %s [%s] %s", 
				LEVEL_STRING[level].toUpperCase(), 
				new SimpleDateFormat("MM/dd hh:mm:ss").format(Calendar.getInstance().getTime()), clazz.getName(), msg);
	}
	
	protected abstract void _any(Class<?> clazz, Object msg);


	protected abstract void _info(Class<?> clazz, Object msg);


	protected abstract void _debug(Class<?> clazz, Object msg);

	protected abstract void _worn(Class<?> clazz, Object msg);


	protected abstract void _error(Class<?> clazz, Object msg);
}

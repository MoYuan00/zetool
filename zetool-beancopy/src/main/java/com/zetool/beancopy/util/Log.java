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
	 * 当前使用的日志实现类
	 */
	public static Log logger = new StdLog();
	
	/**
	 * 标识当前日志等级
	 */
	public static int LEVEL = ANY;
	
	public static String[] LEVEL_STRING = {"any", "debug", "info", "worn", "error"};
	
	public static void any(Class<?> clazz, Object msg) {
		if(LEVEL <= ANY)
			logger._any(clazz, msg);
	}
	
	
	public static void info(Class<?> clazz, Object msg) {
		if(LEVEL <= INFO)
			logger._info(clazz, msg);
	}
	
	
	public static void debug(Class<?> clazz, Object msg) {
		if(LEVEL <= DEBUG)
			logger._debug(clazz, msg);
	}
	
	
	public static void worn(Class<?> clazz, Object msg) {
		if(LEVEL <= WORN)
			logger._worn(clazz, msg);
	}
	
	
	public static void error(Class<?> clazz, Object msg) {
		if(LEVEL <= ERROR)
			logger._error(clazz, msg);
	}
	
	/*********************************
	 * 接口方法
	 */
	public String format(int level, Class<?> clazz, Object msg) {
		return String.format("%-5s %s [%s] %s", 
				LEVEL_STRING[level].toUpperCase(), 
				new SimpleDateFormat("MM/dd hh:mm:ss").format(Calendar.getInstance().getTime()), clazz.getName(), msg);
	}
	
	public abstract void _any(Class<?> clazz, Object msg);
	
	
	public abstract void _info(Class<?> clazz, Object msg);
	
	
	public abstract void _debug(Class<?> clazz, Object msg);
	
	public abstract void _worn(Class<?> clazz, Object msg);
	
	
	public abstract void _error(Class<?> clazz, Object msg);
}

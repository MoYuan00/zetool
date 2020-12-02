package com.zetool.beancopy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日志打印工具类，提供日志打印接口，方便统一管理和以后接入三方日志工具
 * @author Rnti
 *
 */
public final class Log {
	private static final int ANY = 0;
	private static final int DEBUG = 1;
	private static final int INFO = 2;
	private static final int WORN = 3;
	private static final int ERROR = 4;
	private static final int LEVEL = ANY;
	
	private static final String[] LEVEL_STRING = {"any", "debug", "info", "worn", "error"};
	
	private static final String format(int level, Class<?> clazz, Object msg) {
		return String.format("%-5s %s [%s] %s", 
				LEVEL_STRING[level].toUpperCase(), 
				new SimpleDateFormat("MM/dd hh:mm:ss").format(Calendar.getInstance().getTime()), clazz.getName(), msg);
	}
	
	public static final void any(Class<?> clazz, Object msg) {
		if(LEVEL <= ANY)
			System.out.println(format(ANY, clazz, msg));
	}
	
	
	public static final void info(Class<?> clazz, Object msg) {
		if(LEVEL <= INFO)
			System.out.println(format(INFO, clazz, msg));
	}
	
	
	public static final void debug(Class<?> clazz, Object msg) {
		if(LEVEL <= DEBUG)
			System.out.println(format(DEBUG, clazz, msg));
	}
	
	
	public static final void worn(Class<?> clazz, Object msg) {
		if(LEVEL <= WORN)
			System.out.println(format(WORN, clazz, msg));
	}
	
	
	public static final void error(Class<?> clazz, Object msg) {
		if(LEVEL <= ERROR)
			System.err.println(format(ERROR, clazz, msg));
	}
}

package com.zetool.beancopy.util;

/**
 * 标准输出实现log类
 * @author loki02
 * @date 2020年12月2日
 */
public class StdLog extends Log {
	
	protected void _any(Class<?> clazz, Object msg) {
		System.out.println(format(ANY, clazz, msg));
	}


	protected void _info(Class<?> clazz, Object msg) {
		System.out.println(format(INFO, clazz, msg));
	}


	protected void _debug(Class<?> clazz, Object msg) {
		System.out.println(format(DEBUG, clazz, msg));
	}


	protected void _worn(Class<?> clazz, Object msg) {
		System.out.println(format(WORN, clazz, msg));
	}


	protected void _error(Class<?> clazz, Object msg) {
		System.err.println(format(ERROR, clazz, msg));
	}
}

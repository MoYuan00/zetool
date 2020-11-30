package com.zetool.beancopy.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


public class ClassHelper {
	
	private Class<?> clazz;
	
	public ClassHelper(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * 获取到此类中 某个注解类型的所有注解
	 * @param annClazz
	 * @return
	 */
	public  Annotation[] getAnnotations(Class<? extends Annotation> annClazz) {
		return clazz.getAnnotationsByType(annClazz);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(clazz.getName());
		sb.append(System.lineSeparator());
		sb.append("[");
		for(Field field : clazz.getDeclaredFields()) {
			sb.append(field.getName());
			sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
}

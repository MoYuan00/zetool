package com.zetool.beancopy.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import com.zetool.beancopy.checkor.FieldContext;
import com.zetool.beancopy.checkor.FieldContextBuilder;

/**
 * 
 * @author loki02
 * @date 2020年12月1日
 */
public class ClassHelper {
	
	private Class<?> clazz;
	
	private Map<String, FieldContext> fieldContextMap;
	
	public ClassHelper(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public String getClassName() {
		return clazz.getName();
	}
	
	public boolean classIsNull() {
		return clazz == null;
	}
	
	public Map<String, FieldContext> getFieldContexts() {
		if(fieldContextMap == null)
			fieldContextMap =  FieldContextBuilder.buildSimpleFieldContext(clazz);
		return fieldContextMap;
	}
	
	/**
	 * 获取到此类中 某个注解类型的所有注解
	 * @param annClazz
	 * @return
	 */
	public <T extends Annotation> T[] getAnnotations(Class<T> annClazz) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassHelper other = (ClassHelper) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.getName().equals(other.clazz.getName()))
			return false;
		return true;
	}
	
	
}

package com.zetool.beancopy.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.checkor.CopyFromFieldContextFilter;
import com.zetool.beancopy.checkor.FieldContext;
import com.zetool.beancopy.checkor.FieldContextBuilder;
import com.zetool.beancopy.util.Log;

/**
 * 封装java自带的class类，提供映射方法
 * 重写equals和hashCode方法，如果两个ClassHelper中的clazz字段的getTypeName()是相同的，那么两个类相同
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
	
	/**
	 * 获取所有字段的集合
	 * @return
	 */
	public Map<String, FieldContext> getFieldContexts() {
		if(fieldContextMap == null)
			fieldContextMap =  FieldContextBuilder.buildSimpleFieldContext(clazz);
		return fieldContextMap;
	}
	
	/**
	 * 获取需要拷贝的字段的集合
	 * @param copyFrom
	 * @return
	 */
	public Map<String, FieldContext> getFieldContextsByCopyFrom(CopyFrom copyFrom) {
		if(copyFrom.fields().length == 0) {// 默认拷贝所有属性
			Log.info(ClassHelper.class, "默认映射" + copyFrom.sourceClass().getName() + "所有属性");
			return new ClassHelper(copyFrom.sourceClass()).getFieldContexts();
		}
		return new CopyFromFieldContextFilter(copyFrom).filter(getFieldContexts());
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
		result = prime * result + ((clazz == null) ? 0 : clazz.getTypeName().hashCode());
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
		} else if (!clazz.getTypeName().equals(other.clazz.getTypeName()))
			return false;
		return true;
	}
	
	
}

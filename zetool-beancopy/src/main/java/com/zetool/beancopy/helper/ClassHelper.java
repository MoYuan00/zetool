package com.zetool.beancopy.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.util.Log;

/**
 * 封装java自带的class类，提供映射方法
 * 重写equals和hashCode方法，如果两个ClassHelper中的clazz字段的getTypeName()是相同的，那么两个类相同
 * @author loki02
 * @date 2020年12月1日
 */
public class ClassHelper<T> {
	
	private Class<T> clazz;
	
	/**
	 * 当前类绑定的对象
	 */
	private T obj;
	
	private Map<String, FieldContext> fieldContextMap;
	
	public ClassHelper(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	public ClassHelper(T obj) {
		this.obj = obj;
		this.clazz = (Class<T>) obj.getClass();
	}
	
	public String getClassName() {
		return clazz.getName();
	}
	
	public boolean classIsNull() {
		return clazz == null;
	}
	
	/**
	 * 创建Object对象实例
	 * @return
	 * @throws InstantiationException 
	 * @throws IllegalAccessException
	 */
	public T newInstance() throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}
	
	/**
	 * 获取类绑定的对象
	 * @return
	 */
	public T getBindObject() {
		return obj;
	}
	
	/**
	 * 获取所有字段的集合
	 * @return
	 */
	public Map<String, FieldContext> getFieldContexts() {
		if(fieldContextMap == null)
			if(obj == null)
				fieldContextMap =  FieldContextBuilder.buildSimpleFieldContext(clazz);
			else
				fieldContextMap =  FieldContextBuilder.buildSimpleFieldContext(obj);
		return fieldContextMap;
	}
	
	/**
	 * 获取需要拷贝的字段的集合
	 * @param copyFrom
	 * @return
	 */
	public Map<String, FieldContext> getFieldContextsByCopyFrom(CopyFrom copyFrom) {
		Map<String, FieldContext> resultMap = null;
		if(copyFrom.thisFields().length == 0) {// 默认拷贝所有属性
			Log.info(ClassHelper.class, "default, mirror all field in " + clazz);
			resultMap = getFieldContexts();
		} else {
			resultMap = new FieldContext.CopyFromFieldContextFilter(copyFrom).filter(getFieldContexts());
		}
		// 去除exceptFields中标注的字段
		for(String fieldName : copyFrom.exceptThisFields())
			resultMap.remove(fieldName);
		return resultMap;
	}
	
	/**
	 * 绑定一个对象
	 * 并更新所有字段，将所有字段也绑定对象
	 * @param obj
	 */
	public ClassHelper<T> bindObject(T obj) {
		if(obj != this.obj) {
			for(FieldContext fieldContext : getFieldContexts().values()) 
				fieldContext.setObject(obj);
			this.obj = obj;
		}
		return this;
	}
	
	/**
	 * 获取到此类中 某个注解类型的所有注解
	 * @param annClazz
	 * @return
	 */
	public <A extends Annotation> A[] getAnnotations(Class<A> annClazz) {
		return (clazz).getAnnotationsByType(annClazz);
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
		ClassHelper<?> other = (ClassHelper<?>) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.getTypeName().equals(other.clazz.getTypeName()))
			return false;
		return true;
	}
	
	
}

package com.zetool.beancopy.handler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 处理注解，提供注解信息
 * @author loki02
 * @date 2020年11月30日
 */
public class AnnotationHelper {
	
	private Annotation annotation;
	
	public AnnotationHelper(Annotation annotation) {
		this.annotation = annotation;
	}
	
	public Class<? extends Annotation> getType() {
		return annotation.annotationType();
	}
	
	/**
	 * 获取一个类上面的所有此注解
	 * @param clazz
	 * @return
	 */
	public Annotation[] getClassAnnotations(Class<?> clazz) {
		return clazz.getDeclaredAnnotationsByType(getType());
	}
	
	/**
	 * 过滤出classes中具有此注解的class
	 * @param classes
	 * @return
	 */
	public Collection<Class<?>> fillter(Collection<Class<?>> classes){
		List<Class<?>> list = new ArrayList<>(classes.size());
		for(Class<?> clazz : classes) {
			if(clazz.getDeclaredAnnotationsByType(getType()).length > 0) {
				list.add(clazz);
			}
		}
		return list;
	}
}

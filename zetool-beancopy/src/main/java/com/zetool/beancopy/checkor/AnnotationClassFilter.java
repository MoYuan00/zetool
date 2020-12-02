package com.zetool.beancopy.checkor;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 通过注解过滤类集合
 * @author loki02
 * @date 2020年11月30日
 */
public class AnnotationClassFilter implements ClassFilter {

	private Class<? extends Annotation> annotationClass;
	
	public AnnotationClassFilter(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
		if(annotationClass == null) throw new IllegalStateException();
	}
	
	@Override
	public Set<Class<?>> filter(Collection<Class<?>> classes) {
		Set<Class<?>> result = new HashSet<Class<?>>();
		for(Class<?> clazz : classes) 
			if(clazz.getDeclaredAnnotationsByType(annotationClass).length > 0)
				result.add(clazz);
		return result;
	}
}

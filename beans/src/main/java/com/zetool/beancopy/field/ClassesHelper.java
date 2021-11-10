package com.zetool.beancopy.field;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.zetool.beancopy.util.CollectionUtils;

/**
 * 管理一组class
 * @author loki02
 * @date 2020年12月2日
 */
public class ClassesHelper {
	
	Collection<Class<?>> classSet;
	
	public ClassesHelper() {}
	
	public ClassesHelper(Collection<Class<?>> calClasses) {
		this.classSet = CollectionUtils.toSet(calClasses);
	}
	
	public ClassesHelper(Class<?>[] calClasses) {
		this.classSet = CollectionUtils.toSet(calClasses);
	}
	
	/**
	 * 通过注解类型筛选出一部分Class
	 * @param <T>
	 * @param annClass
	 * @return
	 */
	public <T extends Annotation> Set<Class<?>> filterWithAnnotation(Class<T> annClass){
		Set<Class<?>> result = new HashSet<Class<?>>();
		for(Class<?> clazz : classSet)
			if(clazz.getDeclaredAnnotationsByType(annClass).length > 0)
				result.add(clazz);
		return result;
	}
	
	/**
	 * 是否包含某个类
	 * @param clazz
	 * @return
	 */
	public boolean contains(Class<?> clazz) {
		if(this.classSet == null) return false;
		return this.classSet.contains(clazz);
	}
}

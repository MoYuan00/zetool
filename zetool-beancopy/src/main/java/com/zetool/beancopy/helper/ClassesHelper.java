package com.zetool.beancopy.helper;

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
	
	Set<Class<?>> classSet;
	
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
	public <T extends Annotation> Set<Class<?>> getClassesByAnnotation(Class<T> annClass){
		return new AnnotationClassFilter(annClass).filter(classSet);
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

	public ClassesHelper setClassSet(Set<Class<?>> classSet) {
		this.classSet = classSet;
		return this;
	}
	
	
	private static class AnnotationClassFilter implements ClassFilter {
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
	
	private static interface ClassFilter {
		/**
		 * 对类进行过滤，返回过滤后的集合
		 * @param classes
		 * @return
		 */
		public Collection<Class<?>> filter(Collection<Class<?>> classes);
	}
}

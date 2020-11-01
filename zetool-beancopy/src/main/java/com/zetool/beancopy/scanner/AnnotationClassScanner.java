package util.reflex.anntation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.copy.array.ArrayUtils;

/**
 * 注解扫描器
 * @author Rnti
 *
 */
public class AnnotationClassScanner {
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Annotation> getAnnotations(Class<?> clazz){
		return ArrayUtils.toList(clazz.getDeclaredAnnotations());
	}
	
	/**
	 * 获取类下面的所有属性的注解，返回一个map<Field, List<Annotation>>
	 * @param clazz
	 * @return
	 */
	public static Map<Field, List<Annotation>> getFieldsAnnotations(Class<?> clazz){
		Map<Field, List<Annotation>> map = new HashMap<>();
		for(Field field : clazz.getDeclaredFields()) 
			map.put(field, getFieldAnnotations(field));
		return map;
	}
	/**
	 * 获取一个属性的注解，返回一个List<Annotation>
	 * @param clazz
	 * @return
	 */
	public static List<Annotation> getFieldAnnotations(Field field){
		field.setAccessible(true);
		return ArrayUtils.toList(field.getDeclaredAnnotations());
	}
	/**
	 * 获取一个方法的所有注解，返回一个List<Annotation>
	 * @param method
	 * @return
	 */
	public static List<Annotation> getMethodAnnotations(Method method){
		method.setAccessible(true);
		return ArrayUtils.toList(method.getDeclaredAnnotations());
	}
	/**
	 * 获取类下面的所有方法的注解，返回一个map<Field, List<Annotation>>
	 * @param clazz
	 * @return
	 */
	public static Map<Method, List<Annotation>> getMethodsAnnotations(Class<?> clazz){
		Map<Method, List<Annotation>> map = new HashMap<>();
		for(Method method : clazz.getDeclaredMethods()) 
			map.put(method, getMethodAnnotations(method));
		return map;
	}
	
}

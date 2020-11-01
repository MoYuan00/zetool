package util.reflex.anntation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.copy.array.ArrayUtils;

/**
 * ע��ɨ����
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
	 * ��ȡ��������������Ե�ע�⣬����һ��map<Field, List<Annotation>>
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
	 * ��ȡһ�����Ե�ע�⣬����һ��List<Annotation>
	 * @param clazz
	 * @return
	 */
	public static List<Annotation> getFieldAnnotations(Field field){
		field.setAccessible(true);
		return ArrayUtils.toList(field.getDeclaredAnnotations());
	}
	/**
	 * ��ȡһ������������ע�⣬����һ��List<Annotation>
	 * @param method
	 * @return
	 */
	public static List<Annotation> getMethodAnnotations(Method method){
		method.setAccessible(true);
		return ArrayUtils.toList(method.getDeclaredAnnotations());
	}
	/**
	 * ��ȡ����������з�����ע�⣬����һ��map<Field, List<Annotation>>
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

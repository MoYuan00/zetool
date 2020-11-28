package com.zetool.beancopy.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.annotation.CopyTo;
import com.zetool.beancopy.checkor.FieldInfo;


public class AnnotationUtil {
	/**
	 * 获取到sourceClass需要拷贝字段的集合
	 * @param sourceClass
	 * @param bCopyFormA
	 * @return List<Field> b需要拷贝字段的集合
	 */
	public static List<Field> getNeedCopyList(Class<?> sourceClass
			, CopyTo sourceCopyToTarget){
		return getNeedCopyList(Arrays.asList(sourceClass.getDeclaredFields()), sourceCopyToTarget);
	}
	/**
	 * 获取到sourceField需要拷贝字段的集合
	 * @param sourceField
	 * @param bCopyFormA
	 * @return List<Field> b需要拷贝字段的集合
	 */
	public static List<Field> getNeedCopyList(List<Field> sourceField
			, CopyTo sourceCopyToTarget){
		assert sourceField != null && sourceCopyToTarget != null;
		Set<String> needCopyFieldStringSet = new HashSet<>(
				Arrays.asList(sourceCopyToTarget.sourceField()));
		return sourceField.stream().filter(
				(field)->{return needCopyFieldStringSet.contains(field.getName());}// 如果要拷贝字段中包含 field就保留
				)
				.collect(Collectors.toList());
	}
	
	
	
	
	/**
	 * 获取到b需要拷贝字段的集合
	 * @param targetClass
	 * @param targetCopyFromSource
	 * @return List<Field> b需要拷贝字段的集合
	 */
	public static Set<FieldInfo> getNeedCopyList(Class<?> targetClass
			, CopyFrom targetCopyFromSource){
		return getNeedCopyList(targetClass.getDeclaredFields(), targetCopyFromSource);
	}
	
	/**
	 * 获取到targetFieldList需要拷贝字段的集合
	 * @param targetFieldList
	 * @param bCopyFormA
	 * @return List<Field> targetFieldList需要拷贝字段的集合
	 */
	public static Set<FieldInfo> getNeedCopyList(Field[] targetFieldList
			, CopyFrom bCopyFromA){
		assert targetFieldList != null;
		return getNeedCopyList(CollectionUtils.toSet(targetFieldList), bCopyFromA);
	}
	/**
	 * 获取到b需要拷贝字段的集合
	 * @param bFieldList
	 * @param bCopyFormA
	 * @return List<Field> b需要拷贝字段的集合
	 */
	public static Set<FieldInfo> getNeedCopyList(Set<Field> bFieldList
			, CopyFrom targetCopyFromSource){
		Set<String> needCopyFieldStringSet = new HashSet<>(Arrays.asList(targetCopyFromSource.fields()));
		return bFieldList.stream().filter(
				(field)->{return needCopyFieldStringSet.contains(field.getName());}// 如果要拷贝字段中包含 field就保留
				)
				.map((t)-> new FieldInfo(t))
				.collect(Collectors.toSet());
	}
	/**
	 * 获取类上面指定的注解， 没有返回null
	 * @param clazz
	 * @return
	 */
	public static Annotation getAnnotation(Class<?> clazz, Class<? extends Annotation> annClass){
		Log.debug(AnnotationUtil.class, annClass.getName());
		for(Annotation i : AnnotationUtil.getAnnotations(clazz)) {
			Log.debug(AnnotationUtil.class, i.annotationType().getName().toString());
			if(i.annotationType().getName().equals(annClass.getName())) {
				Log.debug(AnnotationUtil.class, "get not null");
				return i;
			}
		}
		return null;
	}
	/**
	 * 获取类上面的注解
	 * @param clazz
	 * @return
	 */
	public static List<Annotation> getAnnotations(Class<?> clazz){
		return CollectionUtils.toList(clazz.getDeclaredAnnotations());
	}
	/**
	 * 获取类中的所有属性的注解，返回一个map<Field, List<Annotation>>
	 * @param clazz
	 * @return
	 */
	public static Map<Field, List<Annotation>> getFieldsAnnotations(Class<?> clazz){
		Map<Field, List<Annotation>> map = new HashMap<>();
		for(Field field : clazz.getDeclaredFields()) 
			map.put(field, AnnotationUtil.getFieldAnnotations(field));
		return map;
	}
	/**
	 * 获取类中所有属性的注解，返回一个map<String, List<Annotation>>
	 * @param clazz
	 * @return
	 */
	public static Map<String, List<Annotation>> getFieldNamesAnnotations(Class<?> clazz){
		Map<String, List<Annotation>> map = new HashMap<>();
		for(Field field : clazz.getDeclaredFields()) 
			map.put(field.getName(), AnnotationUtil.getFieldAnnotations(field));
		return map;
	}
	/**
	 * 获取一个属性的注解，返回一个List<Annotation>
	 * @param clazz
	 * @return
	 */
	public static List<Annotation> getFieldAnnotations(Field field){
		field.setAccessible(true);
		return CollectionUtils.toList(field.getDeclaredAnnotations());
	}
	/**
	 * 获取一个方法的所有注解，返回一个List<Annotation>
	 * @param method
	 * @return
	 */
	public static List<Annotation> getMethodAnnotations(Method method){
		method.setAccessible(true);
		return CollectionUtils.toList(method.getDeclaredAnnotations());
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

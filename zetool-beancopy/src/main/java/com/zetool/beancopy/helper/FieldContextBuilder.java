package com.zetool.beancopy.helper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 构造一个FieldContext集合
 * @author loki02
 * @date 2020年12月2日
 */
public class FieldContextBuilder {
	
	/**
	 * 构造一个FieldName:FieldContext的map集合。通过clazz中的所有定义的属性
	 * @param clazz
	 * @return
	 */
	public static Map<String, FieldContent> buildSimpleFieldContext(Class<?> clazz){
		Map<String, FieldContent> fieldMap = new HashMap<>();
		for (Field field : clazz.getDeclaredFields()) 
			fieldMap.put(field.getName(), new SimpleFieldContent(field));
		return fieldMap;
	}
	
	/**
	 * 构造一个FieldName:FieldContext的map集合。通过obj中的所有定义的属性, 并绑定obj 
	 * @param clazz
	 * @return
	 */
	public static Map<String, FieldContent> buildSimpleFieldContext(Object obj){
		Map<String, FieldContent> fieldMap = new HashMap<>();
		for (Field field : obj.getClass().getDeclaredFields()) 
			fieldMap.put(field.getName(), new SimpleFieldContent(field, obj));
		return fieldMap;
	}
}

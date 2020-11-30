package com.zetool.beancopy.checkor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldContextBuilder {
	
	/**
	 * 构造一个FieldName:FieldContext的map集合。通过clazz中的所有定义的属性
	 * @param clazz
	 * @return
	 */
	public static Map<String, FieldContext> buildSimpleFieldContext(Class<?> clazz){
		Map<String, FieldContext> fieldMap = new HashMap<>();
		for (Field field : clazz.getDeclaredFields()) 
			fieldMap.put(field.getName(), new SimpleFieldContext(field));
		return fieldMap;
	}
	
	/**
	 * 构造一个FieldName:FieldContext的map集合。通过obj中的所有定义的属性, 并绑定obj 
	 * @param clazz
	 * @return
	 */
	public static Map<String, FieldContext> buildSimpleFieldContext(Object obj){
		Map<String, FieldContext> fieldMap = new HashMap<>();
		for (Field field : obj.getClass().getDeclaredFields()) 
			fieldMap.put(field.getName(), new SimpleFieldContext(field, obj));
		return fieldMap;
	}
}

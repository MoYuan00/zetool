package com.zetool.beancopy.field;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 构造一个FieldHelper集合
 * @author loki02
 * @date 2020年12月2日
 */
public class FieldHelperBuilder {
	
	/**
	 * 构造一个FieldName:FieldContext的map集合。通过clazz中的所有定义的属性
	 * @param clazz
	 * @return
	 */
	public static Map<String, FieldHelper> buildFieldHelper(Class<?> clazz){
		Map<String, FieldHelper> fieldMap = new HashMap<>();
		for (Field field : ClassHelper.getAllField(clazz))
			fieldMap.put(field.getName(), new TypeCloneFieldHelper(field));
		return fieldMap;
	}
	
	/**
	 * 构造一个FieldName:FieldContext的map集合。通过obj中的所有定义的属性, 并绑定obj 
	 * @param obj
	 * @return
	 */
	public static Map<String, FieldHelper> buildFieldHelper(Object obj){
		Map<String, FieldHelper> fieldMap = new HashMap<>();
		for (Field field : ClassHelper.getAllField(obj.getClass()))
			fieldMap.put(field.getName(), new TypeCloneFieldHelper(field, obj));
		return fieldMap;
	}


}

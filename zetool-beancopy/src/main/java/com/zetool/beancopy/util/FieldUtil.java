package com.zetool.beancopy.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldUtil {
	/**
	 * 将sourceObj对象个sourceField字段值， 拷贝给targetObj对象的targetField字段
	 * @param sourceObj 源对象
	 * @param sourceField 源字段
	 * @param targetObj 目标对象
	 * @param targetField 目标字段
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void copy(Object sourceObj, Field sourceField, 
			Object targetObj, Field targetField) throws IllegalArgumentException, IllegalAccessException {
		sourceField.setAccessible(true);				// 设置为可以访问
		targetField.setAccessible(true);
		if(Modifier.isFinal(sourceField.getModifiers())){// final 类型变量
			return;
		}
		// 这里只是获取了值，并没有真正的拷贝一份（可能需要递归克隆拷贝）
		Object fieldVal = sourceField.get(sourceObj);	// 获取指定sourceObj对象的sourceField字段的值
		System.out.println("设置字段值：" + targetField.getName() + " set to" + fieldVal);
		targetField.set(targetObj, fieldVal);			// 设置targetField对象值
	}
	/**
	 * 显示一个对象的所有字段值
	 * @param obj
	 */
	public static void showFieldsValue(Object obj) {
		if(obj == null) throw new NullPointerException();
		System.out.print(obj.getClass() + ":{");
		getFields(obj).forEach((field)->{
			try {
				field.setAccessible(true);
				System.out.print(field.getName() + ":" + field.get(obj) + ", ");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		System.out.println("}");
	}
	/**
	 * 获取全部的属性（字段）
	 * @param clazz 类型
	 * @return
	 */
	public static List<Field> getFields(Object obj) {
		if(obj == null) throw new NullPointerException();
		return getFields(obj.getClass());
	}
	/**
	 * 获取全部的属性（字段）
	 * @param clazz 类型
	 * @return
	 */
	public static Stream<Field> getFieldsAsStream(Object obj) {
		if(obj == null) throw new NullPointerException();
		return getFieldsAsStream(obj.getClass());
	}
	/**
	 * 获取全部的属性（字段）
	 * @param clazz 类型
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
//		while(clazz.getSuperclass() != null) {// 可能需要父类的字段也添加
//			fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
//			clazz = clazz.getSuperclass();
//		}
		return fieldList;
	}
	/**
	 * 获取全部的属性（字段）
	 * @param clazz 类型
	 * @return
	 */
	public static Stream<Field> getFieldsAsStream(Class<?> clazz) {
		return Arrays.asList(clazz.getDeclaredFields()).stream();
	}
	/**
	 * 过滤出具有指定注解集合**全部注解**的字段
	 * @param fieldList 待过滤字段集合
	 * @param as 注解
	 * @return
	 */
	public static List<Field> filterFields(List<Field> fieldList, Annotation[] as) {
		return fieldList.stream().filter((field)->{
			List<Annotation> fAs = Arrays.asList(field.getDeclaredAnnotations());// 当前字段的注解
			int i = 0, len = as.length;
			for(; i < len; i++) {// 检查是否所有要包含的字段 都包含
				final int t = i;
				if(!fAs.stream().anyMatch( 
						(a)->{ return a.getClass().isAnnotationPresent(as[t].getClass()); }
						)
				) {
					break;// 不包含
				}
			}
			return i == len;
		}).collect(Collectors.toList());
	}
	
}

package com.zetool.beancopy.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.annotation.CopyTo;


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
	public static List<Field> getNeedCopyList(Class<?> targetClass
			, CopyFrom targetCopyFromSource){
		return getNeedCopyList(targetClass.getDeclaredFields(), targetCopyFromSource);
	}
	
	/**
	 * 获取到targetFieldList需要拷贝字段的集合
	 * @param targetFieldList
	 * @param bCopyFormA
	 * @return List<Field> targetFieldList需要拷贝字段的集合
	 */
	public static List<Field> getNeedCopyList(Field[] targetFieldList
			, CopyFrom bCopyFromA){
		assert targetFieldList != null;
		return getNeedCopyList(Arrays.asList(targetFieldList), bCopyFromA);
	}
	/**
	 * 获取到b需要拷贝字段的集合
	 * @param bFieldList
	 * @param bCopyFormA
	 * @return List<Field> b需要拷贝字段的集合
	 */
	public static List<Field> getNeedCopyList(List<Field> bFieldList
			, CopyFrom targetCopyFromSource){
		Set<String> needCopyFieldStringSet = new HashSet<>(Arrays.asList(targetCopyFromSource.fields()));
		return bFieldList.stream().filter(
				(field)->{return needCopyFieldStringSet.contains(field.getName());}// 如果要拷贝字段中包含 field就保留
				)
				.collect(Collectors.toList());
	}
}

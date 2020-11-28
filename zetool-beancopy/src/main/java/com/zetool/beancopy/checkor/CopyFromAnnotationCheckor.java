package com.zetool.beancopy.checkor;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.util.AnnotationUtil;
import com.zetool.beancopy.util.FieldUtil;
import com.zetool.beancopy.util.Log;

/**
 * 判断注解是否注解正确
 * @author Rnti
 *
 */
public class CopyFromAnnotationCheckor {
	/**
	 * 判断注解正确性
	 * 1。 注解中的属性，toClass中是否全有
	 * 2。注解类是不是传入的fromClass -> 不是则抛出参数错误异常
	 * 3.注解中的属性，fromClass中是否全有
	 * @return 若以上3个全满足，返回true
	 */
	public static boolean check(Class<?> fromClass, Class<?> toClass, CopyFrom ann) {
		// 获取target 和 source 中的属性集合
		Set<String> fromFields = FieldUtil.getFields(fromClass).stream().map((t)->t.getField().getName())
																	.collect(Collectors.toSet());
		Set<String> toFields = FieldUtil.getFields(toClass).stream().map((t)->t.getField().getName())
																	.collect(Collectors.toSet());
		// 获取注解中标注的 属性信息
		String[] fieldInfo = ann.fields();
		// 1. 判断注解中的属性，自己类是不是都有
		for(String fieldName : fieldInfo) {
			if(!toFields.contains(fieldName)) {
				Log.error(CopyFromAnnotationCheckor.class, "本类" + toClass.getName() + "中缺少注解中的属性：" + fieldName);
				return false;
			}
		}
		// 2. 获取注解标注的类是不是这个类
		if(!ann.fromClass().equals(fromClass)) // 如果传入的fromclass 和 注解上的不一致，抛出参数传入错误异常
			throw new IllegalArgumentException(toClass.getName() + " copy from " + fromClass.getClass());
		// 3. 判断是不是全部存在于目标类中
		for(String fieldName : fieldInfo) {
			if(!fromFields.contains(fieldName)) {
				Log.error(CopyFromAnnotationCheckor.class, "匹配失败，" + fieldName + "不在" + fromClass.getName() + "中");
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断所有的类是否注解关联正确
	 * @param classSet 注解类，和被注解的类（target和source）
	 * @return
	 */
	public static boolean check(Set<Class<?>> classSet) {
		Log.info(CopyFromAnnotationCheckor.class, "开始检查！");
		// 获取所有 具有注解的类
		HashMap<Class<?>, CopyFrom> classAnnMap = new HashMap<>();
		Set<Class<?>> ncClassSet = classSet.stream().filter((c)->{
			Log.info(CopyFromAnnotationCheckor.class, "检查：" + c);
			CopyFrom a = (CopyFrom)AnnotationUtil.getAnnotation(c, CopyFrom.class);
			if(a != null) {
				Log.info(CopyFromAnnotationCheckor.class, "获取到注解: " + a.annotationType().getName());
				classAnnMap.put(c, a);
				return true;
			}
			return false;
		}).collect(Collectors.toSet());
		Log.info(CopyFromAnnotationCheckor.class, "被注解的类共" + ncClassSet.size() + "个！");
		
		// 1. 判断所有注解中的类是否存在
		ncClassSet.forEach((c)->{
			if(!classSet.contains(classAnnMap.get(c).fromClass())) {
				throw new RuntimeException("注解中的类不存在" + 
						classAnnMap.get(c).fromClass());
			}
		});
		
		// 2. 判断所有的注解是否匹配
		ncClassSet.forEach((c)->{
			CopyFrom ann = classAnnMap.get(c);
			if(!check(ann.fromClass(), c, classAnnMap.get(c))) {
				throw new RuntimeException(c.getName() + "的注解不匹配！");
			}
		});
		Log.info(CopyFromAnnotationCheckor.class, "通过检查！");
		return true;
	}
}

package com.zetool.beancopy.executor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.util.AnnotationUtil;
import com.zetool.beancopy.util.FieldUtil;


/**
 * 不同类之间的拷贝执行者
 * @author Rnti
 *
 */
public class UnequalsObjectCopyExecutor {
	/**
	 * 1.实例化一个B（需要无参数构造）
	 * 2.通过类A的实例以及，B的类型，将A的某些字段值拷贝给B
	 * 3.这些字段值由 CopyForm 提供并表示
	 * @param <T>
	 * @param sourceObj
	 * @param targetClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <Tsource,Ttarget>Ttarget copyForm(Tsource sourceObj, Class<Ttarget> targetClass) throws InstantiationException, IllegalAccessException {
		if(sourceObj == null || targetClass == null) throw new NullPointerException();
		final Ttarget targetObj = targetClass.newInstance();
		List<Field> targetFieldList =  FieldUtil.getFields(targetObj);				// 获取targetClass的属性
		CopyFrom bCopyFrom = targetClass.getDeclaredAnnotation(CopyFrom.class);		// 获取到映射注解
		if(bCopyFrom != null) {
			// 获取到映射集合 b -> a	// b需要从a的哪个字段拷贝
			targetFieldList = AnnotationUtil.getNeedCopyList(targetFieldList, bCopyFrom);
			System.out.println("需要拷贝字段集合:" + Arrays.toString(targetFieldList.toArray()));
			
			targetFieldList.forEach((targetField)->{// 给每个对象赋值
				Field sourceField = null;
				try {
					sourceField = sourceObj.getClass().getDeclaredField(targetField.getName());//通过targetField属性名获取sourceField属性
				} catch (NoSuchFieldException | SecurityException e1) {
					e1.printStackTrace();
				}
				// TODO 这里不判断是否找到
				System.out.println("找到对应字段:" + sourceField.getName());
				try {
					FieldUtil.copy(sourceObj, sourceField, targetObj, targetField); // 拷贝字段
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		}
		return targetObj;
	}
}

package com.zetool.beancopy.handler;

import java.util.Map;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.checkor.CopyFromFieldContextFilter;
import com.zetool.beancopy.checkor.FieldContext;
import com.zetool.beancopy.checkor.FieldContextBuilder;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;


/**
 * 不同类之间的拷贝执行者
 * @author Rnti
 *
 */
class UnequalsObjectCopyExecutor {
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
		Log.info(UnequalsObjectCopyExecutor.class, "<跨类拷贝>" + sourceObj.getClass().getName() + " to " + targetClass.getName());
		final Ttarget targetObj = targetClass.newInstance();
		Map<String, FieldContext> targetFieldMap = FieldContextBuilder.buildSimpleFieldContext(targetObj); // 获取targetClass的属性
		Map<String, FieldContext> sourceFieldMap = FieldContextBuilder.buildSimpleFieldContext(sourceObj);
		Log.info(UnequalsObjectCopyExecutor.class, ("可拷贝字段集合:" + CollectionUtils.toString(sourceFieldMap.keySet())));
		CopyFrom[] bCopyFrom = (CopyFrom[])new ClassHelper(targetClass).getAnnotations(CopyFrom.class);		// 获取到映射注解
		// TODO 这里可能有多个注解
		if(bCopyFrom.length > 0) {
			// 获取到映射集合 b -> a	// b需要从a的哪个字段拷贝
			targetFieldMap = new CopyFromFieldContextFilter(bCopyFrom[0]).filter(targetFieldMap);
			Log.info(UnequalsObjectCopyExecutor.class, ("需要拷贝字段集合:" + CollectionUtils.toString(targetFieldMap.keySet())));
			targetFieldMap.forEach((name, targetField)->{// 给每个对象赋值
				Log.info(UnequalsObjectCopyExecutor.class, "字段：" + name);
				FieldContext sourceField = sourceFieldMap.get(name);
				Object obj = sourceField.cloneValue();
				Log.info(UnequalsObjectCopyExecutor.class, "值：" + obj);
				targetField.setValue(obj);
			});
		}
		return targetObj;
	}
}

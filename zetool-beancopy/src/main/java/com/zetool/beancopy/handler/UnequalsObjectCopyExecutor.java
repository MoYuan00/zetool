package com.zetool.beancopy.handler;

import java.util.Map;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.helper.ClassHelper;
import com.zetool.beancopy.helper.FieldContext;
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
	public static <Tsource,Ttarget>Ttarget copyFrom(Tsource sourceObj, Class<Ttarget> targetClass) throws InstantiationException, IllegalAccessException {
		if(sourceObj == null || targetClass == null) throw new NullPointerException();
		Log.debug(UnequalsObjectCopyExecutor.class, "<跨类拷贝>" + sourceObj.getClass().getName() + " to " + targetClass.getName());
		ClassHelper<Ttarget> targetClassHelper = new ClassHelper<Ttarget>(targetClass);
		ClassHelper<Tsource> sourceClassHelper = new ClassHelper<Tsource>(sourceObj);
		// 获取targetClass的属性
		Map<String, FieldContext> targetFieldMap = targetClassHelper.bindObject(targetClassHelper.newInstance()).getFieldContexts(); 
		Map<String, FieldContext> sourceFieldMap = sourceClassHelper.bindObject(sourceObj).getFieldContexts();
		Log.debug(UnequalsObjectCopyExecutor.class, ("可拷贝字段集合:" + CollectionUtils.toString(sourceFieldMap.keySet())));
		CopyFrom[] targetCopyFroms = targetClassHelper.getAnnotations(CopyFrom.class);		// 获取到映射注解
		CopyFrom targetCopyFrom = CollectionUtils.findFirst(targetCopyFroms, 
										(c) -> c.sourceClass().equals(sourceObj.getClass()));
		if(targetCopyFrom == null) {
			Log.error(UnequalsObjectCopyExecutor.class, "没有找到相应注解：" + sourceObj.getClass());
			throw new IllegalStateException("不能从" + sourceObj.getClass() + " 拷贝到 " + targetClass);
		}
		// 获取到映射集合 b -> a	// b需要从a的哪个字段拷贝
		targetFieldMap = targetClassHelper.getFieldContextsByCopyFrom(targetCopyFrom);
		Log.info(UnequalsObjectCopyExecutor.class, ("需要拷贝字段集合:" + CollectionUtils.toString(targetFieldMap.keySet())));
		targetFieldMap.forEach((name, targetField)->{// 给每个对象赋值
			Log.debug(UnequalsObjectCopyExecutor.class, "字段名：" + targetField.getName());
			FieldContext sourceField = sourceFieldMap.get(name);
			Object obj = sourceField.cloneValue();
			Log.debug(UnequalsObjectCopyExecutor.class, "值：" + FieldContext.toString(obj));
			targetField.setValue(obj);
		});
		return targetClassHelper.getBindObject();
	}
}

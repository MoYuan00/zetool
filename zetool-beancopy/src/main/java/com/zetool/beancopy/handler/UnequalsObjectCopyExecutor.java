package com.zetool.beancopy.handler;

import java.util.Collection;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.checkor.CopyPair;
import com.zetool.beancopy.checkor.FieldContextPair;
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
		CopyFrom targetCopyFrom = CollectionUtils.findFirst(targetClassHelper.getAnnotations(CopyFrom.class), 
												(c) -> c.sourceClass().equals(sourceObj.getClass()));// 获取到映射注解
		if(targetCopyFrom == null) {
			Log.error(UnequalsObjectCopyExecutor.class, "没有找到相应注解：" + sourceObj.getClass());
			throw new IllegalStateException("不能从" + sourceObj.getClass() + " 拷贝到 " + targetClass);
		}
		CopyPair<Tsource, Ttarget> copyPair = new CopyPair<>(new ClassHelper<Tsource>(sourceObj), targetClassHelper, targetCopyFrom);
		copyPair.bindSourceObject(sourceObj);
		copyPair.bindTargetObject(targetClassHelper.newInstance());
		// 获取targetClass的属性
		Log.debug(UnequalsObjectCopyExecutor.class, ("可拷贝字段集合:" + CollectionUtils.toString(copyPair.getSourceFieldMap().keySet())));
		// 获取映射集合
		Collection<FieldContextPair> fieldContextPairs = FieldContextPairBuilderFactory.getBuilder(copyPair.getMirrorType()).getFieldContexPairs(copyPair);
		// 执行拷贝
		fieldContextPairs.forEach((pair)->{
			Log.debug(UnequalsObjectCopyExecutor.class, "字段名：" + pair.getTargetFC().getName());
			Object obj = pair.getSourceFC().cloneValue();// TODO 把拷贝应该也得抽象出来
			pair.getTargetFC().setValue(obj);
			Log.debug(UnequalsObjectCopyExecutor.class, "值：" + FieldContext.toString(obj));
		});
		return targetClassHelper.getBindObject();
	}
}

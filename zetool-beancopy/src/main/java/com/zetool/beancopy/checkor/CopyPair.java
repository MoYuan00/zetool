package com.zetool.beancopy.checkor;

import java.util.Collection;
import java.util.Map;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.handler.FieldContextPairBuilderFactory;
import com.zetool.beancopy.helper.ClassHelper;
import com.zetool.beancopy.helper.FieldContext;
import com.zetool.beancopy.util.Log;

/**
 * 检查一个映射
 * @author loki02
 * @date 2020年12月1日
 */
public class CopyPair<S, T>{
	/**
	 * 源 即 target类上注解标注的类
	 */
	private ClassHelper<S> sourceClazz;
	/**
	 * 目标类
	 */
	private ClassHelper<T> targetClazz;
	
	/**
	 * 目标类上的注解
	 */
	private CopyFrom copyFrom;
	
	/**
	 * 绑定源对象
	 * @param obj
	 * @return
	 */
	public CopyPair<S, T> bindSourceObject(S obj) {
		this.sourceClazz.bindObject(obj);
		return this;
	}
	
	/**
	 * 绑定目标对象
	 * @param obj
	 * @return
	 */
	public CopyPair<S, T> bindTargetObject(T obj) {
		this.targetClazz.bindObject(obj);
		return this;
	}
	
	/**
	 * 获取源 可拷贝字段集合
	 * @return
	 */
	public Map<String, FieldContext> getSourceFieldMap(){
		return sourceClazz.getFieldContexts();
	}
	
	/**
	 * 获取目标 需要拷贝的字段集合
	 * @return
	 */
	public Map<String, FieldContext> getTargetFieldMap(){
		return targetClazz.getFieldContextsByCopyFrom(copyFrom);
	}
	
	
	
	public CopyPair(ClassHelper<S> sourceClazz, ClassHelper<T> targetClazz, CopyFrom copyFrom) {
		super();
		this.sourceClazz = sourceClazz;
		this.targetClazz = targetClazz;
		this.copyFrom = copyFrom;
	}
	/**
	 * 检查source 和 target对于注解copyfrom是否匹配
	 * @return
	 */
	public boolean check() {
		if(sourceClazz.classIsNull() || targetClazz.classIsNull() || copyFrom == null) throw new NullPointerException();
		if(!checkTargetFields()) return false;
		Log.info(CopyPair.class, "检查source:" + sourceClazz.getClassName() + " <- target" + targetClazz.getClassName());
		Collection<FieldContextPair> fieldPairList = FieldContextPairBuilderFactory.getBuilder().getFieldContexPairs(this);
		if(!checkTypeIsEquals(fieldPairList)) return false;
		return true;
	}
	
	/**
	 * 判断source和target对应的类型是否相同
	 * @return
	 */
	private boolean checkTypeIsEquals(Collection<FieldContextPair> pairList) {
		for(FieldContextPair pari : pairList) {
			if(pari.isMatch()) {
				Log.info(CopyPair.class, pari.getTargetFC().getName() + " is " + pari.getTargetFC().getType() 
							+ "\n" + pari.getSourceFC().getName() + " is " + pari.getSourceFC().getType());
			} else {
				Log.error(CopyPair.class, "类型不兼容：" + pari.getTargetFC().getName() + " is " + pari.getTargetFC().getType() 
							+ "\n" + pari.getSourceFC().getName() + " is " + pari.getSourceFC().getType());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查注解中的所有属性 targetClazz中是否都存在
	 * 检查是否为final类型，如果是抛出异常
	 * 检查是否为static类型，如果是警告
	 * @return
	 */
	private boolean checkTargetFields() {
		Log.info(CopyPair.class, "检查注解中的所有属性target本身是否存在:[" + targetClazz.getClassName() + "]");
		Map<String, FieldContext> targetFieldMap = targetClazz.getFieldContexts();
		for(String name : copyFrom.fields()) {
			FieldContext fieldContext = targetFieldMap.get(name);
			if(fieldContext != null) {
				Log.info(CopyPair.class, "注解中的属性[" + name +  "]存在target[" + targetClazz.getClassName() + "]中");
				if(fieldContext.isFinal()) {
					Log.error(CopyPair.class, targetClazz.getClassName() + "注解中的属性[" + name +  "]是 final 类型");
					throw new IllegalStateException(targetClazz.getClassName() + "注解中的属性[" + name +  "]是 final 类型");
				}
				if(fieldContext.isStatic()) {
					Log.worn(CopyPair.class, targetClazz.getClassName() + "注解中的属性[" + name +  "]是 static 类型");
				}
			}else {
				Log.error(CopyPair.class, "注解中的属性[" + name +  "]不存在target[" + targetClazz.getClassName() + "]中");
				return false;
			}
		}
		return true;
	}
}
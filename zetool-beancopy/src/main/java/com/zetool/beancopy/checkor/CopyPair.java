package com.zetool.beancopy.checkor;

import java.util.Collection;
import java.util.Map;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.annotation.CopyFrom.MirrorType;
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
	
	public MirrorType getMirrorType() {
		return copyFrom.mirrorType();
	}
	
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
		Log.debug(CopyPair.class, "check source:" + sourceClazz.getClassName() + " <- target" + targetClazz.getClassName());
		Collection<FieldContextPair> fieldPairList = FieldContextPairBuilderFactory.getBuilder(getMirrorType()).getFieldContexPairs(this);
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
				Log.debug(CopyPair.class, "field type is compatible! " + pari.getTargetFC().getName() + " and " + pari.getSourceFC().getName());
			} else {
				Log.error(CopyPair.class, "field type is not compatible! " + pari.getTargetFC().getName() + " is " + pari.getTargetFC().getType() 
							+ ", but" + pari.getSourceFC().getName() + " is " + pari.getSourceFC().getType());
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
		Map<String, FieldContext> targetFieldMap = targetClazz.getFieldContexts();
		for(String name : copyFrom.thisFields()) {
			FieldContext fieldContext = targetFieldMap.get(name);
			if(fieldContext != null) {
				Log.debug(CopyPair.class, "field [" + name +  "] exist in [" + targetClazz.getClassName() + "]");
				if(fieldContext.isFinal()) {
					Log.error(CopyPair.class, targetClazz.getClassName() + " field[" + name +  "]is final!");
					throw new IllegalStateException(targetClazz.getClassName() + " field[" + name +  "]is final!");
				}
				if(fieldContext.isStatic()) {
					Log.worn(CopyPair.class, targetClazz.getClassName() + " field[" + name +  "]is static!");
				}
			}else {
				Log.error(CopyPair.class, "field [" + name +  "] not exist in [" + targetClazz.getClassName() + "]");
				return false;
			}
		}
		return true;
	}
}
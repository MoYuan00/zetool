package com.zetool.beancopy.field.checkor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.annotation.CopyFrom.MirrorType;
import com.zetool.beancopy.field.handler.FieldPairBuilderFactory;
import com.zetool.beancopy.field.ClassHelper;
import com.zetool.beancopy.field.FieldHelper;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;

/**
 * 检查一个映射
 * @author loki02
 * @date 2020年12月1日
 */
public final class UnequalsCopyPair<S, T> extends AbstractCopyPair<S, T>{

	/**
	 * 目标类上的注解, 这个注解中的sourceClass应该和sourceClazz相同
	 */
	private CopyFrom copyFrom;
	
	public UnequalsCopyPair(ClassHelper<S> sourceClazz, ClassHelper<T> targetClazz, CopyFrom copyFrom) {
		super();
		this.sourceHelper = sourceClazz;
		this.targetHelper = targetClazz;
		this.copyFrom = copyFrom;
	}

	public UnequalsCopyPair(ClassHelper<S> sourceClazz, ClassHelper<T> targetClazz) {
		super();
		this.sourceHelper = sourceClazz;
		this.targetHelper = targetClazz;
		this.copyFrom = getCopyFrom();
	}
	
	/**
	 * 获取到当前 映射类对 的 映射注解
	 * @return
	 */
	private CopyFrom getCopyFrom() {
		if(copyFrom == null) {
			copyFrom = CollectionUtils.findFirst(targetHelper.getAnnotations(CopyFrom.class),
						(c) -> c.sourceClass().getName().equals(sourceHelper.getClassName()));	// 获取到映射注解
		}
		return copyFrom;
	}
	
	private MirrorType getMirrorType() {
		return copyFrom.mirrorType();
	}

	@Override
	public Map<String, FieldHelper> getTargetFieldMap(){
		return targetHelper.getFieldContextsByCopyFrom(copyFrom);
	}

	@Override
	public T cloneSourceToTarget(S sourceObj) {
		Log.debug(this.getClass(), "<outer class clone> from " + sourceObj.getClass().getName()
				+ " to " + targetHelper.getClassName());
		targetHelper.bindThisClassInstance();
		sourceHelper.bindObject(sourceObj);
		// 获取映射集合, 并执行拷贝
		this.getFieldContextPairList().forEach(FieldPair::cloneSourceToTarget);
		return targetHelper.getBindObject();
	}

	@Override
	public List<FieldPair> getFieldContextPairList() {
		return FieldPairBuilderFactory.getBuilder(getMirrorType()).getFieldContextPairs(this);
	}

	/**
	 * 检查source 和 target对于注解copyFrom是否匹配
	 * @return
	 */
	@Override
	public boolean check() {
		if(sourceHelper.classIsNull() || targetHelper.classIsNull() || copyFrom == null) throw new NullPointerException();
		if(!checkTargetFields()) return false;
		Log.debug(UnequalsCopyPair.class, "check source:" + sourceHelper.getClassName() + " <- target" + targetHelper.getClassName());
		Collection<FieldPair> fieldPairList = FieldPairBuilderFactory.getBuilder(getMirrorType()).getFieldContextPairs(this);
		if(!checkTypeIsEquals(fieldPairList)) return false;
		return true;
	}
	
	/**
	 * 判断source和target对应的类型是否相同
	 * @return
	 */
	private boolean checkTypeIsEquals(Collection<FieldPair> pairList) {
		for(FieldPair pari : pairList) {
			if(pari.isMatch()) {
				Log.debug(UnequalsCopyPair.class, "field type is compatible! " + pari.getTargetFC().getName() + " and " + pari.getSourceFC().getName());
			} else {
				Log.error(UnequalsCopyPair.class, "field type is not compatible! " + pari.getTargetFC().getName() + " is " + pari.getTargetFC().getType() 
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
		Map<String, FieldHelper> targetFieldMap = targetHelper.getFieldContexts();
		for(String name : copyFrom.thisFields()) {
			FieldHelper fieldContext = targetFieldMap.get(name);
			if(fieldContext != null) {
				Log.debug(UnequalsCopyPair.class, "field [" + name +  "] exist in [" + targetHelper.getClassName() + "]");
				if(fieldContext.isFinal()) {
					Log.error(UnequalsCopyPair.class, targetHelper.getClassName() + " field[" + name +  "]is final!");
					throw new IllegalStateException(targetHelper.getClassName() + " field[" + name +  "]is final!");
				}
				if(fieldContext.isStatic()) {
					Log.worn(UnequalsCopyPair.class, targetHelper.getClassName() + " field[" + name +  "]is static!");
				}
			}else {
				Log.error(UnequalsCopyPair.class, "field [" + name +  "] not exist in [" + targetHelper.getClassName() + "]");
				return false;
			}
		}
		return true;
	}
}
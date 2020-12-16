package com.zetool.beancopy.checkor;

import com.zetool.beancopy.helper.FieldContent;

/**
 * 一对属性映射关系
 * @author loki02
 * @date 2020年12月2日
 */
public class FieldContentPair {
	private final FieldContent sourceFC;
	private final FieldContent targetFC;
	public FieldContentPair(FieldContent sourceFC, FieldContent targetFC) {
		super();
		this.sourceFC = sourceFC;
		this.targetFC = targetFC;
	}
	public FieldContent getSourceFC() {
		return sourceFC;
	}
	public FieldContent getTargetFC() {
		return targetFC;
	}
	/**
	 * 是否相匹配（可互相转换：当前是否支持相互拷贝）
	 * @return
	 */
	public boolean isMatch() {
		return sourceFC.getType().equals(targetFC.getType());
	}
	
	/**
	 * 将source 字段的值拷贝给target 字段
	 */
	public void cloneSourceToTarget() {
		Object obj = sourceFC.cloneValue();
		targetFC.setObject(obj);
	}
}
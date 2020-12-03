package com.zetool.beancopy.checkor;

import com.zetool.beancopy.helper.FieldContext;

/**
 * 一对属性映射关系
 * @author loki02
 * @date 2020年12月2日
 */
public class FieldContextPair{
	private FieldContext sourceFC;
	private FieldContext targetFC;
	public FieldContextPair(FieldContext sourceFC, FieldContext targetFC) {
		super();
		this.sourceFC = sourceFC;
		this.targetFC = targetFC;
	}
	public FieldContext getSourceFC() {
		return sourceFC;
	}
	public FieldContext getTargetFC() {
		return targetFC;
	}
	/**
	 * 是否相匹配（可互相转换：当前是否支持相互拷贝）
	 * @return
	 */
	public boolean isMatch() {
		return sourceFC.getType().equals(targetFC.getType());
	}
}
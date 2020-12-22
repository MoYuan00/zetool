package com.zetool.beancopy.checkor;

import com.zetool.beancopy.helper.FieldHelper;

/**
 * 一对属性映射关系
 * @author loki02
 * @date 2020年12月2日
 */
public class FieldPair {
	private final FieldHelper sourceFC;
	private final FieldHelper targetFC;
	public FieldPair(FieldHelper sourceFC, FieldHelper targetFC) {
		super();
		this.sourceFC = sourceFC;
		this.targetFC = targetFC;
	}
	public FieldHelper getSourceFC() {
		return sourceFC;
	}
	public FieldHelper getTargetFC() {
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
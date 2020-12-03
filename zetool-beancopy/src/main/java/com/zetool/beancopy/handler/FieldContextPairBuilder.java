package com.zetool.beancopy.handler;

import java.util.Collection;

import com.zetool.beancopy.checkor.CopyPair;
import com.zetool.beancopy.checkor.FieldContextPair;

/**
 * 构建映射关系
 * @author loki02
 * @date 2020年12月3日
 */
public interface FieldContextPairBuilder {
	
	/**
	 * 通过一对拷贝类，生成拷贝字段对映射集合
	 * @param copyPair
	 * @return
	 */
	public <S, T> Collection<FieldContextPair> getFieldContexPairs(CopyPair<S, T> copyPair);
}

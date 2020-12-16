package com.zetool.beancopy.handler;

import java.util.List;

import com.zetool.beancopy.checkor.CopyPair;
import com.zetool.beancopy.checkor.FieldContentPair;

/**
 * 构建映射关系
 * @author loki02
 * @date 2020年12月3日
 */
public interface FieldContentPairBuilder {
	
	/**
	 * 通过一对拷贝类，生成拷贝字段对映射集合
	 * @param copyPair
	 * @return
	 */
	public <S, T> List<FieldContentPair> getFieldContextPairs(CopyPair<S, T> copyPair);
}

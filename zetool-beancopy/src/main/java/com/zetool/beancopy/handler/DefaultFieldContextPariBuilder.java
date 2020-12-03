package com.zetool.beancopy.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.zetool.beancopy.checkor.CopyPair;
import com.zetool.beancopy.checkor.FieldContextPair;
import com.zetool.beancopy.helper.FieldContext;

/**
 * 默认映射方式，映射字段对
 * @author loki02
 * @date 2020年12月3日
 */
public class DefaultFieldContextPariBuilder implements FieldContextPairBuilder {

	@Override
	public <S, T> Collection<FieldContextPair> getFieldContexPairs(CopyPair<S, T> copyPair) {
		Map<String, FieldContext> sourceFieldMap = copyPair.getSourceFieldMap();
		Map<String, FieldContext> targetFieldMap = copyPair.getTargetFieldMap();
		List<FieldContextPair> fieldPairList = new ArrayList<>(); // 生成映射
		for (String fieldName : targetFieldMap.keySet()) {
			FieldContext sourceContext = sourceFieldMap.get(fieldName);
			if(sourceContext != null) {
				fieldPairList.add(new FieldContextPair(sourceContext, targetFieldMap.get(fieldName)));
			}else {
				throw new IllegalStateException();// 表示映射出错
			}
		}
		return fieldPairList;
	}

}

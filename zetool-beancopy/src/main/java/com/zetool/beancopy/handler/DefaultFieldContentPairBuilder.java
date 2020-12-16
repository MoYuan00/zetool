package com.zetool.beancopy.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zetool.beancopy.checkor.CopyPair;
import com.zetool.beancopy.checkor.FieldContentPair;
import com.zetool.beancopy.helper.FieldContent;

/**
 * 默认映射方式，映射字段对
 * @author loki02
 * @date 2020年12月3日
 */
public class DefaultFieldContentPairBuilder implements FieldContentPairBuilder {

	@Override
	public <S, T> List<FieldContentPair> getFieldContextPairs(CopyPair<S, T> copyPair) {
		Map<String, FieldContent> sourceFieldMap = copyPair.getSourceFieldMap();
		Map<String, FieldContent> targetFieldMap = copyPair.getTargetFieldMap();
		List<FieldContentPair> fieldPairList = new ArrayList<>(); // 生成映射
		for (String fieldName : targetFieldMap.keySet()) {
			FieldContent sourceContext = sourceFieldMap.get(fieldName);
			if(sourceContext != null) {
				fieldPairList.add(new FieldContentPair(sourceContext, targetFieldMap.get(fieldName)));
			}else {
				throw new IllegalStateException("没有找到字段: " + fieldName);// 表示映射出错
			}
		}
		return fieldPairList;
	}

}

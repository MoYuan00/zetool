package com.zetool.beancopy.field.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zetool.beancopy.field.checkor.CopyPair;
import com.zetool.beancopy.field.checkor.FieldPair;
import com.zetool.beancopy.field.FieldHelper;

/**
 * 默认映射方式，映射字段对
 * @author loki02
 * @date 2020年12月3日
 */
public class DefaultFieldPairBuilder implements FieldPairBuilder {

	@Override
	public <S, T> List<FieldPair> getFieldContextPairs(CopyPair<S, T> copyPair) {
		Map<String, FieldHelper> sourceFieldMap = copyPair.getSourceFieldMap();
		Map<String, FieldHelper> targetFieldMap = copyPair.getTargetFieldMap();
		List<FieldPair> fieldPairList = new ArrayList<>(); // 生成映射
		for (String fieldName : targetFieldMap.keySet()) {
			FieldHelper sourceContext = sourceFieldMap.get(fieldName);
			if(sourceContext != null) {
				fieldPairList.add(new FieldPair(sourceContext, targetFieldMap.get(fieldName)));
			}else {
				throw new IllegalStateException("没有找到字段: " + fieldName);// 表示映射出错
			}
		}
		return fieldPairList;
	}

}

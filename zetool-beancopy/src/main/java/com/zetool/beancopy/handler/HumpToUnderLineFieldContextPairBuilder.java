package com.zetool.beancopy.handler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zetool.beancopy.checkor.CopyPair;
import com.zetool.beancopy.checkor.FieldContextPair;
import com.zetool.beancopy.helper.FieldContext;

/**
 * 支持驼峰命名法的 映射构建类
 * 驼峰命名法 映射 到 下划线风格
 * @author loki02
 * @date 2020年12月3日
 */
public class HumpToUnderLineFieldContextPairBuilder implements FieldContextPairBuilder {

	@Override
	public <S, T> Collection<FieldContextPair> getFieldContexPairs(CopyPair<S, T> copyPair) {
		Map<String, FieldContext> sourceFieldMap = copyPair.getSourceFieldMap();
		Map<String, FieldContext> targetFieldMap = copyPair.getTargetFieldMap();
		
		Set<FieldContextPair> contextPairs = new HashSet<FieldContextPair>();
		// targetField中的应该是 驼峰
		// sourceField中应该 是下划线风格
		for(String name :targetFieldMap.keySet()) {
			String underLineName = getUnderLineStyleName(name);// 下划线风格
			FieldContext sourceField = sourceFieldMap.get(underLineName);
			if(sourceField == null) {
				throw new IllegalStateException("驼峰映射下划线匹配失败！");
			}
			contextPairs.add(new FieldContextPair(sourceField, targetFieldMap.get(name)));
		}
		return contextPairs;
	}
	
	
	/**
	 * 驼峰风格转成下划线风格
	 * 获取下划线风格的名称
	 * @return 
	 */
	private static String getUnderLineStyleName(String name) {
		StringBuilder sb = new StringBuilder();
		for(char ch : name.toCharArray()) {
			if(Character.isUpperCase(ch)) {
				sb.append('_');
				sb.append(Character.toLowerCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getUnderLineStyleName("nameStyleTO"));
	}

}

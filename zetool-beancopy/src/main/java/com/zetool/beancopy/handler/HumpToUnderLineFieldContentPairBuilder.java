package com.zetool.beancopy.handler;

import java.util.*;

import com.zetool.beancopy.checkor.CopyPair;
import com.zetool.beancopy.checkor.FieldContentPair;
import com.zetool.beancopy.helper.FieldContent;

/**
 * 支持驼峰命名法的 映射构建类
 * 驼峰命名法 映射 到 下划线风格
 * @author loki02
 * @date 2020年12月3日
 */
public class HumpToUnderLineFieldContentPairBuilder implements FieldContentPairBuilder {

	@Override
	public <S, T> List<FieldContentPair> getFieldContextPairs(CopyPair<S, T> copyPair) {
		Map<String, FieldContent> sourceFieldMap = copyPair.getSourceFieldMap();
		Map<String, FieldContent> targetFieldMap = copyPair.getTargetFieldMap();
		
		List<FieldContentPair> contextPairs = new ArrayList<>();
		// targetField中的应该是 驼峰
		// sourceField中应该 是下划线风格
		for(String name :targetFieldMap.keySet()) {
			String underLineName = getUnderLineStyleName(name);// 下划线风格
			FieldContent sourceField = sourceFieldMap.get(underLineName);
			if(sourceField == null) {
				throw new IllegalStateException("驼峰映射下划线匹配失败！");
			}
			contextPairs.add(new FieldContentPair(sourceField, targetFieldMap.get(name)));
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

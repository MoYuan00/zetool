package com.zetool.beancopy.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zetool.beancopy.checkor.CopyPair;
import com.zetool.beancopy.checkor.FieldContentPair;
import com.zetool.beancopy.helper.FieldContent;

/**
 * 支持驼峰命名法的 映射构建类
 *  下划线风格 映射 到  驼峰命名法
 * @author loki02
 * @date 2020年12月3日
 */
public class UnderLineToHumpFieldContentPairBuilder implements FieldContentPairBuilder {

	@Override
	public <S, T> List<FieldContentPair> getFieldContextPairs(CopyPair<S, T> copyPair) {
		Map<String, FieldContent> sourceFieldMap = copyPair.getSourceFieldMap();
		Map<String, FieldContent> targetFieldMap = copyPair.getTargetFieldMap();
		List<FieldContentPair> contextPairs = new ArrayList<FieldContentPair>();
		// targetField中的应该是下划线风格 
		// sourceField中应该 是驼峰
		for (String fieldName : targetFieldMap.keySet()) {
			String humpName = getHumpStyleName(fieldName);// 驼峰风格
			FieldContent sourceContext = sourceFieldMap.get(humpName);
			if(sourceContext != null) {
				contextPairs.add(new FieldContentPair(sourceContext, targetFieldMap.get(fieldName)));
			}else {
				throw new IllegalStateException("下划线映射驼峰匹配失败！");// 表示映射出错
			}
		}
		return contextPairs;
	}
	
	/**
	 * 下划线风格转成驼峰风格
	 * 获取驼峰风格的名称
	 * @return
	 */
	private static String getHumpStyleName(String name) {
		StringBuilder sb = new StringBuilder();
		boolean toUpper = false;
		for(char ch : name.toCharArray()) {
			if(ch == '_') {
				toUpper = true; continue;
			} 
			if(toUpper) {
				toUpper = false;
				ch = Character.toUpperCase(ch);
			}
			sb.append(ch);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getHumpStyleName("name_style_t_o"));
	}

}

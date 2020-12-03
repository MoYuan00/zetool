package com.zetool.beancopy.handler;

/**
 * 构建类工厂
 * @author loki02
 * @date 2020年12月3日
 */
public class FieldContextPairBuilderFactory {
	
	public static FieldContextPairBuilder getBuilder() {
		return new DefaultFieldContextPariBuilder();
	}
	
}

package com.zetool.beancopy.handler;


import com.zetool.beancopy.annotation.CopyFrom.MirrorType;
import com.zetool.beancopy.util.Log;

/**
 * 构建类工厂
 * @author loki02
 * @date 2020年12月3日
 */
public class FieldPairBuilderFactory {

	public static FieldPairBuilder getBuilder() {
		Log.debug(FieldPairBuilderFactory.class, "get default builder");
		return new DefaultFieldPairBuilder();
	}

	public static FieldPairBuilder getBuilder(MirrorType mirrorType) {
		Log.debug(FieldPairBuilderFactory.class, "mirrorType is " + mirrorType.name());
		switch (mirrorType) {
			case DEFAULT:
				return new DefaultFieldPairBuilder();
			case HUMP_TO_UNDER_LINE:
				return new HumpToUnderLineFieldPairBuilder();
			case UNDER_LINE_TO_HUMP:
				return new UnderLineToHumpFieldPairBuilder();
		}
		throw new IllegalArgumentException();
	}
	
}

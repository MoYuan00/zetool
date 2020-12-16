package com.zetool.beancopy.handler;


import com.zetool.beancopy.annotation.CopyFrom.MirrorType;
import com.zetool.beancopy.util.Log;

/**
 * 构建类工厂
 * @author loki02
 * @date 2020年12月3日
 */
public class FieldContentPairBuilderFactory {
	
	public static FieldContentPairBuilder getBuilder(MirrorType mirrorType) {
		Log.debug(FieldContentPairBuilderFactory.class, "mirrorType is " + mirrorType.name());
		switch (mirrorType) {
			case DEFAULT:
				return new DefaultFieldContentPairBuilder();
			case HUMP_TO_UNDER_LINE:
				return new HumpToUnderLineFieldContentPairBuilder();
			case UNDER_LINE_TO_HUMP:
				return new UnderLineToHumpFieldContentPairBuilder();
		}
		throw new IllegalArgumentException();
	}
	
}

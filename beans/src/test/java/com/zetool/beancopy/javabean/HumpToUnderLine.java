package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.annotation.CopyFrom.MirrorType;

@CopyFrom(sourceClass = UnderLineToHump.class, mirrorType = MirrorType.HUMP_TO_UNDER_LINE)
public class HumpToUnderLine {
	 String type = "UnderLineToHump type";
	 String password = "UnderLineToHump password";
//	 List<Object> list;
	 String tempName;
//	 Set<Object> list;
}

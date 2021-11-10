package com.zetool.beancopy.javabean;


import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.annotation.CopyFrom.MirrorType;

@CopyFrom(sourceClass = Default.class, mirrorType = MirrorType.UNDER_LINE_TO_HUMP)
public class UnderLineToHump {
	 String type = "UnderLineToHump type";
	 String password = "UnderLineToHump password";
	 String temp_name = "UnderLineToHump temp_name";
//	 Set<Object> list;
}

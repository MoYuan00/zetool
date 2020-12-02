package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(sourceClass = C.class)
public class Default {
	 String type;
	 String password = "ccc password";
	 List<Object> list;
//	 Set<Object> list;
}

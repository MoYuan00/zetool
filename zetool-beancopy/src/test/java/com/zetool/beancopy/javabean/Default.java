package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(sourceClass = C.class, fields = {"type"})
public class Default {
	 String type = "Default type";
	 String password = "Default password";
	 List<Object> list;
	 String tempName = "Default tempName";
//	 Set<Object> list;
}

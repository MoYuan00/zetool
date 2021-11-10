package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(sourceClass = Default.class, exceptThisFields = "tempName")
public class Except {
	 String type = "Except type";
	 String password = "Except password";
	 List<Object> list;
	 String tempName = "Except tempName";
//	 Set<Object> list;
}

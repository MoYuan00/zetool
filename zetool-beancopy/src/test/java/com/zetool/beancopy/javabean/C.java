package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(sourceClass = B.class, fields = {"list", "type"})
public class C {
	 String type;
	 List<Object> list;
}

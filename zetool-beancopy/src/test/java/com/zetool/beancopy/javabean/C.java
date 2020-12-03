package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(sourceClass = B.class, thisFields = {"list", "type"})
@CopyFrom(sourceClass = A.class, thisFields = {"list", "password"})
public class C {
	 String type = "";
	 String password = "ccc password";
	 List<Object> list;
}

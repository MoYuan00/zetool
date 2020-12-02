package com.zetool.beancopy.javabean;

import java.util.List;
import java.util.Set;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(sourceClass = B.class, fields = {"list", "type"})
@CopyFrom(sourceClass = A.class, fields = {"list", "password"})
public class C {
	 String type;
	 String password = "ccc password";
//	 List<Object> list;
	 Set<Object> list;
}

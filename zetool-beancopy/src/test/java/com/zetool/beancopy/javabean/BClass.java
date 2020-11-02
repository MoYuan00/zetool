package com.zetool.beancopy.javabean;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(
		fromClass = AClass.class,
		fields = {"a", "b", "c"}
		)
public class BClass {
	private String a = "b";
	private int b = 1;
	private int c = 2;
}

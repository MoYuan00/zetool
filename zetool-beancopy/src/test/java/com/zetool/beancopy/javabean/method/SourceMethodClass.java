package com.zetool.beancopy.javabean.method;

import com.zetool.beancopy.annotation.CopyTo;

public class SourceMethodClass {
	private String name = "SourceMethodClass name";
	private String password = "SourceMethodClass password";
	private int count = 30;
	
	@CopyTo(targetClass = TargetMethodClass.class, sourceField = {"name", "count"} )
	public TargetMethodClass copyToTargetMethodClass() {
		return null;
	}
}

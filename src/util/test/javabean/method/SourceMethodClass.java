package util.test.javabean.method;

import util.annotation.method.CopyTo;

public class SourceMethodClass {
	private String name = "SourceMethodClass name";
	private String password = "SourceMethodClass password";
	private int count = 30;
	
	@CopyTo(targetClass = TargetMethodClass.class, sourceField = {"name", "count"} )
	public TargetMethodClass copyToTargetMethodClass() {
		return null;
	}
}

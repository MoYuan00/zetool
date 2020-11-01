package util.test.javabean;

import util.copy.CopyUtils;

public class AClass {
	private String a = "a";
	private int b = 1111;
	private int c = 2222;
	private int d = 3333;
	
	public BClass copyToB() throws InstantiationException, IllegalAccessException {
		return CopyUtils.copyForm(this, BClass.class);
	}
}

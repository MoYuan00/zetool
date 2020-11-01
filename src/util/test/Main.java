package util.test;

import proxy.javabean.CClass2;
import proxy.javabean.DClass2;
import util.copy.CopyUtils;
import util.reflex.FieldUtil;
import util.test.javabean.AClass;
import util.test.javabean.BClass;

public class Main {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		// 拷贝不同类对象
		BClass b = CopyUtils.copy(new AClass(), BClass.class);
		FieldUtil.showFieldsValue(b);
		
		DClass2 d = CopyUtils.copy(new CClass2(), DClass2.class);
		FieldUtil.showFieldsValue(d);
		
		BClass b2 = new AClass().copyToB();
		FieldUtil.showFieldsValue(b2);
		
		// 拷贝同类对象
		BClass b3 = CopyUtils.copy(new BClass());
		FieldUtil.showFieldsValue(b3);
		
	}
}

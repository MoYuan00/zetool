package com.zetool.beancopy;


import com.zetool.beancopy.checkor.CopyFromAnnotationCheckor;
import com.zetool.beancopy.checkor.FieldContext;
import com.zetool.beancopy.handler.Copier;
import com.zetool.beancopy.handler.SimpleClassScanner;
import com.zetool.beancopy.javabean.A;
import com.zetool.beancopy.javabean.B;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;

public class App2 {
	public static void main(String[] args) {
//		checkTest();
//		copyTest();
		
		checkMultCopy();
	}
	public static void checkTest() {
		// TODO 由于大量使用static方法，导致封装特别差劲，代码重用低
		// 需要将相关操作封装为一个类，而不是各种static方法
		CopyFromAnnotationCheckor.check(
				CollectionUtils.toSet(
						new SimpleClassScanner().addClassesByClassName(A.class.getName()).getClasses()
						)
				);
		// TODO 当前仅仅判断了属性的名称是否相等的，类型没有判断。（假设了类型是相等的。要支持需要重写check方法
	}
	
	/**
	 * 检查允许多个CopyFrom注解
	 */
	public static void checkMultCopy(){
		CopyFromAnnotationCheckor.check(
				CollectionUtils.toSet(
						new SimpleClassScanner().addClassesByClassName(A.class.getName()).getClasses()
						)
				);
	}
	
	public static void copyTest() {
		A a = new A();
		try {
			B b = Copier.copy(a, B.class);
			Log.info(App2.class, "拷贝后的B为:" + FieldContext.toString(b));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	

}

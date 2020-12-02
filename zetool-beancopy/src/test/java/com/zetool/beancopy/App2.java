package com.zetool.beancopy;


import com.zetool.beancopy.checkor.Checkor;
import com.zetool.beancopy.handler.Copier;
import com.zetool.beancopy.handler.SimpleClassScanner;
import com.zetool.beancopy.helper.FieldContext;
import com.zetool.beancopy.javabean.A;
import com.zetool.beancopy.javabean.B;
import com.zetool.beancopy.javabean.C;
import com.zetool.beancopy.util.Log;

public class App2 {
	public static void main(String[] args) {
		
		simpleCopyTest();
		
//		checkTest();
//		copyTest();
		
		checkMultCopy();
		
		multCopyTest();
	}
	public static void checkTest() {
		Checkor.check(
						new SimpleClassScanner().addClassesByClassName(A.class.getName())
				);
	}
	
	/**
	 * 检查允许多个CopyFrom注解
	 */
	public static void checkMultCopy(){
		Checkor.check(
						new SimpleClassScanner().addClassesByClassName(A.class.getName())
				);
	}
	
	/**
	 * 运行多个注解情况下的拷贝
	 */
	public static void simpleCopyTest(){
		A a = new A();
		A a2 = Copier.copy(a);
		Log.info(App2.class, "拷贝后的A为:" + FieldContext.toString(a2));
	}
	
	/**
	 * 运行多个注解情况下的拷贝
	 */
	public static void multCopyTest(){
		A a = new A();
		B b = Copier.copy(a, B.class);
		Log.info(App2.class, "拷贝后的B为:" + FieldContext.toString(b));
		C c = Copier.copy(a,  C.class);
		Log.info(App2.class, "拷贝后的C为:" + FieldContext.toString(c));
	}
	
	public static void copyTest() {
		A a = new A();
		B b = Copier.copy(a, B.class);
		Log.info(App2.class, "拷贝后的B为:" + FieldContext.toString(b));
	}
	

}

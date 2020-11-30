package com.zetool.beancopy;

import java.util.ArrayList;
import java.util.Arrays;

import com.zetool.beancopy.checkor.CopyFromAnnotationCheckor;
import com.zetool.beancopy.checkor.FieldContext;
import com.zetool.beancopy.handler.Copier;
import com.zetool.beancopy.handler.SimpleClassScanner;
import com.zetool.beancopy.javabean.A;
import com.zetool.beancopy.javabean.B;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;

public class App {
	public static void main(String[] args) {
		checkTest();
		copyTest();
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
	public static void copyTest() {
		A a = new A();
		try {
			B b = Copier.copy(a, B.class);
			Log.info(App.class, "拷贝后的B为:" + FieldContext.toString(b));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void testCopy() throws InstantiationException, IllegalAccessException {
		// 对于基本类型 IO拷贝 ，可以直接new对象拷贝比IO要快，要优化实现
		System.out.println(Copier.copy(new String("123")));
		System.out.println(Copier.copy(123123));
		// 数组 元素实现Serializable 调用 IO拷贝
		System.out.println(Arrays.toString(Copier.copy(new int[] {1, 4, 3})));
		
		// 数组元素没有实现Serializable接口， IO拷贝失败
		System.out.println(Arrays.toString(Copier.copy(new App[] {new App(), new App()})));
		
		// list 集合中 没有继承 Serializable 接口 IO拷贝失败
		// 思路：对集合中元素依次拷贝
		System.out.println(Arrays.toString(Copier.copy(new ArrayList<>(Arrays.asList(new App()))).toArray()));
		
	}
}

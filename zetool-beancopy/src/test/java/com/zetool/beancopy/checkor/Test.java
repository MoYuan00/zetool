package com.zetool.beancopy.checkor;

import java.io.FileNotFoundException;

import com.zetool.beancopy.scanner.ClassScanner;
import com.zetool.beancopy.util.CollectionUtils;

/**
 * 测试 注解检查功能
 * @author Rnti
 *
 */
public class Test {
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
		// TODO 由于大量使用static方法，导致封装特别差劲，代码重用低
		// 需要将相关操作封装为一个类，而不是各种static方法
		CopyFromAnnotationCheckor.check(
				CollectionUtils.toSet(
						ClassScanner.scanClassPackage(Test.class)
						)
				);
		// TODO 当前仅仅判断了属性的名称是否相等的，类型没有判断。（假设了类型是相等的。要支持需要重写check方法
	}
}

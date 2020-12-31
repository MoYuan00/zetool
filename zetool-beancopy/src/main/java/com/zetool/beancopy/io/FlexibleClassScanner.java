package com.zetool.beancopy.io;

import java.util.*;

import com.zetool.beancopy.field.ClassesHelper;

/**
 * java类文件扫描器
 * @author loki02
 * @date 2020年11月29日
 */
public class FlexibleClassScanner implements ClassScanner {

	/**
	 * 存放所有扫描到的类
	 */
	private Set<Class<?>> classSet = new HashSet<>();

	@Override
	public ClassScanner addPackageClasses(String packageName) {
		classSet.addAll(ClassScanner.packageScanner(packageName));// 获取当前包下以及子包下所以的类
		return this;
	}

	@Override
	public ClassScanner addClass(Class<?> clazz) {
		classSet.add(clazz);
		return this;
	}


	@Override
	public ClassesHelper getClasses() {
		return new ClassesHelper(classSet);
	}

}

package com.zetool.beancopy.handler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.zetool.beancopy.helper.ClassesHelper;
import com.zetool.beancopy.util.Log;

/**
 * java类文件扫描器
 * @author loki02
 * @date 2020年11月29日
 */
public class SimpleClassScanner implements ClassScanner {
	
	/**
	 * 存放所有扫描到的类
	 */
	private Set<Class<?>> classSet;
	
	/**
	 * 存放要扫描的所有包
	 */
	private Set<String> packageSet = new HashSet<String>();
	
	@Override
	public ClassScanner addPackageClasses(String packageName) {
		Log.info(SimpleClassScanner.class, "add package[" + packageName + "]");
		packageSet.add(packageName);
		return this;
	}
	
	@Override
	public ClassScanner addClass(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			Log.info(SimpleClassScanner.class, "add class<" + className + ">");
			classSet.add(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
	@Override
	public ClassesHelper getClasses() {
		classSet = new HashSet<Class<?>>();
		for(String pgName : packageSet) 
			classSet.addAll(scanPackage(pgName));
		return new ClassesHelper().setClassSet(classSet);
	}
	
	
	/**
	 * 通过类名扫描该类所在包以及 包下的所有子包的class
	 * @param clazz
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	private Collection<Class<?>> scanPackage(String packageName) {
		Log.debug(SimpleClassScanner.class, "scan package name is:" + packageName);
		try {
			Collection<Class<?>> classes = new ClassScannerUtils().packageScanner(packageName); // 获取当前包下以及子包下所以的类
			Log.debug(SimpleClassScanner.class, "class is scanned " + classes.size() + " total of number");
			return classes;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(0); 
	}
	
}

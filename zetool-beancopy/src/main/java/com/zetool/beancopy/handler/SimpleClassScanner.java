package com.zetool.beancopy.handler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
		Log.info(SimpleClassScanner.class, "添加包[" + packageName + "]");
		packageSet.add(packageName);
		return this;
	}
	
	@Override
	public ClassScanner addClass(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			Log.info(SimpleClassScanner.class, "添加类<" + className + ">");
			classSet.add(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
	@Override
	public Collection<Class<?>> getClasses() {
		classSet = new HashSet<Class<?>>();
		for(String pgName : packageSet) 
			classSet.addAll(scanPackage(pgName));
		return classSet;
	}
	
	
	/**
	 * 通过类名扫描该类所在包以及 包下的所有子包的class
	 * @param clazz
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	private Collection<Class<?>> scanPackage(String packageName) {
		Log.info(SimpleClassScanner.class, "扫描包：" + packageName);
		try {
			Collection<Class<?>> classes = new ClassScannerUtils().packageScanner(packageName); // 获取当前包下以及子包下所以的类
			Log.info(SimpleClassScanner.class, "扫描到共:" + classes.size() + "个类");
			return classes;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(0); 
	}
	
}

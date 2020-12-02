package com.zetool.beancopy.handler;


import com.zetool.beancopy.helper.ClassesHelper;

/**
 * 类扫描器接口
 * <pre>
 * 实现对于类，包的扫描。并存放扫描结果。
 * </pre>
 * @author loki02
 * @date 2020年11月29日
 */
public interface ClassScanner {
	
	/**
	 * 通过包名，添加一个需要扫描的类的集合，<b>将扫描这个包的所有类, 以及子包下的所有类</b>
	 * @param packageName
	 * @return
	 */
	public ClassScanner addPackageClasses(String packageName);
	
	/**
	 * 通过一个类，添加一个要扫描的类的集合<b>将扫描这个类以及同包的类，和所有子包的类</b>
	 * @param clazz
	 * @return
	 */
	public default ClassScanner addClassesByClassName(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return addPackageClasses(clazz.getPackage().getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * 添加一个类到集合中
	 * @param clazz
	 * @return
	 */
	public ClassScanner addClass(String className);
	
	/**
	 * 获取所有扫描到的类
	 * @return 扫描到的所有类的集合
	 */
	public ClassesHelper getClasses();
}

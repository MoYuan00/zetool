package com.zetool.beancopy.scanner;

import java.io.FileNotFoundException;
import java.util.List;

import com.zetool.beancopy.proxy.ProxyBuilder;

/**
 * java类文件扫描器
 * @author Rnti
 *
 */
public class ClassScanner {
	/**
	 * 通过类名扫描该类所在包以及 包下的所有子包的class
	 * @param clazz
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> scanClassPackage(Class<?> clazz) throws FileNotFoundException, ClassNotFoundException{
		String packageName = clazz.getPackage().getName();
		System.out.println("当前包名:" + packageName);
		return PackageClassScanner.packageScanner(packageName);// 获取当前包下以及子包下所以的类
	}
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
		System.out.println(ClassScanner.scanClassPackage(ProxyBuilder.class).size());
		System.out.println(PackageClassScanner.packageScanner(ProxyBuilder.class.getPackage().getName()).size());
	}
}

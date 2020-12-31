package com.zetool.beancopy.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import com.zetool.beancopy.field.ClassesHelper;
import com.zetool.beancopy.util.Log;

/**
 * 扫描一个包中，以及子包下的jar包文件，和类文件
 * @author loki02
 * @date 2020年11月30日
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
	 * @param className
	 * @return
	 */
	public default ClassScanner addPackageClassesByClassName(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return this.addPackageClasses(clazz.getPackage().getName());
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
	public ClassScanner addClass(Class<?> clazz);

	/**
	 * 添加一个类到集合中
	 * @param className
	 * @return
	 */
	public default ClassScanner addClass(String className){
		try {
			Class<?> clazz = Class.forName(className);
			return this.addClass(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 获取所有扫描到的类
	 * @return 扫描到的所有类的集合
	 */
	public ClassesHelper getClasses();
	
	/**
	 * 通过包名扫描包下的所有.class文件
	 * @param packageName 包名
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static  Collection<Class<?>> packageScanner(String packageName)  {
		Collection<Class<?>> classSet = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');	//获取包的名字 并进行替换
        try {
        	Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);// 这里获取到了url
			while(dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
                String protocol = url.getProtocol();		//得到协议的名称
                if ("file".equals(protocol)) { 			//如果是以文件的形式保存在服务器上
                	String filePath = URLDecoder.decode(url.getFile(), "UTF-8"); //获取包的物理路径
                	fileScanner(packageName, filePath, classSet); 				//以文件的方式扫描整个包下的文件 并添加到集合中
                } else if("jar".equals(protocol)) {		// 以jar包放在服务器上
                	jarScanner(packageName, url, classSet);
                } else {
                	throw new IllegalStateException("protocol is " + protocol);
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classSet;
	}
	
	
	/**
	 * 扫描jar包
	 * @param packageName
	 * @param url
	 */
	public static void jarScanner(String packageName, URL url, final Collection<Class<?>> classSet) {
		try {
			JarFile jarFile = ((JarURLConnection)(url.openConnection())).getJarFile();
			Log.info(ClassScanner.class, "scan jar:" + jarFile.getName());
			Enumeration<JarEntry> entries = jarFile.entries();
			while(entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				if(name.charAt(0) == '/') name = name.substring(1); // 去掉开头的/
				if(name.startsWith(packageName)) {// 如果包名的前半段相同，表示需要扫描
					int index = name.lastIndexOf("/"); 
					if(index > -1) { // 如果能够找到/符号
						packageName = name.substring(0, index).replace("/", ".");// 获取到包名
						if(name.endsWith(".class") && !entry.isDirectory()) {// 如果是.class文件
							// 去掉后面对的className，获取真正的类名
							String className = name.substring(packageName.length() + 1, name.length() - 6);
							classSet.add(forName(packageName , className));
						}
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过文件，和包名扫描一个包下的所有.class文件
	 * @param packageName
	 * @param packagePath
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public static void fileScanner(String packageName, String packagePath, final Collection<Class<?>> classSet) {
		// 深度优先扫描： 可以传递包名
		File file = new File(packagePath);
		if(!file.exists())
			return;
		for (File nextFile : file.listFiles((f)-> f.isDirectory() || f.getName().endsWith(".class")) ) {
			if(nextFile.isDirectory()) 
				fileScanner(packageName + "." + nextFile.getName(), nextFile.getAbsolutePath(), classSet);
			else {
				// 如果是文件
				String className = nextFile.getName();
				className = className.substring(0, className.length() - 6);// 去掉.class
				Class<?> clazz = forName(packageName,  className);
				classSet.add(clazz); // 添加进来
			}
		}
	}

	public static Class<?> forName(String packageName, String className){
		Class<?> clazz = null;
		try {
			clazz = Class.forName(packageName + "." + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
}

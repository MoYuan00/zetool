package com.zetool.beancopy.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import com.zetool.beancopy.util.Log;

/**
 * 扫描一个包中，以及子包下的jar包文件，和类文件
 * @author loki02
 * @date 2020年11月30日
 */
public class ClassScannerUtils {
	
	private Collection<Class<?>> classSet = new HashSet<>(); 
	
	/**
	 * 通过包名扫描包下的所有.class文件
	 * @param packageName 包名
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Collection<Class<?>> packageScanner(String packageName) throws ClassNotFoundException {
        String packageDirName = packageName.replace('.', '/');	//获取包的名字 并进行替换
        try {
        	Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);// 这里获取到了url
			while(dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
                String protocol = url.getProtocol();		//得到协议的名称
                if ("file".equals(protocol)) { 			//如果是以文件的形式保存在服务器上
                	String filePath = URLDecoder.decode(url.getFile(), "UTF-8"); //获取包的物理路径
                	fileScanner(packageName, filePath); 				//以文件的方式扫描整个包下的文件 并添加到集合中
                } else if("jar".equals(protocol)) {		// 以jar包放在服务器上
                	jarScanner(packageName, url);
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
	private void jarScanner(String packageName, URL url) {
		try {
			JarFile jarFile = ((JarURLConnection)(url.openConnection())).getJarFile();
			Log.info(ClassScannerUtils.class, "扫描jar:" + jarFile.getName());
			Enumeration<JarEntry> entries = jarFile.entries();
			while(entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				if(name.charAt(0) == '/') name = name.substring(1); // 去掉开头的/
				if(name.startsWith(packageName)) {// 如果包名的前半段相同，表示需要扫描
					int index = name.lastIndexOf("/"); 
					if(index > -1) { // 如果能够找到/符号
						packageName = name.substring(0, index).replace("/", ".");// 获取到包名
					}
					if(index > -1) {
						if(name.endsWith(".class") && !entry.isDirectory()) {// 如果是.class文件
							// 去掉后面对的className，获取真正的类名
							String className = name.substring(packageName.length() + 1, name.length() - 6);
							try {
								classSet.add(Class.forName(packageName + "." + className));
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
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
	 * @param classList
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	private void fileScanner(String packageName, String packagePath) 
					throws FileNotFoundException, ClassNotFoundException{
		Log.info(ClassScannerUtils.class, "扫描包路径：" + packagePath);
		// 深度优先扫描： 可以传递包名
		File file = new File(packagePath);
		if(!file.exists())
			return;
		for (File nextFile : file.listFiles((f)-> f.isDirectory() || f.getName().endsWith(".class")) ) {
			if(nextFile.isDirectory()) 
				fileScanner(packageName + "." + nextFile.getName(), nextFile.getAbsolutePath());
			else {
				// 如果是文件
				String className = nextFile.getName();
				className = className.substring(0, className.length() - 6);// 去掉.class
				Class<?> clazz = Class.forName(packageName + '.' + className);
				Log.info(ClassScannerUtils.class, "扫描到class: " + clazz.getName());
				classSet.add(clazz); // 添加进来
			}
		}
	}
}

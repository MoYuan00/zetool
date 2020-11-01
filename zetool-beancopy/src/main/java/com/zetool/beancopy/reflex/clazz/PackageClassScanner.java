package util.reflex.clazz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PackageClassScanner {
	/**
	 * 通过包名扫描包下的所有.class文件
	 * @param packageName 包名
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> packageScanner(String packageName) throws ClassNotFoundException {
		List<Class<?>> classList = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');//获取包的名字 并进行替换
		Enumeration<URL> dirs = null;
        try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);// 这里获取到了url
			while(dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
                String protocol = url.getProtocol();//得到协议的名称
                if ("file".equals(protocol)) { //如果是以文件的形式保存在服务器上
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");//获取包的物理路径
                    scannerPackageByFile(packageName, filePath, classList); //以文件的方式扫描整个包下的文件 并添加到集合中
                }else {
                	System.err.println("protocol is " + protocol);
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classList;
	}
	/**
	 * 通过文件，和包名扫描一个包下的所有.class文件
	 * @param packageName
	 * @param packagePath
	 * @param classList
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	private static void scannerPackageByFile(String packageName, String packagePath, List<Class<?>> classList) 
					throws FileNotFoundException, ClassNotFoundException{
		// 深度优先扫描： 可以传递包名
		File file = new File(packagePath);
		if(!file.exists())
			return;
		for (File nextFile : file.listFiles((ft)->{
			return ft.isDirectory() || ft.getName().endsWith(".class");
			})) {
			if(nextFile.isDirectory()) 
				scannerPackageByFile(packageName + "." + nextFile.getName(), nextFile.getAbsolutePath(), classList);
			else // 如果是文件
				classList.add(FileClassScanner.scannerFile(packageName, nextFile));// 尝试扫描
		}
	}
}

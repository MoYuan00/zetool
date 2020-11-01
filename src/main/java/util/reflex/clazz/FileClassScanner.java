package util.reflex.clazz;

import java.io.File;
import java.io.FileNotFoundException;

public class FileClassScanner {
	/**
	 * 扫描一个class文件
	 * @param packageName 类所在包名
	 * @param file 类文件
	 * @return
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public static Class<?> scannerFile(String packageName, File file) 
			throws ClassNotFoundException, FileNotFoundException{
		//如果是java类文件 去掉后面的.class 只留下类名
		String className = file.getName().substring(0, file.getName().length() - 6);
		System.out.println("add: " + className);
		return Class.forName(packageName + '.' + className);
	}
}

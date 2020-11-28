package com.zetool.beancopy.scanner;

import java.io.File;
import java.io.FileNotFoundException;

import com.zetool.beancopy.util.Log;

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
		Log.info(FileClassScanner.class, "获取到类:" + className);
		return Class.forName(packageName + '.' + className);
	}
}

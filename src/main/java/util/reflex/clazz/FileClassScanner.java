package util.reflex.clazz;

import java.io.File;
import java.io.FileNotFoundException;

public class FileClassScanner {
	/**
	 * ɨ��һ��class�ļ�
	 * @param packageName �����ڰ���
	 * @param file ���ļ�
	 * @return
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public static Class<?> scannerFile(String packageName, File file) 
			throws ClassNotFoundException, FileNotFoundException{
		//�����java���ļ� ȥ�������.class ֻ��������
		String className = file.getName().substring(0, file.getName().length() - 6);
		System.out.println("add: " + className);
		return Class.forName(packageName + '.' + className);
	}
}

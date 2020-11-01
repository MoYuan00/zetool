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
	 * ͨ������ɨ����µ�����.class�ļ�
	 * @param packageName ����
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> packageScanner(String packageName) throws ClassNotFoundException {
		List<Class<?>> classList = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');//��ȡ�������� �������滻
		Enumeration<URL> dirs = null;
        try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);// �����ȡ����url
			while(dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
                String protocol = url.getProtocol();//�õ�Э�������
                if ("file".equals(protocol)) { //��������ļ�����ʽ�����ڷ�������
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");//��ȡ��������·��
                    scannerPackageByFile(packageName, filePath, classList); //���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������
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
	 * ͨ���ļ����Ͱ���ɨ��һ�����µ�����.class�ļ�
	 * @param packageName
	 * @param packagePath
	 * @param classList
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	private static void scannerPackageByFile(String packageName, String packagePath, List<Class<?>> classList) 
					throws FileNotFoundException, ClassNotFoundException{
		// �������ɨ�裺 ���Դ��ݰ���
		File file = new File(packagePath);
		if(!file.exists())
			return;
		for (File nextFile : file.listFiles((ft)->{
			return ft.isDirectory() || ft.getName().endsWith(".class");
			})) {
			if(nextFile.isDirectory()) 
				scannerPackageByFile(packageName + "." + nextFile.getName(), nextFile.getAbsolutePath(), classList);
			else // ������ļ�
				classList.add(FileClassScanner.scannerFile(packageName, nextFile));// ����ɨ��
		}
	}
}

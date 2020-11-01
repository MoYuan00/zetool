package util.reflex.clazz;

import java.io.FileNotFoundException;
import java.util.List;

import util.copy.CopyUtils;

public class ClassScanner {
	/**
	 * ͨ������ɨ��������ڰ��Լ� ���µ������Ӱ���class
	 * @param clazz
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> scanClassPackage(Class<?> clazz) throws FileNotFoundException, ClassNotFoundException{
		String packageName = clazz.getPackage().getName();
		System.out.println("��ǰ����:" + packageName);
		return PackageClassScanner.packageScanner(packageName);// ��ȡ��ǰ�����Լ��Ӱ������Ե���
	}
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
		System.out.println(ClassScanner.scanClassPackage(CopyUtils.class).size());
		System.out.println(PackageClassScanner.packageScanner(CopyUtils.class.getPackage().getName()).size());
	}
}

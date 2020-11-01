package dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;


public class ProxyBuilder {
	/**
	 * 
	 * @param targetClass ԭ���� ��
	 * @param proxyClass ���������
	 * @return Ҫ����Ľӿڶ���
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Object getProxyIntance(Class<?> targetClass, Class<?> proxyClass) 
			throws ClassNotFoundException, InstantiationException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, NoSuchMethodException, SecurityException{
		Object target = targetClass.newInstance(); // ͨ�����Ĭ�Ϲ��캯������ʵ��
		Object hander = proxyClass.getConstructor(Object.class).newInstance(target);// �����������ʵ��
        return Proxy.newProxyInstance(hander.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), (InvocationHandler) hander);// ���ɴ������
	}
	/**
	 * 
	 * @param targetClassName ԭ���� ����
	 * @param proxyClassName 	������� ����
	 * @return Ҫ����Ľӿڶ���
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static Object getProxyIntance(String targetClassName, String proxyClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Class<?> targetClazz = Class.forName(targetClassName);	// ͨ��������ȡ����
		Object target = targetClazz.newInstance();				// ͨ�����Ĭ�Ϲ��캯������ʵ��
		Class<?> proxyClass = Class.forName(proxyClassName);	
		Object hander = proxyClass.getConstructor(Object.class).newInstance(target);// �����������ʵ��
        return  Proxy.newProxyInstance(proxyClass.getClassLoader(), 
        		targetClazz.getInterfaces(), (InvocationHandler) hander);// ���ɴ������
	}
}

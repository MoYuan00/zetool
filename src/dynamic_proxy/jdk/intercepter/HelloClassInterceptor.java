package dynamic_proxy.jdk.intercepter;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * �������������������
 * @author Rnti
 * @param <T>
 *
 */
public class HelloClassInterceptor<T> implements InvocationHandler  {
	private T target;
	/**
	 * @param target ��Ҫ����Ķ���
	 */ 
	public HelloClassInterceptor(T target) {
		this.target = target;
	}
	@Override
	public Object invoke(Object obj, Method method, Object[] arg) throws Throwable {
		System.out.println("before ----" + System.currentTimeMillis());
		Object re = method.invoke(target, arg);						// ����ԭ���󷽷�
		System.out.println("after ----" + System.currentTimeMillis());
		return re;
	}// ������
	
}

package dynamic_proxy.cglib.intercepter;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ����һ������ʵ����
 * @author Rnti
 *
 * @param <T>
 */
public class HelloClassInterceptor<T> implements MethodInterceptor {// ������
	public T target;
	/**
	 * ����һ���������
	 * @param targetObject �������
	 * @return ���ɵĴ��������
	 */
	@SuppressWarnings("unchecked")
	public T createProxyInstance(T targetObject){
		this.target = targetObject;
		Enhancer enhancer = new Enhancer();//�������ɴ������
		enhancer.setSuperclass(this.target.getClass()); //����Ŀ����Ϊ�������ĸ���
		enhancer.setCallback(this); //���ûص�����Ϊ����
		return (T)enhancer.create(); //����һ�����������
	}
	@Override
	public Object intercept(Object obj, Method method, 
			Object[] argvs, MethodProxy methodProxy) throws Throwable {
        System.out.println("begin time -----> "+ System.currentTimeMillis());
        Object returnObj = methodProxy.invoke(target, argvs);			// ����ԭ���󷽷�
        System.out.println("end time -----> "+ System.currentTimeMillis());
        return returnObj;
    }
}

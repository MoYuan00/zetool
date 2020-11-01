package dynamic_proxy.jdk.test;

import java.lang.reflect.InvocationTargetException;

import dynamic_proxy.ProxyBuilder;
import dynamic_proxy.jdk.HelloClass;
import dynamic_proxy.jdk.HelloInterface;
import dynamic_proxy.jdk.intercepter.HelloClassInterceptor;

public class Main {
	public static void main(String[] args)  {
		try {
			HelloInterface proxyInstance = (HelloInterface) ProxyBuilder.getProxyIntance("dynamic_proxy.jdk.HelloClass", 
					"dynamic_proxy.jdk.intercepter.HelloClassInterceptor");
			proxyInstance.hello();
			
			proxyInstance = (HelloInterface) ProxyBuilder.getProxyIntance(HelloClass.class, HelloClassInterceptor.class);
			proxyInstance.hello();
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
}

package com.zetool.beancopy.proxy.jdk.test;

import java.lang.reflect.InvocationTargetException;

import com.zetool.beancopy.proxy.ProxyBuilder;
import com.zetool.beancopy.proxy.jdk.HelloClass;
import com.zetool.beancopy.proxy.jdk.HelloInterface;
import com.zetool.beancopy.proxy.jdk.intercepter.HelloClassInterceptor;


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

package com.zetool.beancopy.proxy.cglib.test;

import com.zetool.beancopy.proxy.cglib.HelloClass;
import com.zetool.beancopy.proxy.cglib.intercepter.HelloClassInterceptor;

public class Main {
	public static void main(String[] args) {
        HelloClass cGsubject = 
        		new HelloClassInterceptor<HelloClass>().createProxyInstance(new HelloClass());// ���ɴ���
        cGsubject.hello();
	}
}

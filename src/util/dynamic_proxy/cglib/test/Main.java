package util.dynamic_proxy.cglib.test;


import util.dynamic_proxy.cglib.HelloClass;
import util.dynamic_proxy.cglib.intercepter.HelloClassInterceptor;

public class Main {
	public static void main(String[] args) {
        HelloClass cGsubject = 
        		new HelloClassInterceptor<HelloClass>().createProxyInstance(new HelloClass());// ���ɴ���
        cGsubject.hello();
	}
}

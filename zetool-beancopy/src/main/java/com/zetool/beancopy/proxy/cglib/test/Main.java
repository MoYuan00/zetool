package dynamic_proxy.cglib.test;


import dynamic_proxy.cglib.HelloClass;
import dynamic_proxy.cglib.intercepter.HelloClassInterceptor;

public class Main {
	public static void main(String[] args) {
        HelloClass cGsubject = 
        		new HelloClassInterceptor<HelloClass>().createProxyInstance(new HelloClass());// 生成代理
        cGsubject.hello();
	}
}

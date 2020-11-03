package com.zetool.beancopy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zetool.beancopy.executor.CopyExecutorAdapter;

public class App {
	public int a = 0;
	public static void show(HashMap<Integer, String> map) {
		System.out.println("hashMap");
	}
	public static void show(Map<Integer, String> map) {
		System.out.println("map");
	}
	
	public static <T>void show(T t) {
		System.out.println("T");
	}
	public static <T extends Serializable>void show(T t) {
		System.out.println("<T extends App>");
	}
	@Override
	public String toString() {
		return "app " + super.toString();
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		System.out.println("运行");
		
		// 对于基本类型 IO拷贝 ，可以直接new对象拷贝比IO要快，要优化实现
		System.out.println(CopyExecutorAdapter.copy(new String("123")));
		System.out.println(CopyExecutorAdapter.copy(123123));
		// 数组 元素实现Serializable 调用 IO拷贝
		System.out.println(Arrays.toString(CopyExecutorAdapter.copy(new int[] {1, 4, 3})));
		
		// 数组元素没有实现Serializable接口， IO拷贝失败
		System.out.println(Arrays.toString(CopyExecutorAdapter.copy(new App[] {new App(), new App()})));
		
		// list 集合中 没有继承 Serializable 接口 IO拷贝失败
		// 思路：对集合中元素依次拷贝
		System.out.println(Arrays.toString(CopyExecutorAdapter.copy(new ArrayList<>(Arrays.asList(new App()))).toArray()));
		
		// IO拷贝 效率低下
		
		show(new HashMap<>());
		show(new LinkedHashMap<>());
		
		show(new App());
		show("1231");
	}
}

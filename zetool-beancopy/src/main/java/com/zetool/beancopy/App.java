package com.zetool.beancopy;

import java.io.Serializable;
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
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		System.out.println("运行");
		
		System.out.println(CopyExecutorAdapter.copy(new String("123")));
		System.out.println(CopyExecutorAdapter.copy(123123));
		
		show(new HashMap<>());
		show(new LinkedHashMap<>());
		
		show(new App());
		show("1231");
	}
}

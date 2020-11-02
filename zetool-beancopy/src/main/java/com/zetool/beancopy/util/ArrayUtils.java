package com.zetool.beancopy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayUtils {
	/**
	 * 将一个数组T[]转换成ArrayList<T>. 如果T[] == null 返回空集合
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T>List<T> toList(T array[]){
		if(array == null) return new ArrayList<>();
		List<T> list = new ArrayList<>(array.length);
		for(T t : array) 
			list.add(t);
		return list;
	}
	
	/**
	 * 将array[]添加到collection中
	 * @param <T>
	 * @param list
	 * @param array
	 */
	public static <T>void addAll(Collection<T> collection, T array[]){
		if(array == null) return;
		for(T t : array)
			collection.add(t);
	}
}

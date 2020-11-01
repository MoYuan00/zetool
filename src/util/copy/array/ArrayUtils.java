package util.copy.array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayUtils {
	/**
	 * ��һ������T[]ת����ArrayList<T>. ���T[] == null ���ؿռ���
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
	 * ��array[]��ӵ�collection��
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

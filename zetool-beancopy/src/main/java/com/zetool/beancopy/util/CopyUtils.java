package com.zetool.beancopy.util;

import util.copy.executor.EqualsObjecCopyExecutor;
import util.copy.executor.UnequalsObjectCopyExecutor;

/**
 * �������󹤾���
 * ��Ҫ��Ž�a������b�ķ���
 * @author Rnti
 *
 */
public class CopyUtils {
	/**
	 * ��Tsource�����¡��Ttarget
	 * @param <Tsource>
	 * @param <Ttarget>
	 * @param sourceObj
	 * @param targetClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static  <Tsource, Ttarget>Ttarget 
		copy(Tsource sourceObj, Class<Ttarget> targetClass) 
			throws InstantiationException, IllegalAccessException{
		return UnequalsObjectCopyExecutor.copyForm(sourceObj, targetClass);
	}
	/**
	 * ��¡һ��T����
	 * @param <T>
	 * @param sourceObj
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T>T copy(T sourceObj) 
			throws InstantiationException, IllegalAccessException{
		return EqualsObjecCopyExecutor.copyForm(sourceObj);
	}
}

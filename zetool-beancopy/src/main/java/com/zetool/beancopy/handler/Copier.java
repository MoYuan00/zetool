package com.zetool.beancopy.handler;

import java.io.Serializable;

/**
 * 拷贝对象工具类 的客服类（外观类，对外提供的接口）
 * 主要存放将a拷贝到b的方法
 * @author Rnti
 *
 */
public class Copier {
	
	/**
	 * 将Tsource对象克隆给Ttarget
	 * @param <Tsource>
	 * @param <Ttarget>
	 * @param sourceObj
	 * @param targetClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static  <Tsource, Ttarget>Ttarget copy(Tsource sourceObj, Class<Ttarget> targetClass) {
		try {
			return UnequalsObjectCopyExecutor.copyFrom(sourceObj, targetClass);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
	
	/**
	 * 克隆一份T对象
	 * @param <T>
	 * @param sourceObj
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T>T copy(T sourceObj) {
		try {
			return EqualsObjecCopyExecutor.copyFrom(sourceObj);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		} 
	}
	
	/**
	 * 克隆一份T对象
	 * @param <T>
	 * @param sourceObj
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T extends Serializable>T copy(T sourceObj) {
		return EqualsObjecCopyExecutor.copyFromWithIO(sourceObj);
	}
}

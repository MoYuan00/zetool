package com.zetool.beancopy.handler;

import com.zetool.beancopy.checkor.EqualsCopyPair;
import com.zetool.beancopy.checkor.UnequalsCopyPair;
import com.zetool.beancopy.helper.ClassHelper;

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
	 * @param <S>
	 * @param <T>
	 * @param sourceObj
	 * @param targetClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static  <S, T>T copy(S sourceObj, Class<T> targetClass) {
		return new UnequalsCopyPair<S, T>(new ClassHelper<S>(sourceObj), new ClassHelper<T>(targetClass)).cloneSourceToTarget(sourceObj);
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
		return new EqualsCopyPair<T, T>(new ClassHelper<>(sourceObj), new ClassHelper<>(sourceObj))
						.cloneSourceToTarget(sourceObj);

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

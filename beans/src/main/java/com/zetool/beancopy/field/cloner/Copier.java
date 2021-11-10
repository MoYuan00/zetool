package com.zetool.beancopy.field.cloner;


import com.zetool.beancopy.bean.cloner.TypeClonerAdapter;
import com.zetool.beancopy.field.checkor.CopyPair;
import com.zetool.beancopy.field.checkor.UnequalsCopyPair;
import com.zetool.beancopy.field.ClassHelper;
import com.zetool.beancopy.util.Log;

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
		Log.debug(CopyPair.class, "copy " + sourceObj.getClass().getTypeName() + " to " + targetClass.getTypeName());
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
		return TypeClonerAdapter.cloneValue(sourceObj.getClass(), sourceObj, 1);
	}
}

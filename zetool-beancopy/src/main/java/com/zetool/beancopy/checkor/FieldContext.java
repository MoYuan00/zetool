package com.zetool.beancopy.checkor;

import java.util.Map;

import com.google.gson.Gson;

/**
 * 封装Field的FieldContext类
 * @author loki02
 * @date 2020年11月30日
 */
public interface FieldContext {
	
	/**
	 * 将一个对象转换为字符串
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if(obj != null) {
			return new Gson().toJson(obj);
		}
		return "null";
	}
	
	/**
	 * 
	 * @return 返回字段名
	 */
	public String getName();
	/**
	 * 
	 * @return 返回字段类型
	 */
	public Class<?> getType();
	
	/**
	 * 是否是final变量
	 * @return
	 */
	public boolean isFinal();
	
	/**
	 * 是否是静态变量
	 * @return
	 */
	public boolean isStatic();
	
	/**
	 * 设置这个是哪个对象的字段，与对象绑定
	 * @return
	 */
	public FieldContext setObject(Object obj);
	
	/**
	 * 
	 * @return 返回字段值
	 */
	public Object getValue();
	
	/**
	 * 设置当前类的字段值
	 * @return 当前类
	 */
	public FieldContext setValue(Object value);
	
	/**
	 * 拷贝当前字段的值并返回
	 * @return 拷贝的字段值
	 */
	public Object cloneValue();
}

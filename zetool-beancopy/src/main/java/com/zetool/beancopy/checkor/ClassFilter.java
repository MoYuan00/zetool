package com.zetool.beancopy.checkor;

import java.util.Collection;

/**
 * 对类进行过滤
 * @author loki02
 * @date 2020年11月30日
 */
public interface ClassFilter {
	
	/**
	 * 对类进行过滤，返回过滤后的集合
	 * @param classes
	 * @return
	 */
	public Collection<Class<?>> filter(Collection<Class<?>> classes);
}

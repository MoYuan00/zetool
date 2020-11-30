package com.zetool.beancopy.checkor;

import java.util.Collection;

/**
 * 属性列表过滤器
 * @author loki02
 * @date 2020年11月30日
 */
public interface FieldContextFilter {
	/**
	 * 过滤出一部分合法的fieldcontext
	 * @param fieldContexts
	 * @return
	 */
	public Collection<FieldContext> filter(Collection<FieldContext> fieldContexts);
}

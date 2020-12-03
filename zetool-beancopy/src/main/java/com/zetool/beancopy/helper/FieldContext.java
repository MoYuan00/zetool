package com.zetool.beancopy.helper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.util.CollectionUtils;

/**
 * 封装Field的FieldContext类
 * @author loki02
 * @date 2020年11月30日
 */
public abstract class FieldContext {
	
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
	public abstract String getName();
	/**
	 * 
	 * @return 返回字段类型
	 */
	public abstract Class<?> getType();
	
	/**
	 * 是否是final变量
	 * @return
	 */
	public abstract boolean isFinal();
	
	/**
	 * 是否是静态变量
	 * @return
	 */
	public abstract boolean isStatic();
	
	/**
	 * 设置这个是哪个对象的字段，与对象绑定
	 * @return
	 */
	public abstract FieldContext setObject(Object obj);
	
	/**
	 * 
	 * @return 返回字段值
	 */
	public abstract Object getValue();
	
	/**
	 * 设置当前类的字段值
	 * @return 当前类
	 */
	public abstract FieldContext setValue(Object value);
	
	/**
	 * 拷贝当前字段的值并返回
	 * @return 拷贝的字段值
	 */
	public abstract Object cloneValue();
	
	private static interface FieldContextFilter {
		/**
		 * 过滤出一部分合法的fieldcontext
		 * @param fieldContexts
		 * @return
		 */
		public Collection<FieldContext> filter(Collection<FieldContext> fieldContexts);
	}

	
	protected static class CopyFromFieldContextFilter implements FieldContextFilter {
		private CopyFrom copyFrom;
		
		public CopyFromFieldContextFilter(CopyFrom copyFrom) {
			this.copyFrom = copyFrom;
			if(copyFrom == null) throw new IllegalArgumentException();
		}
		
		/**
		 * 过滤出集合中的字段，这个字段包含在copyFrom注解的fields中
		 * @param fieldContexts
		 * @return
		 */
		@Override
		public Collection<FieldContext> filter(Collection<FieldContext> fieldContexts) {
			Set<String> fieldNameSet = CollectionUtils.toSet(copyFrom.thisFields());
			List<FieldContext> result = new ArrayList<FieldContext>();
			for (FieldContext fieldContext : fieldContexts) 
				if(fieldNameSet.contains(fieldContext.getName())) result.add(fieldContext);
			return result;
		}
		/**
		 * 过滤出集合中的字段，这个字段包含在copyFrom注解的fields中
		 * @param fieldContexts
		 * @return
		 */
		public Map<String, FieldContext> filter(Map<String, FieldContext> fieldContexts) {
			Set<String> fieldNameSet = CollectionUtils.toSet(copyFrom.thisFields());
			Map<String, FieldContext> result = new HashMap<String, FieldContext>();
			for (String name : fieldContexts.keySet()) 
				if(fieldNameSet.contains(name)) result.put(name, fieldContexts.get(name));
			return result;
		}
	}
}

package com.zetool.beancopy.checkor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.util.CollectionUtils;

public class CopyFromFieldContextFilter implements FieldContextFilter {
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
		Set<String> fieldNameSet = CollectionUtils.toSet(copyFrom.fields());
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
		Set<String> fieldNameSet = CollectionUtils.toSet(copyFrom.fields());
		Map<String, FieldContext> result = new HashMap<String, FieldContext>();
		for (String name : fieldContexts.keySet()) 
			if(fieldNameSet.contains(name)) result.put(name, fieldContexts.get(name));
		return result;
	}

}

package com.zetool.beancopy.checkor;

import java.lang.reflect.Field;

/**
 * 封装 java 自带的Field类
 * 重写equals，hashCode等方法
 * @author Rnti
 *
 */
public class FieldInfo implements Comparable<FieldInfo> {
	private Field field;
	
	public FieldInfo(Field field) {
		this.field = field;
	}
	
	public Field getField() {
		return field;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldInfo other = (FieldInfo) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field)) // 通过名称比较
			return false;
		return true;
	}
	@Override
	public int compareTo(FieldInfo o) {
		return this.field.getName().compareTo(o.field.getName());
	}
}

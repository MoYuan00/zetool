package com.zetool.beancopy.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zetool.beancopy.util.Log;

/**
 * 封装 java 自带的Field类
 * 重写equals，hashCode等方法
 * @author Rnti
 *
 */
public class SimpleFieldHelper extends FieldHelper {
	
	protected Field field;
	private Object obj;
	
	public SimpleFieldHelper(Field field) {
		this.field = field;
		field.setAccessible(true);
	}
	
	public SimpleFieldHelper(Field field, Object obj) {
		this(field);
		this.obj = obj;
	}
	
	@Override
	public String getName() {
		return field.getName();
	}

	@Override
	public Class<?> getType() {
		return field.getType();
	}

	@Override
	public Object getValue() {
		if(obj == null) throw new IllegalArgumentException("object is null, can not get this value!");
		try {
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}

	@Override
	public SimpleFieldHelper setValue(Object value) {
		if(value == null) return this;
		try {
			
			Log.debug(SimpleFieldHelper.class, "setValue that value type is " + value.getClass().getTypeName());
			field.set(obj, value);
			return this;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}

	@Override
	public Object cloneValue() {
		if(obj == null) throw new IllegalArgumentException("object is null, can not clone this value!");
		Object value = getValue();
		if(value == null) return null;
		TypeToken<?> typeToken = TypeToken.get(field.getType());
		Log.debug(SimpleFieldHelper.class, "clone value type is " + typeToken.getType());
		return new Gson().fromJson(new Gson().toJson(value), typeToken.getType());
	}

	@Override
	public boolean isFinal() {
		return Modifier.isFinal(field.getModifiers());
	}

	@Override
	public boolean isStatic() {
		return Modifier.isStatic(field.getModifiers());
	}

	@Override
	public SimpleFieldHelper setObject(Object obj) {
		this.obj = obj;
		return this;
	}
}

package com.zetool.beancopy.checkor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 封装 java 自带的Field类
 * 重写equals，hashCode等方法
 * @author Rnti
 *
 */
public class SimpleFieldContext implements FieldContext {
	
	private Field field;
	private Object obj;
	
	public SimpleFieldContext(Field field) {
		this.field = field;
		field.setAccessible(true);
	}
	
	public SimpleFieldContext(Field field, Object obj) {
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
	public SimpleFieldContext setValue(Object value) {
		try {
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
		TypeToken<?> typeToken = TypeToken.get(field.getType());
		return new Gson().fromJson(new Gson().toJson(getValue()), typeToken.getType());
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
	public SimpleFieldContext setObject(Object obj) {
		this.obj = obj;
		return this;
	}
}

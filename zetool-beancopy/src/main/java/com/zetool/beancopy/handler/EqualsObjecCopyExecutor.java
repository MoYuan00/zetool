package com.zetool.beancopy.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import com.zetool.beancopy.checkor.FieldContext;
import com.zetool.beancopy.checkor.FieldContextBuilder;


class EqualsObjecCopyExecutor {
	/**
	 * 拷贝对象A和B同类型，并且实现了Serializable接口，就直接深度拷贝
	 * @param <T>
	 * @param <T>
	 * @param sourceObj
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T copyFromWithIO(T sourceObj){
		System.out.println("<T extends Serializable> copyFrom");
		if(sourceObj == null) throw new NullPointerException();
		T bInstance = null;
		ByteArrayOutputStream bAOS = new ByteArrayOutputStream(100);
		ObjectOutputStream oOs = null;
		try {
			oOs = new ObjectOutputStream(bAOS);// 将对象写入字节缓存区
			oOs.writeObject(sourceObj); // 写入对象
			oOs.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oOs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ObjectInputStream oIS = null;
		try {
			oIS = new ObjectInputStream(new ByteArrayInputStream(bAOS.toByteArray()));
			bInstance = (T) oIS.readObject();// 读取对象
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bAOS.close();
				oIS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bInstance;
	}
	
	/**
	 * 拷贝对象A和B同类型
	 * @param <T>
	 * @param a
	 * @return 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public static <T>T copyFrom(T sourceObj) throws InstantiationException, IllegalAccessException{
		System.out.println("<T>T copyFrom");
		if(sourceObj == null) throw new NullPointerException("sourceObj is null");
		Object targetObj = sourceObj.getClass().newInstance();
		Map<String, FieldContext> targetMap = FieldContextBuilder.buildSimpleFieldContext(targetObj);
		FieldContextBuilder.buildSimpleFieldContext(sourceObj.getClass()).forEach((name, sourceField) -> {
			try {
				sourceField.setObject(sourceObj);
				Object value = sourceField.cloneValue(); // 拷贝值
				targetMap.get(name).setValue(value);
			} catch (IllegalArgumentException | SecurityException e) {
				e.printStackTrace();
			}
		});
		return (T)targetObj;
	}

}

package util.copy.executor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

import util.reflex.FieldUtil;

public class EqualsObjecCopyExecutor {
	/**
	 * 拷贝对象A和B同类型，并且实现了Serializable接口，就直接深度拷贝
	 * @param <T>
	 * @param a
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable>T copyForm(T a){
		if(a == null) throw new NullPointerException();
		T bInstance = null;
		ByteArrayOutputStream bAOS = new ByteArrayOutputStream(100);
		ObjectOutputStream oOs = null;
		try {
			oOs = new ObjectOutputStream(bAOS);// 将对象写入字节缓存区
			oOs.writeObject(a); // 写入对象
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
	public static <T>T copyForm(T sourceObj) throws InstantiationException, IllegalAccessException{
		if(sourceObj == null) throw new NullPointerException("sourceObj is null");
		T targetObj = (T) sourceObj.getClass().newInstance();
		FieldUtil.getFieldsAsStream(sourceObj).forEach(sourceField->{
			Field targetField = null;
			try {
				targetField = targetObj.getClass().getDeclaredField(sourceField.getName());// 获取targetObj的字段
			} catch (IllegalArgumentException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			try {
				FieldUtil.copy(sourceObj, sourceField, targetObj, targetField);// 设置当前sourceObj对象的targetField
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
		});
		return targetObj;
	}
}

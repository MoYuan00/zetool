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
	 * ��������A��Bͬ���ͣ�����ʵ����Serializable�ӿڣ���ֱ����ȿ���
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
			oOs = new ObjectOutputStream(bAOS);// ������д���ֽڻ�����
			oOs.writeObject(a); // д�����
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
			bInstance = (T) oIS.readObject();// ��ȡ����
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
	 * ��������A��Bͬ����
	 * @param <T>
	 * @param a
	 * @return 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public static <T>T copyForm(T sourceObj) throws InstantiationException, IllegalAccessException{
		if(sourceObj == null) throw new NullPointerException();
		T targetObj = (T) sourceObj.getClass().newInstance();
		FieldUtil.getFieldsAsStream(sourceObj).forEach(sourceField->{
			Field targetField = null;
			try {
				targetField = targetObj.getClass().getDeclaredField(sourceField.getName());// ��ȡtargetObj���ֶ�
			} catch (IllegalArgumentException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			try {
				FieldUtil.copy(sourceObj, sourceField, targetObj, targetField);// ���õ�ǰsourceObj�����targetField
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				e.printStackTrace();
			}
		});
		return targetObj;
	}
}

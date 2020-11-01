package util.copy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.annotation.CopyFrom;
import util.reflex.AnnotationUtil;
import util.reflex.FieldUtil;

/**
 * �������󹤾���
 * ��Ҫ��Ž�a������b�ķ���
 * @author Rnti
 *
 */
public class CopyUtils {
	/**
	 * 1.ʵ����һ��B����Ҫ�޲������죩
	 * 2.ͨ����A��ʵ���Լ���B�����ͣ���A��ĳЩ�ֶ�ֵ������B
	 * 3.��Щ�ֶ�ֵ�� CopyForm �ṩ����ʾ
	 * @param <T>
	 * @param a
	 * @param b
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <Ta,Tb>Tb copyForm(Ta a, Class<Tb> b) throws InstantiationException, IllegalAccessException {
		if(a == null || b == null) throw new NullPointerException();
		final Tb bInstance = b.newInstance();
		List<Field> bFieldList =  FieldUtil.getFields(bInstance);// ��ȡB�������
//		Set<Field> aFieldSet =  new HashSet<Field>(FieldUtil.getFields(a));
		CopyFrom bCopyFrom = b.getDeclaredAnnotation(CopyFrom.class);// ��ȡ��ӳ��ע��
		if(bCopyFrom != null) {
			// ��ȡ��ӳ�伯�� b -> a	// b��Ҫ��a���ĸ��ֶο���
			bFieldList = AnnotationUtil.getNeedCopyList(bFieldList, bCopyFrom);
			System.out.println("��Ҫ�����ֶμ���:" + Arrays.toString(bFieldList.toArray()));
			bFieldList.forEach((bField)->{
//				Field aField = aFieldSet.stream().filter((aF)->aF.getName().equals(bField.getName())).findFirst().get();
				Field aField = null;
				try {
					aField = a.getClass().getDeclaredField(bField.getName());// ͨ��b��������ȡa����
				} catch (NoSuchFieldException | SecurityException e1) {
					e1.printStackTrace();
				}
				// ���ж��Ƿ��ҵ�
				System.out.println("�ҵ���Ӧ�ֶ�:" + aField.getName());
				aField.setAccessible(true);// ����Ϊ���Է���
				bField.setAccessible(true);
				try {
					Object fieldVal = aField.get(a);// ��ȡָ��a����ĵ�ǰ�ֶε�ֵ
					System.out.println("��ȡa��ֵΪ:" + fieldVal);
					bField.set(bInstance, fieldVal);// ���õ�ǰb����ֵ
				} catch (IllegalArgumentException | IllegalAccessException 
						| SecurityException e) {
					e.printStackTrace();
				}
			});
		}
		return bInstance;
	}
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
	public static <T>T copyForm(T obj) throws InstantiationException, IllegalAccessException{
		if(obj == null) throw new NullPointerException();
		T newObj = (T) obj.getClass().newInstance();
		FieldUtil.getFieldsAsStream(obj).forEach(field->{
			field.setAccessible(true);// ����Ϊ���Է���
			Object fieldVal = null;
			try {
				fieldVal = field.get(obj);// ��ȡָ��obj����ĵ�ǰ�ֶε�ֵ
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println("��ȡobj��ֵΪ:" + fieldVal);
			try {
				Field newObjField = newObj.getClass().getDeclaredField(field.getName());
				newObjField.setAccessible(true);
				newObjField.set(newObj, fieldVal);// ���õ�ǰnewObj����ֵ
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		});
		return newObj;
	}
}

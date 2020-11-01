package util.reflex;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldUtil {
	/**
	 * ��sourceObj�����sourceField�ֶ�ֵ�� ������targetObj�����targetField�ֶ�
	 * @param sourceObj Դ����
	 * @param sourceField Դ�ֶ�
	 * @param targetObj Ŀ�����
	 * @param targetField Ŀ���ֶ�
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void copy(Object sourceObj, Field sourceField, 
			Object targetObj, Field targetField) throws IllegalArgumentException, IllegalAccessException {
		assert sourceObj != null && sourceField != null && targetObj != null && targetField != null;
		sourceField.setAccessible(true);				// ����Ϊ���Է���
		targetField.setAccessible(true);
		// ����ֻ�ǻ�ȡ��ֵ����û�������Ŀ���һ�ݣ�������Ҫ�ݹ��¡������
		Object fieldVal = sourceField.get(sourceObj);	// ��ȡָ��sourceObj�����sourceField�ֶε�ֵ
		targetField.set(targetObj, fieldVal);			// ����targetField����ֵ
	}
	/**
	 * ��ʾһ������������ֶ�ֵ
	 * @param obj
	 */
	public static void showFieldsValue(Object obj) {
		if(obj == null) throw new NullPointerException();
		System.out.print(obj.getClass() + ":{");
		getFields(obj).forEach((field)->{
			try {
				field.setAccessible(true);
				System.out.print(field.getName() + ":" + field.get(obj) + ", ");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		System.out.println("}");
	}
	/**
	 * ��ȡȫ�������ԣ��ֶΣ�
	 * @param clazz ����
	 * @return
	 */
	public static List<Field> getFields(Object obj) {
		if(obj == null) throw new NullPointerException();
		return getFields(obj.getClass());
	}
	/**
	 * ��ȡȫ�������ԣ��ֶΣ�
	 * @param clazz ����
	 * @return
	 */
	public static Stream<Field> getFieldsAsStream(Object obj) {
		if(obj == null) throw new NullPointerException();
		return getFieldsAsStream(obj.getClass());
	}
	/**
	 * ��ȡȫ�������ԣ��ֶΣ�
	 * @param clazz ����
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
//		while(clazz.getSuperclass() != null) {// ������Ҫ������ֶ�Ҳ���
//			fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
//			clazz = clazz.getSuperclass();
//		}
		return fieldList;
	}
	/**
	 * ��ȡȫ�������ԣ��ֶΣ�
	 * @param clazz ����
	 * @return
	 */
	public static Stream<Field> getFieldsAsStream(Class<?> clazz) {
		return Arrays.asList(clazz.getDeclaredFields()).stream();
	}
	/**
	 * ���˳�����ָ��ע�⼯��**ȫ��ע��**���ֶ�
	 * @param fieldList �������ֶμ���
	 * @param as ע��
	 * @return
	 */
	public static List<Field> filterFields(List<Field> fieldList, Annotation[] as) {
		return fieldList.stream().filter((field)->{
			List<Annotation> fAs = Arrays.asList(field.getDeclaredAnnotations());// ��ǰ�ֶε�ע��
			int i = 0, len = as.length;
			for(; i < len; i++) {// ����Ƿ�����Ҫ�������ֶ� ������
				final int t = i;
				if(!fAs.stream().anyMatch( 
						(a)->{ return a.getClass().isAnnotationPresent(as[t].getClass()); }
						)
				) {
					break;// ������
				}
			}
			return i == len;
		}).collect(Collectors.toList());
	}
	
}

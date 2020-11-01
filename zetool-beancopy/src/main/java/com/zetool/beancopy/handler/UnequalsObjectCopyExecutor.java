package com.zetool.beancopy.handler;

import util.annotation.CopyFrom;
import util.reflex.FieldUtil;
import util.reflex.anntation.AnnotationUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * ��ͬ��֮��Ŀ���ִ����
 * @author Rnti
 *
 */
public class UnequalsObjectCopyExecutor {
	/**
	 * 1.ʵ����һ��B����Ҫ�޲������죩
	 * 2.ͨ����A��ʵ���Լ���B�����ͣ���A��ĳЩ�ֶ�ֵ������B
	 * 3.��Щ�ֶ�ֵ�� CopyForm �ṩ����ʾ
	 * @param <T>
	 * @param sourceObj
	 * @param targetClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <Tsource,Ttarget>Ttarget copyForm(Tsource sourceObj, Class<Ttarget> targetClass) throws InstantiationException, IllegalAccessException {
		if(sourceObj == null || targetClass == null) throw new NullPointerException();
		final Ttarget targetObj = targetClass.newInstance();
		List<Field> targetFieldList =  FieldUtil.getFields(targetObj);				// ��ȡtargetClass������
		CopyFrom bCopyFrom = targetClass.getDeclaredAnnotation(CopyFrom.class);		// ��ȡ��ӳ��ע��
		if(bCopyFrom != null) {
			// ��ȡ��ӳ�伯�� b -> a	// b��Ҫ��a���ĸ��ֶο���
			targetFieldList = AnnotationUtil.getNeedCopyList(targetFieldList, bCopyFrom);
			System.out.println("��Ҫ�����ֶμ���:" + Arrays.toString(targetFieldList.toArray()));
			
			targetFieldList.forEach((targetField)->{// ��ÿ������ֵ
				Field sourceField = null;
				try {
					sourceField = sourceObj.getClass().getDeclaredField(targetField.getName());//ͨ��targetField��������ȡsourceField����
				} catch (NoSuchFieldException | SecurityException e1) {
					e1.printStackTrace();
				}
				// TODO ���ﲻ�ж��Ƿ��ҵ�
				System.out.println("�ҵ���Ӧ�ֶ�:" + sourceField.getName());
				try {
					FieldUtil.copy(sourceObj, sourceField, targetObj, targetField); // �����ֶ�
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		}
		return targetObj;
	}
}

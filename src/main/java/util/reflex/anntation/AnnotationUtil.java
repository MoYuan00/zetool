package util.reflex.anntation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import util.annotation.CopyFrom;
import util.annotation.method.CopyTo;

public class AnnotationUtil {
	/**
	 * ��ȡ��sourceClass��Ҫ�����ֶεļ���
	 * @param sourceClass
	 * @param bCopyFormA
	 * @return List<Field> b��Ҫ�����ֶεļ���
	 */
	public static List<Field> getNeedCopyList(Class<?> sourceClass
			, CopyTo sourceCopyToTarget){
		return getNeedCopyList(Arrays.asList(sourceClass.getDeclaredFields()), sourceCopyToTarget);
	}
	/**
	 * ��ȡ��sourceField��Ҫ�����ֶεļ���
	 * @param sourceField
	 * @param bCopyFormA
	 * @return List<Field> b��Ҫ�����ֶεļ���
	 */
	public static List<Field> getNeedCopyList(List<Field> sourceField
			, CopyTo sourceCopyToTarget){
		assert sourceField != null && sourceCopyToTarget != null;
		Set<String> needCopyFieldStringSet = new HashSet<>(
				Arrays.asList(sourceCopyToTarget.sourceField()));
		return sourceField.stream().filter(
				(field)->{return needCopyFieldStringSet.contains(field.getName());}// ���Ҫ�����ֶ��а��� field�ͱ���
				)
				.collect(Collectors.toList());
	}
	
	
	
	
	/**
	 * ��ȡ��b��Ҫ�����ֶεļ���
	 * @param targetClass
	 * @param targetCopyFromSource
	 * @return List<Field> b��Ҫ�����ֶεļ���
	 */
	public static List<Field> getNeedCopyList(Class<?> targetClass
			, CopyFrom targetCopyFromSource){
		return getNeedCopyList(targetClass.getDeclaredFields(), targetCopyFromSource);
	}
	
	/**
	 * ��ȡ��targetFieldList��Ҫ�����ֶεļ���
	 * @param targetFieldList
	 * @param bCopyFormA
	 * @return List<Field> targetFieldList��Ҫ�����ֶεļ���
	 */
	public static List<Field> getNeedCopyList(Field[] targetFieldList
			, CopyFrom bCopyFromA){
		assert targetFieldList != null;
		return getNeedCopyList(Arrays.asList(targetFieldList), bCopyFromA);
	}
	/**
	 * ��ȡ��b��Ҫ�����ֶεļ���
	 * @param bFieldList
	 * @param bCopyFormA
	 * @return List<Field> b��Ҫ�����ֶεļ���
	 */
	public static List<Field> getNeedCopyList(List<Field> bFieldList
			, CopyFrom targetCopyFromSource){
		Set<String> needCopyFieldStringSet = new HashSet<>(Arrays.asList(targetCopyFromSource.fields()));
		return bFieldList.stream().filter(
				(field)->{return needCopyFieldStringSet.contains(field.getName());}// ���Ҫ�����ֶ��а��� field�ͱ���
				)
				.collect(Collectors.toList());
	}
}

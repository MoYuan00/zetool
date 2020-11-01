package util.reflex;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import util.annotation.CopyFrom;

public class AnnotationUtil {
//	/**
//	 * ��ȡ��ӳ�伯�� b -> a	// b��Ҫ��a���ĸ��ֶο���
//	 * @param a Ŀ����a
//	 * @param aFieldList
//	 * @param bFieldList
//	 * @return
//	 */
//	public static Map<Field, String> getFieldMap(List<Field> aFieldList, List<Field> bFieldList
//			, CopyForm bCopyFormA){
//		 bCopyFormA.fields();
//		
//		return null;
//	}
	/**
	 * ��ȡ��b��Ҫ�����ֶεļ���
	 * @param bFieldList
	 * @param bCopyFormA
	 * @return List<Field> b��Ҫ�����ֶεļ���
	 */
	public static List<Field> getNeedCopyList(List<Field> bFieldList
			, CopyFrom bCopyFromA){
		Set<String> needCopyFieldStringSet = new HashSet<>(Arrays.asList(bCopyFromA.fields()));
		return bFieldList.stream().filter(
				(field)->{return needCopyFieldStringSet.contains(field.getName());}// ���Ҫ�����ֶ��а��� field�ͱ���
				)
				.collect(Collectors.toList());
	}
}

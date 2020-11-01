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
//	 * 获取到映射集合 b -> a	// b需要从a的哪个字段拷贝
//	 * @param a 目标类a
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
	 * 获取到b需要拷贝字段的集合
	 * @param bFieldList
	 * @param bCopyFormA
	 * @return List<Field> b需要拷贝字段的集合
	 */
	public static List<Field> getNeedCopyList(List<Field> bFieldList
			, CopyFrom bCopyFromA){
		Set<String> needCopyFieldStringSet = new HashSet<>(Arrays.asList(bCopyFromA.fields()));
		return bFieldList.stream().filter(
				(field)->{return needCopyFieldStringSet.contains(field.getName());}// 如果要拷贝字段中包含 field就保留
				)
				.collect(Collectors.toList());
	}
}

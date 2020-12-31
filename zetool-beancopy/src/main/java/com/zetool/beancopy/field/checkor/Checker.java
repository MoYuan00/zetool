package com.zetool.beancopy.field.checkor;

import java.util.Set;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.field.ClassHelper;
import com.zetool.beancopy.field.ClassesHelper;
import com.zetool.beancopy.io.ClassScanner;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;

/**
 * 判断注解是否注解正确
 * @author Rnti
 *
 */
public class Checker {
	/**
	 * 判断所有的类是否注解关联正确
	 * @param classSet 注解类，和被注解的类（target和source）
	 * @return
	 */
	public static boolean check(ClassesHelper classSet) {
		Log.info(Checker.class, "check begin!");
		// 获取所有 具有注解的类
		Set<ClassHelper<?>> needCopyClassSet =
				CollectionUtils.trans(classSet.filterWithAnnotation(CopyFrom.class), ClassHelper::new);
		Log.debug(Checker.class, "is appended annotation has " + needCopyClassSet.size());
		
		// 1. 判断所有注解中的类是否存在
		needCopyClassSet.forEach((c) -> {
			for(CopyFrom from : c.getAnnotations(CopyFrom.class)) {
				if(classSet.contains(from.sourceClass())) {
					Log.debug(Checker.class, "class exist:" + from.sourceClass().getName());
				} else {
					throw new RuntimeException("class not exist");
				}
			}
		});
		
		// 2. 判断所有的注解是否匹配
		needCopyClassSet.forEach((targetClassHelper)->{
			for(CopyFrom from : targetClassHelper.getAnnotations(CopyFrom.class)) {
				UnequalsCopyPair<?, ?> copyPair = new UnequalsCopyPair<>(new ClassHelper<>(from.sourceClass()), targetClassHelper, from);
				Log.debug(Checker.class, "check mirror:" + from.sourceClass() + " <- " + targetClassHelper.getClassName());
				if(copyPair.check()) {
					Log.debug(Checker.class, "check success!");
				}else {
					throw new IllegalArgumentException("check error!");
				}
			}
		});
		Log.info(Checker.class, "check completed and success!");
		return true;
	}
	
	public static boolean check(ClassScanner scanner) {
		return check(scanner.getClasses());
	}
}

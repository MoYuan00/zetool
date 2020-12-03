package com.zetool.beancopy.checkor;

import java.util.Set;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.handler.ClassScanner;
import com.zetool.beancopy.helper.ClassHelper;
import com.zetool.beancopy.helper.ClassesHelper;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;

/**
 * 判断注解是否注解正确
 * @author Rnti
 *
 */
public class Checkor {
	/**
	 * 判断所有的类是否注解关联正确
	 * @param classSet 注解类，和被注解的类（target和source）
	 * @return
	 */
	public static boolean check(ClassesHelper classSet) {
		Log.info(Checkor.class, "开始检查！");
		// 获取所有 具有注解的类
		Set<ClassHelper<?>> needCopyClassSet = CollectionUtils.trans(classSet.getClassesByAnnotation(CopyFrom.class),
						t -> new ClassHelper<>(t) );
		Log.debug(Checkor.class, "被注解的类共" + needCopyClassSet.size() + "个！");
		
		// 1. 判断所有注解中的类是否存在
		needCopyClassSet.forEach((c) -> {
			for(CopyFrom from : (CopyFrom[])c.getAnnotations(CopyFrom.class)) {
				if(classSet.contains(from.sourceClass())) {
					Log.debug(Checkor.class, "注解中的类存在：" + from.sourceClass().getName());
				} else {
					throw new RuntimeException("注解中的类不存在");
				}
			}
		});
		
		// 2. 判断所有的注解是否匹配
		needCopyClassSet.forEach((targetClassHelper)->{
			for(CopyFrom from : targetClassHelper.getAnnotations(CopyFrom.class)) {
				CopyPair<?, ?> copyPair = new CopyPair<>(new ClassHelper<>(from.sourceClass()), targetClassHelper, from);
				Log.debug(Checkor.class, "检查映射：" + from.sourceClass() + " <- " + targetClassHelper.getClassName()); 
				if(copyPair.check()) {
					Log.debug(Checkor.class, "注解匹配成功：" +  targetClassHelper.getClassName() + ":" + from.sourceClass().getName());
				}else {
					throw new IllegalArgumentException("注解配置错误！" 
								+ targetClassHelper.getClassName() + ":" + from.sourceClass());
				}
			}
		});
		Log.info(Checkor.class, "通过检查！");
		return true;
	}
	
	public static boolean check(ClassScanner scanner) {
		return check(scanner.getClasses());
	}
}

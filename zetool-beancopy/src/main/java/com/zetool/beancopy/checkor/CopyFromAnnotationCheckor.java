package com.zetool.beancopy.checkor;

import java.util.Map;
import java.util.Set;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.handler.ClassHelper;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;

/**
 * 判断注解是否注解正确
 * @author Rnti
 *
 */
public class CopyFromAnnotationCheckor {
	/**
	 * 检查一个映射
	 * @author loki02
	 * @date 2020年12月1日
	 */
	private static class CopyPair{
		private ClassHelper sourceClazz;
		private ClassHelper targetClazz;
		private CopyFrom copyFrom;
		public CopyPair(ClassHelper sourceClazz, ClassHelper targetClazz, CopyFrom copyFrom) {
			super();
			this.sourceClazz = sourceClazz;
			this.targetClazz = targetClazz;
			this.copyFrom = copyFrom;
		}
		/**
		 * 检查source 和 target对于注解copyfrom是否匹配
		 * @return
		 */
		public boolean check() {
			if(sourceClazz.classIsNull() || targetClazz.classIsNull() || copyFrom == null) throw new NullPointerException();
			if(!checkTargetFields()) return false;
			CopyFromFieldContextFilter filter = new CopyFromFieldContextFilter(copyFrom);
			Map<String, FieldContext> sourceFieldMap = sourceClazz.getFieldContexts();
			Map<String, FieldContext> targetFieldMap = filter.filter(targetClazz.getFieldContexts());
			Log.info(CopyPair.class, "检查source:" + sourceClazz.getClassName() + " <- target" + targetClazz.getClassName());
			for (String fieldName : targetFieldMap.keySet()) {
				if(sourceFieldMap.containsKey(fieldName)) {// TODO 只判断名字是否相同 TODO 实际上还需要检查是否为final类型
					Log.info(CopyPair.class, "注解中的属性[" + fieldName +  "]存在source[" + sourceClazz.getClassName() + "中]");
				}else {
					Log.error(CopyPair.class, "注解中的属性[" + fieldName +  "]不存在source:[" + sourceClazz.getClassName() + "]中");
					return false;
				}
			}
			return true;
		}
		
		/**
		 * 检查注解中的所有属性 targetClazz中是否都存在
		 * TODO 实际上还需要检查是否为final类型
		 * @return
		 */
		private boolean checkTargetFields() {
			Log.info(CopyPair.class, "检查注解中的所有属性target本身是否存在:[" + targetClazz.getClassName() + "]");
			Map<String, FieldContext> targetFieldMap = targetClazz.getFieldContexts();
			for(String name : copyFrom.fields()) {
				if(targetFieldMap.keySet().contains(name)) {
					Log.info(CopyPair.class, "注解中的属性[" + name +  "]存在target[" + targetClazz.getClassName() + "]中");
				}else {
					Log.error(CopyPair.class, "注解中的属性[" + name +  "]不存在target[" + targetClazz.getClassName() + "]中");
					return false;
				}
			}
			return true;
		}
		
	}
	
	/**
	 * 判断所有的类是否注解关联正确
	 * @param classSet 注解类，和被注解的类（target和source）
	 * @return
	 */
	public static boolean check(Set<Class<?>> classSet) {
		Log.info(CopyFromAnnotationCheckor.class, "开始检查！");
		// 获取所有 具有注解的类
		Set<ClassHelper> needCopyClassSet = 
				CollectionUtils.trans(new AnnotationClassFilter(CopyFrom.class).filter(classSet),
						t -> new ClassHelper(t) );
		Log.info(CopyFromAnnotationCheckor.class, "被注解的类共" + needCopyClassSet.size() + "个！");
		
		// 1. 判断所有注解中的类是否存在
		needCopyClassSet.forEach((c) -> {
			for(CopyFrom from : c.getAnnotations(CopyFrom.class)) {
				if(classSet.contains(from.sourceClass())) {
					Log.info(CopyFromAnnotationCheckor.class, "注解中的类存在：" + from.sourceClass().getName());
				} else {
					throw new RuntimeException("注解中的类不存在");
				}
			}
		});
		
		// 2. 判断所有的注解是否匹配
		needCopyClassSet.forEach((targetClassHelper)->{
			for(CopyFrom from : targetClassHelper.getAnnotations(CopyFrom.class)) {
				CopyPair copyPair = new CopyPair(new ClassHelper(from.sourceClass()), targetClassHelper, from);
				if(copyPair.check()) {
					Log.info(CopyFromAnnotationCheckor.class, "注解匹配成功：" +  targetClassHelper.getClassName() + ":" + from.sourceClass().getName());
				}else {
					throw new IllegalArgumentException("注解配置错误！" 
								+ targetClassHelper.getClassName() + ":" + from.sourceClass());
				}
			}
		});
		Log.info(CopyFromAnnotationCheckor.class, "通过检查！");
		return true;
	}
}

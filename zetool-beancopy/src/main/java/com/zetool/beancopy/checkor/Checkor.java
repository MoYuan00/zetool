package com.zetool.beancopy.checkor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.handler.ClassScanner;
import com.zetool.beancopy.helper.ClassHelper;
import com.zetool.beancopy.helper.ClassesHelper;
import com.zetool.beancopy.helper.FieldContext;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;

/**
 * 判断注解是否注解正确
 * @author Rnti
 *
 */
public class Checkor {
	/**
	 * 检查一个映射
	 * @author loki02
	 * @date 2020年12月1日
	 */
	private static class CopyPair{
		/**
		 * 源 即 target类上注解标注的类
		 */
		private ClassHelper<?> sourceClazz;
		/**
		 * 目标类
		 */
		private ClassHelper<?> targetClazz;
		/**
		 * 目标类上的注解
		 */
		private CopyFrom copyFrom;
		
		public CopyPair(ClassHelper<?> sourceClazz, ClassHelper<?> targetClazz, CopyFrom copyFrom) {
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
			Map<String, FieldContext> sourceFieldMap = sourceClazz.getFieldContexts();
			Map<String, FieldContext> targetFieldMap = targetClazz.getFieldContextsByCopyFrom(copyFrom);
			Log.info(CopyPair.class, "检查source:" + sourceClazz.getClassName() + " <- target" + targetClazz.getClassName());
			
			List<FieldContextPair> fieldPairList = new ArrayList<>(); // 存放映射关系
			for (String fieldName : targetFieldMap.keySet()) {
				if(sourceFieldMap.containsKey(fieldName)) {// TODO 只判断名字是否相同 TODO 实际上还需要检查是否为final类型
					Log.info(CopyPair.class, "注解中的属性[" + fieldName +  "]存在source[" + sourceClazz.getClassName() + "中]");
					fieldPairList.add(new FieldContextPair(sourceFieldMap.get(fieldName), targetFieldMap.get(fieldName)));
				}else {
					Log.error(CopyPair.class, "类型不匹配： 注解中的属性[" + fieldName +  "]不存在source:[" + sourceClazz.getClassName() + "]中");
					return false;
				}
			}
			if(!checkTypeIsEquals(fieldPairList)) return false;
			return true;
		}
		
		/**
		 * 判断source和target对应的类型是否相同
		 * @return
		 */
		private boolean checkTypeIsEquals(List<FieldContextPair> pairList) {
			for(FieldContextPair pari : pairList) {
				if(pari.isMatch()) {
					Log.info(CopyPair.class, pari.getTargetFC().getName() + " is " + pari.getTargetFC().getType() 
					+ "\n" + pari.getSourceFC().getName() + " is " + pari.getSourceFC().getType());
				} else {
					Log.error(CopyPair.class, "类型不兼容：" + pari.getTargetFC().getName() + " is " + pari.getTargetFC().getType() 
							+ "\n" + pari.getSourceFC().getName() + " is " + pari.getSourceFC().getType());
					return false;
				}
			}
			return true;
		}
		
		/**
		 * 检查注解中的所有属性 targetClazz中是否都存在
		 * 检查是否为final类型，如果是抛出异常
		 * 检查是否为static类型，如果是警告
		 * @return
		 */
		private boolean checkTargetFields() {
			Log.info(CopyPair.class, "检查注解中的所有属性target本身是否存在:[" + targetClazz.getClassName() + "]");
			Map<String, FieldContext> targetFieldMap = targetClazz.getFieldContexts();
			for(String name : copyFrom.fields()) {
				FieldContext fieldContext = targetFieldMap.get(name);
				if(fieldContext != null) {
					Log.info(CopyPair.class, "注解中的属性[" + name +  "]存在target[" + targetClazz.getClassName() + "]中");
					if(fieldContext.isFinal()) {
						Log.error(CopyPair.class, targetClazz.getClassName() + "注解中的属性[" + name +  "]是 final 类型");
						throw new IllegalStateException(targetClazz.getClassName() + "注解中的属性[" + name +  "]是 final 类型");
					}
					if(fieldContext.isStatic()) {
						Log.worn(CopyPair.class, targetClazz.getClassName() + "注解中的属性[" + name +  "]是 static 类型");
					}
				}else {
					Log.error(CopyPair.class, "注解中的属性[" + name +  "]不存在target[" + targetClazz.getClassName() + "]中");
					return false;
				}
			}
			return true;
		}
		
		/**
		 * 一对属性映射关系
		 * @author loki02
		 * @date 2020年12月2日
		 */
		private static class FieldContextPair{
			private FieldContext sourceFC;
			private FieldContext targetFC;
			public FieldContextPair(FieldContext sourceFC, FieldContext targetFC) {
				super();
				this.sourceFC = sourceFC;
				this.targetFC = targetFC;
			}
			public FieldContext getSourceFC() {
				return sourceFC;
			}
			public FieldContext getTargetFC() {
				return targetFC;
			}
			/**
			 * 是否相匹配（可互相转换：当前是否支持相互拷贝）
			 * @return
			 */
			public boolean isMatch() {
				return sourceFC.getType().equals(targetFC.getType());
			}
		}
	}
	
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
		Log.info(Checkor.class, "被注解的类共" + needCopyClassSet.size() + "个！");
		
		// 1. 判断所有注解中的类是否存在
		needCopyClassSet.forEach((c) -> {
			for(CopyFrom from : (CopyFrom[])c.getAnnotations(CopyFrom.class)) {
				if(classSet.contains(from.sourceClass())) {
					Log.info(Checkor.class, "注解中的类存在：" + from.sourceClass().getName());
				} else {
					throw new RuntimeException("注解中的类不存在");
				}
			}
		});
		
		// 2. 判断所有的注解是否匹配
		needCopyClassSet.forEach((targetClassHelper)->{
			for(CopyFrom from : targetClassHelper.getAnnotations(CopyFrom.class)) {
				CopyPair copyPair = new CopyPair(new ClassHelper<>(from.sourceClass()), targetClassHelper, from);
				if(copyPair.check()) {
					Log.info(Checkor.class, "注解匹配成功：" +  targetClassHelper.getClassName() + ":" + from.sourceClass().getName());
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

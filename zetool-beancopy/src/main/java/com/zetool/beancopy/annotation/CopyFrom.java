package com.zetool.beancopy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 * 如果你需要从A对象拷贝字段值到B，那么你需要再B的类上面标注此注解。并且标明B中哪些字段值需要从A拷贝
 * @author Rnti
 *
 */
/**
 * 
 * @author loki02
 * @date 2020年12月3日
 */
@Repeatable(CopyFroms.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyFrom {
	
	/**
	 * 值：拷贝来源于哪里
	 * @return
	 */
	Class<?> sourceClass();
	
	/**
	 * 标注需要拷贝的字段
	 * 值：当前类有哪些字段需要拷贝
	 * 默认拷贝全部属性。
	 * @return
	 */
	String[] thisFields() default {};
	
	/**
	 * 使用的映射类型
	 * 默认DEFAULT
	 * @return
	 */
	MirrorType mirrorType() default MirrorType.DEFAULT;
	
	/**
	 * 要排除在外的字段
	 * （某些情况下需要拷贝大部分字段，只有少数几个字段不需要拷贝，那么可以考虑这个选项，然后配合默认拷贝所有字段使用）
	 * @return
	 */
	String[] exceptThisFields() default {};
	
	/**
	 * 映射类型
	 * @author loki02
	 * @date 2020年12月3日
	 */
	public static enum MirrorType{
		DEFAULT,
		UNDER_LINE_TO_HUMP,
		HUMP_TO_UNDER_LINE
	}
}

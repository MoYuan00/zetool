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
	String[] fields() default {};
	
	

}

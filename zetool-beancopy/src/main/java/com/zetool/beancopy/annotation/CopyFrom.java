package util.annotation;

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
	Class<?> fromClass();// 拷贝来源于哪里
	String[] fields() default {};// 当前类有哪几个字段需要进行copy
}

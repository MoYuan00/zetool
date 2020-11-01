package util.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 * 标注在拷贝源sourceClass的某个方法上
 * 例如: 拷贝源sourceClass的方法copyToTarget标注了这个注解，那么需要标注
 * 1 拷贝目标的类型targetClass 
 * 2 以及拷贝源sourceClass有哪些字段需要拷贝过去
 * @author Rnti
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CopyTo {
	/**
	 * @return 拷贝目标的类型targetClass 
	 */
	Class<?> targetClass();
	/**
	 * @return 拷贝源sourceClass有哪些字段需要拷贝过去
	 */
	String[] sourceField(); 
}

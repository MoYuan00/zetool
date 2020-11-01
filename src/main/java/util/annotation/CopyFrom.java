package util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ע��
 * �������Ҫ��A���󿽱��ֶ�ֵ��B����ô����Ҫ��B���������ע��ע�⡣���ұ���B����Щ�ֶ�ֵ��Ҫ��A����
 * @author Rnti
 *
 */
@Repeatable(CopyFroms.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyFrom {
	Class<?> fromClass();// ������Դ������
	String[] fields() default {};// ��ǰ�����ļ����ֶ���Ҫ����copy
}

package proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ע��
 * �������Ҫ��A���󿽱��ֶ�ֵ��B��
 * 1. ����Ҫʵ��ReplicableI�ӿ�
 * 2. ����Ҫ��B��copyFrom���������ע��ע�⡣���ұ���B����Щ�ֶ�ֵ��Ҫ��A����
 * @author Rnti
 *
 */
@Repeatable(CopyFroms.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CopyFrom {
	Class<?> fromClass();// ������Դ������
	String[] fields() default {};// ��ǰ�����ļ����ֶ���Ҫ����copy
}

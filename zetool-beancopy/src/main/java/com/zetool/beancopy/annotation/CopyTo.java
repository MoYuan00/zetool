package com.zetool.beancopy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ע��
 * ��ע�ڿ���ԴsourceClass��ĳ��������
 * ����: ����ԴsourceClass�ķ���copyToTarget��ע�����ע�⣬��ô��Ҫ��ע
 * 1 ����Ŀ�������targetClass 
 * 2 �Լ�����ԴsourceClass����Щ�ֶ���Ҫ������ȥ
 * @author Rnti
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CopyTo {
	/**
	 * @return ����Ŀ�������targetClass 
	 */
	Class<?> targetClass();
	/**
	 * @return ����ԴsourceClass����Щ�ֶ���Ҫ������ȥ
	 */
	String[] sourceField(); 
}

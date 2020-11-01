package com.zetool.beancopy.annotation;

import java.lang.annotation.*;

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

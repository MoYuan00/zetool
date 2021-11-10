package com.zetool.beancopy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使CopyFrom支持多次注解
 * CopyFrom可注解多次
 * @author Rnti
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyFroms {
	CopyFrom[] value();
}

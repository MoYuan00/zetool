package com.zetool.beancopy.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使得CloneBeanFor可以被一个类多次注解
 */
@Target(ElementType.TYPE)// 可以注解在类上
@Retention(RetentionPolicy.RUNTIME)// 运行时可以获取到
public @interface CloneBeansFor {
    CloneBeanFor[] value();
}

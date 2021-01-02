package com.zetool.beancopy.bean.property;

public class BeanAnnotationException extends RuntimeException {
    public BeanAnnotationException(String message) {
        super("注解匹配异常：" + message);
    }
}

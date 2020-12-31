package com.zetool.beancopy.field.cloner;

/**
 * 字符串类型克隆器
 */
public class StringTypeCloner extends TypeCloner {

    @Override
    public <T> T cloneValue(T t) {
        return t;
    }
}

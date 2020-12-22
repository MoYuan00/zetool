package com.zetool.beancopy.handler;

/**
 * 字符串类型克隆器
 */
public class StringTypeCloner implements TypeCloner {

    @Override
    public <T> T cloneValue(T t) {
        return t;
    }
}

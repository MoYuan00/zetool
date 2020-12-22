package com.zetool.beancopy.handler;

/**
 * 类型克隆器
 */
public interface TypeCloner {

    /**
     * 克隆t值，并返回
     * @param t
     * @return 克隆出来的t值
     */
    public <T> T cloneValue(T t);

}

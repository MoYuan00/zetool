package com.zetool.beancopy.bean.cloner;

public interface TypeCloner {
    /**
     * 克隆一个obj出来
     * @param obj 要克隆的对象
     * @param deepMax 拷贝深度
     * @param <T>
     * @return 返回克隆的新对象
     */
    <T> T cloneValue(T obj, int deepMax);
}

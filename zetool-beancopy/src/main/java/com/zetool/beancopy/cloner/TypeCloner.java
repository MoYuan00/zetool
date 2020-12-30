package com.zetool.beancopy.cloner;

/**
 * 类型克隆器
 */
public abstract class TypeCloner {

    /**
     * 克隆深度默认值，最多为5层
     */
    public static final int DEEP_MAX_DEFAULT = 5;

    /**
     * 克隆深度
     */
    protected int deepMax = DEEP_MAX_DEFAULT;

    public TypeCloner(){}

    public TypeCloner(int deepMax){
        this.deepMax = deepMax;
    }

    /**
     * 克隆t值，并返回
     * @param t
     * @return 克隆出来的t值
     */
    abstract public  <T> T cloneValue(T t);

}

package com.zetool.beancopy.bean.property;

/**
 * 属性映射关系、映射方式
 */
public interface PropertyMirror {

    /**
     * 属性名映射
     * @param name
     * @return
     */
    default String mirrorName(String name)  {
        return name;
    }

}

package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.util.TypeUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface TypeCloner {
    /**
     * 克隆一个obj出来
     * @param obj 要克隆的对象
     * @param deepMax 拷贝深度
     * @param <T>
     * @return 返回克隆的新对象
     */
    @Nonnull
    default  <T> T cloneValue(@Nonnull T obj, int deepMax){
        if(deepMax <= 0) return obj;
        T newObj = (T) TypeUtils.newInstanceNoParameter(obj.getClass());
        return cloneValue(obj, newObj, deepMax);
    }

    /**
     *  将sourceObj中的属性n值 拷贝 到targetObj 的属性值中
     * @param sourceObj
     * @param targetObj
     * @param deepMax 拷贝深度
     * @param <T>
     * @return
     */
    @Nonnull
    <T> T cloneValue(@Nonnull T sourceObj, @Nonnull T targetObj, int deepMax);
}

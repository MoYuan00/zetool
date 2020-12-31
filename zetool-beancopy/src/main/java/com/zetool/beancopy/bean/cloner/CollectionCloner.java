package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.util.TypeUtils;

import java.util.Collection;

/**
 * 集合拷贝器
 */
public class CollectionCloner implements TypeCloner {
    /**
     *
     * @param obj 要克隆的对象
     * @param deepMax 拷贝深度
     * @param <T>
     * @return
     */
    @Override
    public <T> T cloneValue(T obj, int deepMax) {
        Iterable<?> iterable = (Iterable<?>)obj;
        Collection collection = (Collection) TypeUtils.newInstanceNoParameter(obj.getClass());// 实例化一个集合
        iterable.forEach(item -> {
            if(item == null) collection.add(null);
            else collection.add(TypeClonerAdapter.cloneValue(item.getClass(), item, deepMax - 1));
        });// 循环拷贝集合
        return (T) collection;
    }
}

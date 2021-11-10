package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.util.TypeUtils;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * 集合拷贝器
 */
public class CollectionCloner implements TypeCloner {

    @Nonnull
    @Override
    public <T> T cloneValue(@Nonnull T sourceObj, @Nonnull T targetObj, int deepMax) {
        Iterable<?> iterable = (Iterable<?>)sourceObj;
        Collection collection = (Collection)targetObj;
        iterable.forEach(item -> {
            if(item == null) collection.add(null);
            else collection.add(TypeClonerAdapter.cloneValue(item, deepMax - 1));
        });// 循环拷贝集合
        return (T) collection;
    }
}

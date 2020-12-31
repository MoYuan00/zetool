package com.zetool.beancopy.field.cloner;

import com.zetool.beancopy.util.Log;
import com.zetool.beancopy.util.TypeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 可迭代的类型的克隆器
 * 实现：继承Collection的类型的拷贝
 */
public class CollectionTypeCloner extends TypeCloner{
    @Override
    public <T> T cloneValue(T t) {
        if(t == null) return null;
        Iterable<?> iterable = (Iterable<?>)t;
        Collection collection = (Collection) TypeUtils.newInstanceNoParameter(t.getClass());// 实例化一个集合
        iterable.forEach(item -> collection.add(TypeClonerFactory.getTypeCloner(item.getClass()).cloneValue(item)));// 循环拷贝集合
        return (T) collection;
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(2);
        Log.debug(CollectionTypeCloner.class, list.getClass());
        Log.debug(CollectionTypeCloner.class, ArrayList.class.isInstance(list));

        Log.debug(CollectionTypeCloner.class, list.getClass().isInstance(new ArrayList()));

        List listClone = new CollectionTypeCloner().cloneValue(list);
        Log.debug(CollectionTypeCloner.class, listClone.size());
    }
}

package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.util.TypeUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 类型克隆适配器
 * 根据不同的类型，调用不同的克隆器，然后返回克隆值
 */
public class TypeClonerAdapter {

    public final static int DEFAULT_DEEP_MAX = 4;

    /**
     * 调用克隆器，克隆obj值
     * @param clazz
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T cloneValue(Class<?> clazz, T obj) {
        return cloneValue(clazz, obj, DEFAULT_DEEP_MAX);
    }

    /**
     * 调用克隆器，克隆obj值
     * @param clazz
     * @param obj
     * @param deepMax
     * @param <T>
     * @return
     */
    public static <T> T cloneValue(Class<?> clazz, T obj, int deepMax) {
        if(deepMax <= 0) return obj;// 如果拷贝深度 为0以下就不用拷贝了，返回引用就可以
        if(obj == null) return null;
        if(clazz.isPrimitive() || obj instanceof String || TypeUtils.isWrapClass(clazz))           // 如果是基本类型，或者是包装类型
            return obj;
        if(Collection.class.isAssignableFrom(clazz)) {       // 如果实现了Collection接口
            return TypeClonerFactory.getCollectionCloner().cloneValue(obj, deepMax);
        }
        if(Map.class.isAssignableFrom(clazz)) {              // 如果实现了Map接口
            return TypeClonerFactory.getMapCloner().cloneValue(obj, deepMax);
        }
        if(clazz.isArray()) {                                // 如果是数组
            return TypeClonerFactory.getArrayCloner().cloneValue(obj, deepMax);
        }
        if(Cloneable.class.isAssignableFrom(clazz)){         // 如果实现了Cloneable接口, map和collection的clone方法都不是深度拷贝 TODO 我认为对于集合来说就应该是浅拷贝（这里要添加选项，默认浅拷贝才对
            return TypeClonerFactory.getCloneableCloner().cloneValue(obj, deepMax);
        }
        return TypeClonerFactory.getObjectCloner().cloneValue(obj, deepMax);
    }

    public static void main(String[] args) {
        System.out.println((int[].class).isArray());
        System.out.println((Object[].class).isArray());

    }

}

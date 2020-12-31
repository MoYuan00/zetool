package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.util.TypeUtils;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 类型克隆适配器
 * 根据不同的类型，调用不同的克隆器，然后返回克隆值
 */
public class TypeClonerAdapter {

    /**
     * 默认最大拷贝深度
     */
    public final static int DEFAULT_DEEP_MAX = 4;

    /**
     * 调用克隆器，克隆obj值
     * @param obj
     * @param <T>
     * @return
     */
    @Nonnull
    public static <T> T cloneValue(@Nonnull T obj) {
        return cloneValue(obj.getClass(), obj, DEFAULT_DEEP_MAX);
    }

    /**
     * 调用克隆器，克隆obj值
     * @param obj
     * @param deepMax
     * @param <T>
     * @return
     */
    @Nonnull
    public static <T> T cloneValue(@Nonnull T obj, int deepMax) {
        return cloneValue(obj.getClass(), obj, deepMax);
    }

    /**
     * 调用克隆器，克隆obj值
     * @param clazz
     * @param obj
     * @param <T>
     * @return
     */
    @Nonnull
    public static <T> T cloneValue(@Nonnull Class<?> clazz, @Nonnull T obj) {
        return cloneValue(clazz, obj, DEFAULT_DEEP_MAX);
    }

    /**
     * 缓存类型和克隆器之间的映射
     * 减少if对于各种类型的判断, (可以提高性能1000000, 由2000+ms提高到900+ms)
     */
    private static ConcurrentMap<Class<?>, TypeCloner> cachedTypeClonerAdapterMap = new ConcurrentHashMap<>();

    /**
     * 缓存 可以直接返回的不变类型，包括int,integer,long,String和 基本类型的包装类型
     * 是否是可以直接返回的不变类型（int/Integer,Long/long, String... 等
     */
    private static Set<Class<?>> finalClassCachedSet = new HashSet<>(14);

    /**
     * 调用克隆器
     * @param clazz
     * @param obj
     * @param deepMax
     * @param <T>
     * @return
     */
    @Nonnull
    public static <T> T cloneValue(@Nonnull Class<?> clazz, @Nonnull T obj, int deepMax) {
        if(deepMax <= 0) return obj;                        // 如果拷贝深度 为0以下就不用拷贝了，返回引用就可以
        TypeCloner typeCloner = cachedTypeClonerAdapterMap.get(clazz);
        if(typeCloner != null) return typeCloner.cloneValue(obj, deepMax);
        if(finalClassCachedSet.contains(clazz)) return obj; // 是否是可以直接返回的不变类型
        if(clazz.isPrimitive() || obj instanceof String || TypeUtils.isWrapClass(clazz)){// 基本类型int..., 包装类型Integer..., String
            finalClassCachedSet.add(clazz);
            return obj;
        }// 引用类型
        if(clazz.isArray()){// 数组 Object[], int[]
            typeCloner = TypeClonerFactory.getArrayCloner();
            cachedTypeClonerAdapterMap.putIfAbsent(clazz, typeCloner);
            return typeCloner.cloneValue(obj, deepMax);
        }// 非数组的对象Long, String, List...
        if(Collection.class.isAssignableFrom(clazz)) {       // 如果实现了Collection接口
            typeCloner = TypeClonerFactory.getCollectionCloner();
            cachedTypeClonerAdapterMap.putIfAbsent(clazz, typeCloner);
            return typeCloner.cloneValue(obj, deepMax);
        }
        if(Map.class.isAssignableFrom(clazz)) {              // 如果实现了Map接口
            typeCloner = TypeClonerFactory.getMapCloner();
            cachedTypeClonerAdapterMap.putIfAbsent(clazz, typeCloner);
            return typeCloner.cloneValue(obj, deepMax);
        }
        if(Cloneable.class.isAssignableFrom(clazz)){         // 如果实现了Cloneable接口, map和collection的clone方法都不是深度拷贝 TODO 我认为对于集合来说就应该是浅拷贝（这里要添加选项，默认浅拷贝才对
            typeCloner = TypeClonerFactory.getCloneableCloner();
            cachedTypeClonerAdapterMap.putIfAbsent(clazz, typeCloner);
            return typeCloner.cloneValue(obj, deepMax);
        }
        typeCloner = TypeClonerFactory.getObjectCloner();
        cachedTypeClonerAdapterMap.putIfAbsent(clazz, typeCloner);
        return typeCloner.cloneValue(obj, deepMax);
    }

    public static void main(String[] args) {
        System.out.println((int[].class).isArray());
        System.out.println((Object[].class).isArray());

    }

}

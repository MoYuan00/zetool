package com.zetool.beancopy.util;

import java.lang.reflect.InvocationTargetException;

/**
 * 类型工具
 */
public class TypeUtils {

    /**
     * 是否是基本类型的包装类型
     * @param clazz
     * @return
     */
    public static boolean isWrapClass(Class<?> clazz){
        try {
            return ((Class<?>)clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    /**
     * 创建新对象，通过无参数构造函数
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T newInstanceNoParameter(Class<T> type){
        try {
            return type.getDeclaredConstructor().newInstance(new Class[]{});
        } catch (NoSuchMethodException | InvocationTargetException |IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}

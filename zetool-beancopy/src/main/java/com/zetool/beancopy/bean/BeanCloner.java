package com.zetool.beancopy.bean;

import com.zetool.beancopy.bean.annotation.CloneBeanFor;
import com.zetool.beancopy.bean.cloner.TypeClonerAdapter;
import com.zetool.beancopy.bean.property.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class BeanCloner {

    /**
     * 深拷贝拷贝<br>
     * 如果拷贝为一个对象，那么这个对象必须具有默认构造函数<br>
     * 或者为基本类型，或者其包装类型，数组，String<br>
     * 拷贝最大深度参考 {@code TypeClonerAdapter} 默认值<br>
     *
     * @param source
     * @param <S>
     * @return
     */
    public static <S> S cloneProperties(@Nullable S source) {
        return TypeClonerAdapter.cloneValue(source);
    }

    /**
     * 浅拷贝<br>
     * 如果拷贝为一个对象，那么这个对象必须具有默认构造函数<br>
     * 或者为基本类型，或者其包装类型，数组，String<br>
     * 拷贝最大深度参考 {@code TypeClonerAdapter} 默认值<br>
     *
     * @param source
     * @param <S>
     * @return
     */
    @Nonnull
    public static <S> S cloneSurfaceProperties(@Nonnull S source) {
        return TypeClonerAdapter.cloneValue(source, 1);
    }

    /**
     * 对象类型拷贝<br>
     * 拷贝对象一定要是一个Object类。而不是数组或者其他基本类型<br>
     * 浅拷贝，将s对象中的值拷贝到target中<br>
     *
     * @param source
     * @param target
     * @param <T>
     * @param <S>
     * @return
     */
    @Nonnull
    public static <T, S> T cloneSurfaceProperties(@Nonnull S source, @Nonnull T target) {
        return cloneProperties(source, target, 1);
    }

    /**
     * 克隆属性
     *
     * @param source
     * @param target
     * @param deepMax 克隆深度
     * @param <T>
     * @param <S>
     * @return
     */
    @Nonnull
    public static <T, S> T cloneProperties(@Nonnull S source, @Nonnull T target, int deepMax) {
        if (deepMax <= 0) return target;
        if (source.getClass().equals(target.getClass())) {// 同类拷贝
            return cloneSameProperties((T) source, target, deepMax);
        }
        // 异类型拷贝
        CloneBeanFor cloneBeanFor = AnnotationBeanProperties.getMatchSourceAnnotationFromTarget(source.getClass(), target.getClass());
        // 1. not have annotation, clone all common properties that S have getMethod and T have SetMethod.
        // source 和 target 没有注解，或者有注解但没有target->source的注解
        if (cloneBeanFor == null) {
            return cloneDifferentProperties(source, target, deepMax);
        }
        // 2. have annotation, clone all properties in that annotation described.
        // 有 target->source的注解
        return cloneDifferentPropertiesWithAnnotation(source, target, cloneBeanFor, deepMax);
    }

    /**
     * 通过注解拷贝两个不同的对象的属性
     *
     * @param source
     * @param target
     * @param cloneBeanFor
     * @param deepMax
     * @param <T>
     * @param <S>
     * @return
     */
    @Nonnull
    private static <T, S> T cloneDifferentPropertiesWithAnnotation(@Nonnull S source, @Nonnull T target, @Nonnull CloneBeanFor cloneBeanFor, int deepMax) {
        AnnotationBeanProperties targetBeanHelper = CachedBeanWithAnnotation.forClass(target.getClass());
        AnnotationBeanProperties sourceBeanHelper = CachedBeanWithAnnotation.forClass(source.getClass());
        for (PropertyDescriptor targetPd : targetBeanHelper.getSetterPropertyDescriptorsInAnnotation(cloneBeanFor)) {
            PropertyDescriptor sourcePd = sourceBeanHelper.getGetterPropertyDescriptor(targetPd.getName());
            boolean result = false;
            if (sourcePd != null) {
               result = cloneProperty(sourcePd, targetPd, source, target, deepMax);
            }
            if (!result) throw new BeanAnnotationException("无法克隆属性：" + targetPd.getName());
        }
        return target;
    }

    /**
     * 拷贝不同类型，只拷贝S中存在read方法，且T中存在write方法的属性
     *
     * @param source
     * @param target
     * @param deepMax
     * @param <T>
     * @param <S>
     * @return
     */
    @Nonnull
    private static <T, S> T cloneDifferentProperties(@Nonnull S source, @Nonnull T target, int deepMax) {
        BeanProperties targetBeanHelper = CachedBeanHelper.forClass(target.getClass());
        BeanProperties sourceBeanHelper = CachedBeanHelper.forClass(source.getClass());
        for (PropertyDescriptor sourcePd : sourceBeanHelper.getGetterPropertyDescriptors()) {
            PropertyDescriptor targetPd = targetBeanHelper.getSetterPropertyDescriptor(sourcePd.getName());
            if (targetPd != null) {
                cloneProperty(sourcePd.getReadMethod(), targetPd.getWriteMethod(), source, target, deepMax);
            }
        }
        return target;
    }

    /**
     * 拷贝一个属性到另一个属性，通过source的getter方法，和target的setter方法
     *
     * @param sourcePd
     * @param targetPd
     * @param source
     * @param target
     * @param deepMax
     * @param <T>
     * @param <S>
     * @return 执行了克隆，返回true., 没有执行克隆，false
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T, S> boolean cloneProperty(@Nonnull PropertyDescriptor sourcePd, @Nonnull PropertyDescriptor targetPd,
                                               @Nonnull S source, @Nonnull T target, int deepMax) {
        Method sourceReadMethod = sourcePd.getReadMethod();
        if (sourceReadMethod != null) {// 如果get、set都存在
            Method targetWriteMethod = targetPd.getWriteMethod();
            if (targetWriteMethod != null) {
                return cloneProperty(sourceReadMethod, targetWriteMethod, source, target, deepMax);
            }
        }
        return false;// 没有执行克隆，false
    }

    /**
     * 调用sourceReadMethod并且克隆返回值，然后调用targetWriteMethod设置值
     * @param sourceReadMethod
     * @param targetWriteMethod
     * @param source
     * @param target
     * @param deepMax
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> boolean cloneProperty(@Nonnull Method sourceReadMethod, @Nonnull Method targetWriteMethod,
                                              @Nonnull S source, @Nonnull T target, int deepMax) {
        if (!Modifier.isPublic(sourceReadMethod.getDeclaringClass().getModifiers())) {
            sourceReadMethod.setAccessible(true);
        }
        Object value = null;// 调用get
        try {
            value = sourceReadMethod.invoke(source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (value != null) {
            if (!Modifier.isPublic(targetWriteMethod.getDeclaringClass().getModifiers())) {
                targetWriteMethod.setAccessible(true);
            }
            Object cloneValue = TypeClonerAdapter.cloneValue(value, deepMax - 1);
            try {
                targetWriteMethod.invoke(target, cloneValue);// 调用set
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return true;// 执行了克隆，返回true. 算克隆成功
    }

    /**
     * 克隆相同的类型
     *
     * @param source
     * @param target
     * @param deepMax
     * @param <T>
     * @return
     */
    private static <T> T cloneSameProperties(@Nonnull T source, @Nonnull T target, int deepMax) {
        for (PropertyDescriptor pd : CachedBeanHelper.forClass(source.getClass()).getGetterAndSetterPropertyDescriptors()) {
            cloneProperty(pd, pd, source, target, deepMax);
        }
        return target;
    }

}

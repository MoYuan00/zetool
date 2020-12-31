package com.zetool.beancopy.bean.cloner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CloneableCloner implements TypeCloner{

    @Nonnull
    @Override
    public <T> T cloneValue(@Nonnull T obj, int deepMax) {
        Method cloneMethod;
        try {
            //先获取自己定义的clone方法
            cloneMethod = obj.getClass().getDeclaredMethod("clone");
        } catch (NoSuchMethodException e) {
            //如果自身未定义clone方法，则从父类中找，但父类的clone一定要是public
            try {
                cloneMethod = obj.getClass().getMethod("clone");
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
                throw new IllegalStateException();
            }
        }
        cloneMethod.setAccessible(true);
        T val;
        try {
            val = (T)cloneMethod.invoke(obj, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
        return val;
    }

    @Nonnull
    @Override
    public <T> T cloneValue(@Nonnull T sourceObj, @Nonnull T targetObj, int deepMax) {
        return cloneValue(sourceObj, deepMax);
    }
}

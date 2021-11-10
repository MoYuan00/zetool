package com.zetool.beancopy.field.cloner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 如果是实现了Cloneable接口的类，就调用它的clone方法
 */
public class CloneableTypeCloner extends TypeCloner{

    @Override
    public <T> T cloneValue(T t) {
        if(t == null) return null;
        Method cloneMethod = null;
        try {
            //先获取自己定义的clone方法
            cloneMethod = t.getClass().getDeclaredMethod("clone", new Class[] {});
        } catch (NoSuchMethodException e) {
            //如果自身未定义clone方法，则从父类中找，但父类的clone一定要是public
            try {
                cloneMethod = t.getClass().getMethod("clone", new Class[] {});
            } catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
                throw new IllegalStateException();
            }
        }
        cloneMethod.setAccessible(true);
        T val = null;
        try {
            val = (T)cloneMethod.invoke( t, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
        return val;
    }
}

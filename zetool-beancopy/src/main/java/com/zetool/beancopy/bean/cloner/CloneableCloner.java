package com.zetool.beancopy.bean.cloner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CloneableCloner implements TypeCloner{
    @Override
    public <T> T cloneValue(T obj, int deepMax) {
        if (obj == null) return null;
        Method cloneMethod = null;
        try {
            //先获取自己定义的clone方法
            cloneMethod = obj.getClass().getDeclaredMethod("clone", new Class[] {});
        } catch (NoSuchMethodException e) {
            //如果自身未定义clone方法，则从父类中找，但父类的clone一定要是public
            try {
                cloneMethod = obj.getClass().getMethod("clone", new Class[] {});
            } catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
                throw new IllegalStateException();
            }
        }
        cloneMethod.setAccessible(true);
        T val = null;
        try {
            val = (T)cloneMethod.invoke( obj, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
        return val;
    }
}

package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.util.TypeUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 对象克隆器
 */
public class ObjectCloner implements TypeCloner {

    @Override
    public <T> T cloneValue(T obj, int deepMax) {
        T newObj = (T) TypeUtils.newInstanceNoParameter(obj.getClass());// 用无参数构造，实例化一个对象
        new BeanHelper(obj.getClass()).getPropertyDescriptors().forEach(dp -> {// 拷贝所有参数
            try {
                Object value = dp.getReadMethod().invoke(obj);
                if(value == null) {
                    dp.getWriteMethod().invoke(obj, null);
                    return;
                }
                Object cloneValue =  TypeClonerAdapter.cloneValue(value.getClass(), value, deepMax - 1);
                dp.getWriteMethod().invoke(obj, cloneValue);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return newObj;
    }

}

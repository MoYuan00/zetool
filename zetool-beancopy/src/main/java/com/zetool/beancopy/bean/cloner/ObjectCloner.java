package com.zetool.beancopy.bean.cloner;


import com.zetool.beancopy.bean.BeanCloner;
import com.zetool.beancopy.bean.property.CachedBeanHelper;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * 对象克隆器
 */
public class ObjectCloner implements TypeCloner {

    @Nonnull
    @Override
    public <T> T cloneValue(@Nonnull T sourceObj, @Nonnull T targetObj, int deepMax) {
        for(PropertyDescriptor pd : CachedBeanHelper.forClass(sourceObj.getClass()).getGetterAndSetterPropertyDescriptors()){// 拷贝所有参数
            BeanCloner.cloneProperty(pd, pd, sourceObj, targetObj, deepMax);
        }
        return targetObj;
    }

}

package com.zetool.beancopy.bean.property;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class BeanProperties {

    /**
     *
     * @return 需要拷贝的属性集合
     */
    public static PropertyDescriptor[] getPrepareDescriptors(Class<?> clazz){
        PropertyDescriptor[] pds = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            pds = beanInfo.getPropertyDescriptors();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return pds;
    }


}

package com.zetool.beancopy.bean.property;

import com.zetool.beancopy.bean.annotation.CloneBeanFor;
import com.zetool.beancopy.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CachedBeanWithAnnotation implements AnnotationBeanProperties{

    private BeanProperties beanHelper;

    private static ConcurrentMap<Class<?>, CachedBeanWithAnnotation> cachedBeanMap = new ConcurrentHashMap<>();

    private ConcurrentMap<CloneBeanFor, PropertyDescriptor[]> cloneBeanForDescriptorMap = new ConcurrentHashMap<>();

    /**
     * 构造一个{@code CachedBeanWithAnnotation}
     * @param clazz
     * @return
     */
    public static CachedBeanWithAnnotation forClass(Class<?> clazz){
        CachedBeanWithAnnotation obj = cachedBeanMap.get(clazz);
        if(obj == null){
            obj = new CachedBeanWithAnnotation(clazz);
            cachedBeanMap.putIfAbsent(clazz, obj);
        }
        return obj;
    }

    private CachedBeanWithAnnotation(Class<?> clazz){
        beanHelper = CachedBeanHelper.forClass(clazz);

    }

    @Override
    public PropertyDescriptor[] getSetterPropertyDescriptorsInAnnotation(CloneBeanFor cloneBeanFor) {
        PropertyDescriptor[] propertyDescriptors = getSetterPropertyDescriptors();
        return getPropertyDescriptorsInAnnotation(cloneBeanFor, propertyDescriptors);
    }

    @Override
    public PropertyDescriptor[] getGetterPropertyDescriptorsInAnnotation(CloneBeanFor cloneBeanFor) {
        PropertyDescriptor[] propertyDescriptors = getGetterPropertyDescriptors();
        return getPropertyDescriptorsInAnnotation(cloneBeanFor, propertyDescriptors);
    }

    private PropertyDescriptor[] getPropertyDescriptorsInAnnotation(CloneBeanFor annotation, PropertyDescriptor[] propertyDescriptors) {
        if(annotation == null) return propertyDescriptors;

        PropertyDescriptor[] result = cloneBeanForDescriptorMap.get(annotation);// 获取缓存
        if(result == null) cloneBeanForDescriptorMap.get(annotation);
        if(result != null) return result;


        List<PropertyDescriptor> resultList = new ArrayList<>();
        if(annotation.requirePropertyNames().length == 0) {
            Set<String> ignoreNames = CollectionUtils.toSet(annotation.ignorePropertyNames());
            for (PropertyDescriptor pd : propertyDescriptors) {
                if(!ignoreNames.contains(pd.getName())) resultList.add(pd);
            }
            result = resultList.toArray(new PropertyDescriptor[resultList.size()]);
            cloneBeanForDescriptorMap.putIfAbsent(annotation, result);
            return result;
        }


        Set<String> requireNames = CollectionUtils.toSet(annotation.requirePropertyNames());
        for (PropertyDescriptor pd : propertyDescriptors) {
            if(requireNames.contains(pd.getName())) resultList.add(pd);
        }
        result = resultList.toArray(new PropertyDescriptor[resultList.size()]);
        cloneBeanForDescriptorMap.putIfAbsent(annotation, result);
        return result;
    }

    @Override
    public PropertyDescriptor[] getGetterPropertyDescriptors() {
        return beanHelper.getGetterPropertyDescriptors();
    }

    @Override
    public PropertyDescriptor[] getGetterAndSetterPropertyDescriptors() {
        return beanHelper.getGetterAndSetterPropertyDescriptors();
    }

    @Override
    public PropertyDescriptor[] getSetterPropertyDescriptors() {
        return beanHelper.getSetterPropertyDescriptors();
    }

    @Override
    public PropertyDescriptor getGetterPropertyDescriptor(String propertyName) {
        return beanHelper.getGetterPropertyDescriptor(propertyName);
    }

    @Override
    public PropertyDescriptor getSetterPropertyDescriptor(String propertyName) {
        return beanHelper.getSetterPropertyDescriptor(propertyName);
    }


}

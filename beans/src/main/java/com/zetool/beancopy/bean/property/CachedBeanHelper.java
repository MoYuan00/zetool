package com.zetool.beancopy.bean.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * java bean 帮助类，获取一个类的属性
 */
public class CachedBeanHelper implements BeanProperties {

    private BeanInfo beanInfo;

    private PropertyDescriptor[] getterPropertyDescriptors;

    private PropertyDescriptor[] setterPropertyDescriptors;

    private PropertyDescriptor[] getterAndSetterPropertyDescriptors;

    private Map<String, PropertyDescriptor> setterPropertyDescriptorsMap;

    private Map<String, PropertyDescriptor> getterPropertyDescriptorsMap;


    /**
     * class缓存
     */
    private static final ConcurrentMap<Class<?>, CachedBeanHelper> classCachedSet = new ConcurrentHashMap<>();

    /**
     * 添加缓存，减少重复的反射操作
     * @param clazz get a {@code CachedBeanHelper} for given clazz
     * @return a {@code CachedBeanHelper} for given clazz
     */
    @Nonnull
    public static CachedBeanHelper forClass(Class<?> clazz){
        CachedBeanHelper result = null;
        result = classCachedSet.get(clazz);
        if(result == null) result = classCachedSet.get(clazz);
        if(result == null){
            result = new CachedBeanHelper(clazz);
            classCachedSet.putIfAbsent(clazz, result);
        }
        return result;
    }

    private CachedBeanHelper(Class<?> clazz) {
        try {
            this.beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            throw new IllegalStateException();
        }
        PropertyDescriptor[] propertyDescriptors = this.beanInfo.getPropertyDescriptors();
        // 对PropertyDescriptor进行预处理
        List<PropertyDescriptor> getterList = new ArrayList<>(propertyDescriptors.length);
        List<PropertyDescriptor> setterList = new ArrayList<>(propertyDescriptors.length);
        List<PropertyDescriptor> getterAndSetterList = new ArrayList<>(propertyDescriptors.length);
        setterPropertyDescriptorsMap = new HashMap<>(propertyDescriptors.length);
        getterPropertyDescriptorsMap = new HashMap<>(propertyDescriptors.length);
        Method readMethod = null;
        Method writeMethod = null;
        for (PropertyDescriptor pd : propertyDescriptors) {
            readMethod = pd.getReadMethod();
            writeMethod = pd.getWriteMethod();
            if(writeMethod != null){
                setterList.add(pd);
                setterPropertyDescriptorsMap.putIfAbsent(pd.getName(), pd);
            }
            if(readMethod != null){
                getterList.add(pd);
                getterPropertyDescriptorsMap.putIfAbsent(pd.getName(), pd);
                if(writeMethod != null){
                    getterAndSetterList.add(pd);
                }
            }
        }
        this.getterPropertyDescriptors = getterList.toArray(new PropertyDescriptor[getterList.size()]);
        this.setterPropertyDescriptors = setterList.toArray(new PropertyDescriptor[setterList.size()]);
        getterAndSetterPropertyDescriptors = getterAndSetterList.toArray(new PropertyDescriptor[getterAndSetterList.size()]);
    }

    @Nonnull
    @Override
    public PropertyDescriptor[] getGetterPropertyDescriptors() {
        return getterPropertyDescriptors;
    }

    @Nonnull
    @Override
    public PropertyDescriptor[] getGetterAndSetterPropertyDescriptors() {
        return getterAndSetterPropertyDescriptors;
    }

    @Nonnull
    @Override
    public PropertyDescriptor[] getSetterPropertyDescriptors() {
        return setterPropertyDescriptors;
    }

    @Nullable
    @Override
    public PropertyDescriptor getGetterPropertyDescriptor(String propertyName) {
        return getterPropertyDescriptorsMap.get(propertyName);
    }

    @Nullable
    @Override
    public PropertyDescriptor getSetterPropertyDescriptor(String propertyName) {
        return setterPropertyDescriptorsMap.get(propertyName);
    }
}

package com.zetool.beancopy.bean.property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public interface BeanProperties {

    /**
     *
     * @return
     */
    public @Nonnull PropertyDescriptor[] getGetterPropertyDescriptors();

    /**
     *
     * @return
     */
    public @Nonnull PropertyDescriptor[] getGetterAndSetterPropertyDescriptors();

    /**
     *
     * @return
     */
    public @Nonnull
    PropertyDescriptor[] getSetterPropertyDescriptors();

    /**
     *
     * @return
     */

    public @Nullable PropertyDescriptor getGetterPropertyDescriptor(String propertyName);

    /**
     *
     * @return
     */
    public @Nullable PropertyDescriptor getSetterPropertyDescriptor(String propertyName);

}

package com.zetool.beancopy.bean.property;

import com.zetool.beancopy.bean.annotation.CloneBeanFor;
import com.zetool.beancopy.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;

public interface AnnotationBeanProperties extends BeanProperties {

    /**
     * 获取注解中注解了的Setter属性
     *
     * @param cloneBeanFor
     * @return
     */
    public @Nonnull
    PropertyDescriptor[] getSetterPropertyDescriptorsInAnnotation(CloneBeanFor cloneBeanFor);

    /**
     * 获取注解中注解了的Getter属性
     *
     * @param cloneBeanFor
     * @return
     */
    public @Nonnull
    PropertyDescriptor[] getGetterPropertyDescriptorsInAnnotation(@Nonnull CloneBeanFor cloneBeanFor);

    /**
     * 获取到target注解中，sourceClass或者sourceName注解了source的注解。
     * 如果没有返回null
     * 如果有多个只返回任意一个
     *
     * @param source
     * @param target
     * @param <T>
     * @param <S>
     * @return
     */
    @Nullable
    public static <T, S> CloneBeanFor getMatchSourceAnnotationFromTarget(@Nonnull Class<S> source, @Nonnull Class<T> target) {
        CloneBeanFor[] annotations = target.getDeclaredAnnotationsByType(CloneBeanFor.class);
        return CollectionUtils.findFirst(annotations, (cloneBeanFor) -> {
            if (source.equals(cloneBeanFor.sourceClassFor())) return true;
            if (source.getName().equals(cloneBeanFor.sourceNameFor())) return true;
            return false;
        });
    }

}

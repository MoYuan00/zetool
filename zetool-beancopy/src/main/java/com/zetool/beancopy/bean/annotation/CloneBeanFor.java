package com.zetool.beancopy.bean.annotation;

import java.lang.annotation.*;


@Repeatable(CloneBeansFor.class)
@Target(ElementType.TYPE)// 可以注解在类上
@Retention(RetentionPolicy.RUNTIME)// 运行时可以获取到
public @interface CloneBeanFor {

    /**
     * 拷贝来源类
     * @return
     */
    Class<?> sourceClassFor();

    /**
     * 拷贝来源类名
     * @return
     */
    String sourceNameFor() default "";

    /**
     * 必须拷贝的属性名，如果拷贝失败则会抛出异常。
     * <br>
     * 利用这个注解值可以防止bean的属性名修改导致的意料之外的拷贝。
     * @return
     */
    String[] requirePropertyNames() default {};

    /**
     * 要忽略属性名, 将不会拷贝被注解的属性名。
     * 注意，如果在{@code ignorePropertyNames} 中 出现的 属性 在{@code requirePropertyNames}也出现了。
     * 那么将无视你在{@code ignorePropertyNames}中注解的这个属性。
     * @return
     */
    String[] ignorePropertyNames() default {};
}

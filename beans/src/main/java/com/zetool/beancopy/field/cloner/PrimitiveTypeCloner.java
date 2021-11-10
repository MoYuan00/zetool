package com.zetool.beancopy.field.cloner;

/**
 * 克隆基本类型
 */
public final class PrimitiveTypeCloner extends TypeCloner {

    @Override
    public  final <T> T cloneValue(T t){
        return t;
    }

}

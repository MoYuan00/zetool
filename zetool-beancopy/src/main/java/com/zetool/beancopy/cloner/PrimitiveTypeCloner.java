package com.zetool.beancopy.cloner;

import com.zetool.beancopy.util.Log;

/**
 * 克隆基本类型
 */
public final class PrimitiveTypeCloner extends TypeCloner {

    @Override
    public  final <T> T cloneValue(T t){
        if(t == null) return null;
        Log.debug(PrimitiveTypeCloner.class, "primitive class:" + t.getClass().getTypeName() + " value:" + t);
        return t;
    }

}

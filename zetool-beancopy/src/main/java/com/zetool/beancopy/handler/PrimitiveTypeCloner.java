package com.zetool.beancopy.handler;

import com.zetool.beancopy.util.Log;

/**
 * 克隆基本类型
 */
public final class PrimitiveTypeCloner implements TypeCloner {

    @Override
    public  final <T> T cloneValue(T t){
        Log.debug(PrimitiveTypeCloner.class, "primitive class:" + t.getClass() + " value:" + t);
        return t;
    }

}

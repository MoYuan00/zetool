package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.util.TypeUtils;

import java.util.Map;

public class MapCloner implements TypeCloner {
    @Override
    public <T> T cloneValue(T obj, int deepMax) {
        if(obj == null) return null;
        Map map = (Map)obj;
        Map mapClone = (Map) TypeUtils.newInstanceNoParameter(obj.getClass());
        map.entrySet().forEach(entry -> {
            Map.Entry item = (Map.Entry)entry;// TODO 这里声明了太多变量，可以声明在外部提速
            Object key = item.getKey();
            Object value = item.getValue();
            Object keyClone = TypeClonerAdapter.cloneValue(key.getClass(), key, deepMax - 1);// 克隆key
            Object valueClone = TypeClonerAdapter.cloneValue(value.getClass(), value, deepMax - 1);// 克隆value
            mapClone.put(keyClone, valueClone);
        });
        return (T) mapClone;
    }
}

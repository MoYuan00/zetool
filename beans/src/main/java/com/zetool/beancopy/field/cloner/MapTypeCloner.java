package com.zetool.beancopy.field.cloner;

import com.zetool.beancopy.util.Log;
import com.zetool.beancopy.util.TypeUtils;

import java.util.Map;
import java.util.TreeMap;

public class MapTypeCloner extends TypeCloner {
    @Override
    public <T> T cloneValue(T t) {
        if(t == null) return null;
        Map map = (Map)t;
        Map mapClone = (Map) TypeUtils.newInstanceNoParameter(t.getClass());
        map.entrySet().forEach(entry -> {
            Map.Entry item = (Map.Entry)entry;// TODO 这里声明了太多变量，可以声明在外部提速
            Object key = item.getKey();
            Object value = item.getValue();
            Object keyClone = TypeClonerFactory.getTypeCloner(key.getClass()).cloneValue(key);// 克隆key
            Object valueClone = TypeClonerFactory.getTypeCloner(value.getClass()).cloneValue(value);// 克隆value
            mapClone.put(keyClone, valueClone);
        });
        return (T) mapClone;
    }

    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>();
        map.put("123", "456");
        Map mapClone = new MapTypeCloner().cloneValue(map);
        Log.debug(MapTypeCloner.class, mapClone.size());

    }
}

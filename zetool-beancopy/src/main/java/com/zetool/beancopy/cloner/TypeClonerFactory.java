package com.zetool.beancopy.cloner;

import com.zetool.beancopy.util.Log;

import java.util.*;


/**
 * 类型克隆器工厂
 * 管理所有的克隆器的创建。保证克隆器是单例存在。
 * 隐藏克隆器的分配，让调用者无需关心到底需要哪个克隆器
 */
public class TypeClonerFactory {

    private static final PrimitiveTypeCloner primitiveTypeCloner = new PrimitiveTypeCloner();

    private static final ArrayTypeCloner arrayTypeCloner = new ArrayTypeCloner();

    private static final CloneableTypeCloner cloneableTypeCloner = new CloneableTypeCloner();

    private static final StringTypeCloner stringTypeCloner = new StringTypeCloner();

    private static final SerializableTypeCloner serializableTypeCloner = new SerializableTypeCloner();

    private static final CollectionTypeCloner collectionTypeCloner = new CollectionTypeCloner();

    private static final MapTypeCloner mapTypeCloner = new MapTypeCloner();

    /**
     * 通过给定类型获取到合适的克隆器
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> TypeCloner getTypeCloner(Class<T> clazz){
        return getTypeCloner(clazz, TypeCloner.DEEP_MAX_DEFAULT);
    }

    /**
     * 通过给定类型获取到合适的克隆器
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> TypeCloner getTypeCloner(Class<T> clazz, int deepMax){
        Log.info(TypeClonerFactory.class, "class:" + clazz.getTypeName());
        // TODO 这里的if结构有点庞大，可以考虑使用hashmap改进。既可以提速，也可以解耦合更灵活。
        if(clazz.isPrimitive() || isWrapClass(clazz)) {             // 如果是基本类型，或者是包装类型
            Log.debug(TypeClonerFactory.class, "primitive type");
            return primitiveTypeCloner;
        }else if(String.class.equals(clazz)){                       // String类型，TODO 这里给String类型也分配了拷贝器，感觉有点多余，是不是要再抽象一层适配器。然后由适配器调用工厂类。以达到减少克隆器的数量。
            Log.debug(TypeClonerFactory.class, "string type");
            return stringTypeCloner;
        }else if(clazz.isArray()){                                  // 如果是数组
            Log.debug(TypeClonerFactory.class, "array type");
            return arrayTypeCloner;
        }else if(Collection.class.isAssignableFrom(clazz)) {        // 如果实现了Collection接口
            Log.debug(TypeClonerFactory.class, "Collection type");
            return collectionTypeCloner;
        }else if(Map.class.isAssignableFrom(clazz)) {               // 如果实现了Map接口
            Log.debug(TypeClonerFactory.class, "Map type");
            return mapTypeCloner;
        }else if(Cloneable.class.isAssignableFrom(clazz)){          // 如果实现了Cloneable接口, map和collection的clone方法都不是深度拷贝 TODO 我认为对于集合来说就应该是浅拷贝（这里要添加选项，默认浅拷贝才对
            Log.debug(TypeClonerFactory.class, "Cloneable type");
            return cloneableTypeCloner;
        }else{
            Log.debug(TypeClonerFactory.class, "Object type");  // 对象类型
            return new ObjectTypeCloner(deepMax);
        }
    }

    /**
     * 是否是基本类型的包装类型
     * @param clazz
     * @return
     */
    public static boolean isWrapClass(Class<?> clazz){
        try {
            return ((Class<?>)clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;// TODO 这里好像有点利用异常来控制流程的意思了。不知道有没有更好的写法
        }
    }

    public static void main(String[] args) {
        TypeClonerFactory.getTypeCloner(String.class);
        TypeClonerFactory.getTypeCloner(Integer.class);
        TypeClonerFactory.getTypeCloner(HashMap.class);
        TypeClonerFactory.getTypeCloner(LinkedList.class);
        TypeClonerFactory.getTypeCloner(LinkedList[].class);
    }
}

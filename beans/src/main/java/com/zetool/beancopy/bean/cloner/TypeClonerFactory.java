package com.zetool.beancopy.bean.cloner;


/**
 * 类型克隆器工厂
 * 管理所有的克隆器的创建。保证克隆器是单例存在。
 * 隐藏克隆器的分配，让调用者无需关心到底需要哪个克隆器
 */
public class TypeClonerFactory {

    private static TypeCloner arrayCloner = new ArrayCloner();

    private static TypeCloner objectCloner = new ObjectCloner();

    private static TypeCloner collectionCloner = new CollectionCloner();

    private static TypeCloner mapCloner = new MapCloner();

    private static TypeCloner cloneableCloner = new CloneableCloner();


    public static TypeCloner getArrayCloner(){
        return arrayCloner;
    }

    public static TypeCloner getObjectCloner(){
        return objectCloner;
    }

    public static TypeCloner getCollectionCloner(){
        return collectionCloner;
    }

    public static TypeCloner getMapCloner(){
        return mapCloner;
    }

    public static TypeCloner getCloneableCloner(){
        return cloneableCloner;
    }

    public static void main(String[] args) {
//        TypeClonerFactory.getTypeCloner(String.class);
//        TypeClonerFactory.getTypeCloner(Integer.class);
//        TypeClonerFactory.getTypeCloner(HashMap.class);
//        TypeClonerFactory.getTypeCloner(LinkedList.class);
//        TypeClonerFactory.getTypeCloner(LinkedList[].class);
    }
}

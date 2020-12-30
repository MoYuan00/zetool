package com.zetool.beancopy.cloner;

import com.zetool.beancopy.helper.ClassHelper;
import com.zetool.beancopy.helper.FieldHelper;
import com.zetool.beancopy.util.Log;

import java.lang.reflect.Modifier;

/**
 * 对象拷贝器
 * 这个拷贝器的实现必定最为复杂 和 需要保证性能，安全等。
 */
public class ObjectTypeCloner extends TypeCloner {

    public ObjectTypeCloner(){}

    public ObjectTypeCloner(int deepMax){super(deepMax);}

    @Override
    public <T> T cloneValue(T t) {
        if(t == null) return null;
        Log.debug(ObjectTypeCloner.class, "deep max value is " + deepMax);
        if(deepMax <= 0) return t;
        T resultObject = (T) ClassHelper.newInstanceNoParameter (t.getClass());
        cloneFieldsValue(t, resultObject);
        return resultObject;
    }

    /**
     * 拷贝对象的所有字段值
     * @param sourceObj
     * @param targetObj
     * @param <T>
     */
    private <T> void cloneFieldsValue(T sourceObj, T targetObj){
        ClassHelper.getAllField(sourceObj.getClass())
            .forEach(field -> {
                if(Modifier.isFinal(field.getModifiers())) return;// 过滤掉modifier对象
                try {
                    field.setAccessible(true);
                    Object value = field.get(sourceObj);
                    if(value != null){
                        Object v = TypeClonerFactory.getTypeCloner(field.getType(), deepMax - 1).cloneValue(value);
                        field.set(targetObj, v);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
    }

    public static class Test{
         public static class DataFather{
            public DataFather(){}
            public DataFather(String name) {
                this.name = name;
            }
            public String name = "data default";// 第3层
        }
        public static class Data{
            public DataFather dataFather;
            public String name = "data default";// 第2层
            public Data(){}
            public Data(String name) {
                this.name = name;
            }
        }
        public static class A{
            public int _int = 10;// 第1层
            public Integer integer = 100;
            public String string = "A default";
            public Data data = new Data("data: A 1");
        }
        public static void main(String[] args) {
            A a = new A();
            a.string = "A loki";
            a.data.name = "data: A loki";
            a.data.dataFather = new DataFather("data father: loki");
            A copy = TypeClonerFactory.getTypeCloner(A.class, 2).cloneValue(a);

            a.integer = 111;
            a.data.name = "data: A loki change";
            a.data.dataFather.name = "data father: loki change";
            System.out.println(FieldHelper.toString(copy));
            // 0层直接引用，1层考字段引用，2层考字段对象的字段引用，3层。。。。。。依次类推。实现精准层数控制
        }
    }
}

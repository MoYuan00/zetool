package com.zetool.beancopy.cloner;

import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.Log;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 数组类型克隆器
 */
public class ArrayTypeCloner extends TypeCloner {

    @Override
    public <T> T cloneValue(T t) {
        if(t == null) return null;
        Log.debug(ArrayTypeCloner.class, "array type:" + t.getClass().getTypeName() + ", component type:" + t.getClass().getComponentType().getTypeName());
        T newT = (T) Array.newInstance(t.getClass().getComponentType(), Array.getLength(t));
        cloneArray(t, newT);
        return newT;
    }

    /**
     * 递归克隆
     * @param sourceArr
     * @param targetArr
     */
    private void cloneArray(Object sourceArr, Object targetArr){
        Object item = null;
        Object itemClone = null;
        for(int i = 0, len = Array.getLength(sourceArr); i < len; i++){
            //注:如果是非数组的基本类型，则返回的是包装类型
            item = Array.get(sourceArr, i);
            if(item == null) {
                itemClone = null;
            }else if(item.getClass().isArray()){// 如果是多维数组
                itemClone = Array.newInstance(item.getClass().getComponentType(), Array.getLength(item));
                //如果元素是数组，则递归调用
                this.cloneArray(item, itemClone);
            }else{
                /*
                 *  注:这里不能使用 objTmp.getClass.isPrimitive()来判断是元素是
                 *  否是基本类型，因为objTmp是通过Array.get获得的，而Array.get返
                 *  回的是Object 类型，也就是说如果是基本类型会自动转换成对应的包
                 *  装类型后返回，所以 我们只能采用原始的类型来判断才行。
                 */
                itemClone = TypeClonerFactory.getTypeCloner(sourceArr.getClass().getComponentType()).cloneValue(item);
            }
            Array.set(targetArr, i, itemClone);// 设置值
        }
    }

    public static void main(String[] args) {
        TypeCloner cloner = new ArrayTypeCloner();
        System.out.println(cloner.cloneValue(new int[]{1, 2, 3}));
        /**
         * 多维数组
         */
        System.out.println(cloner.cloneValue(new Integer[][]{{1, 2, 3}, {2, 3}}));

        System.out.println(cloner.cloneValue(new List[][]{new List[]{CollectionUtils.toList(1, 2), CollectionUtils.toList(3, 4)}}));
    }
}

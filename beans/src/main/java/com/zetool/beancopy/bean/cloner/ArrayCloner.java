package com.zetool.beancopy.bean.cloner;


import javax.annotation.Nonnull;
import java.lang.reflect.Array;

/**
 * 数组克隆器
 */
public class ArrayCloner implements TypeCloner {
    @Nonnull
    @Override
    public <T> T cloneValue(@Nonnull T obj, int deepMax) {
        T newObj = (T) Array.newInstance(obj.getClass().getComponentType(), Array.getLength(obj));
        return cloneValue(obj, newObj, deepMax);
    }

    @Nonnull
    @Override
    public <T> T cloneValue(@Nonnull T sourceObj, @Nonnull T targetObj, int deepMax) {
        cloneArray(sourceObj, targetObj, deepMax);
        return targetObj;
    }

    /**
     * 递归克隆
     * @param sourceArr
     * @param targetArr
     */
    private void cloneArray(@Nonnull Object sourceArr, @Nonnull Object targetArr, int deepMax){
        Object item = null;
        Object itemClone = null;
        for(int i = 0, len = Array.getLength(sourceArr); i < len; i++){
            //注:如果是非数组的基本类型，则返回的是包装类型
            item = Array.get(sourceArr, i);
            if(item == null) {
                continue;
            }else if(item.getClass().isArray()){// 如果是多维数组
                itemClone = Array.newInstance(item.getClass().getComponentType(), Array.getLength(item));
                //如果元素是数组，则递归调用
                this.cloneArray(item, itemClone, deepMax);
            }else{
                /*
                 *  注:这里不能使用 objTmp.getClass.isPrimitive()来判断是元素是
                 *  否是基本类型，因为objTmp是通过Array.get获得的，而Array.get返
                 *  回的是Object 类型，也就是说如果是基本类型会自动转换成对应的包
                 *  装类型后返回，所以 我们只能采用原始的类型来判断才行。
                 */
                itemClone = TypeClonerAdapter.cloneValue(sourceArr.getClass().getComponentType(), item, deepMax - 1);
            }
            Array.set(targetArr, i, itemClone);// 设置值
        }
    }
}

package com.zetool.beancopy.executor;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 拷贝基本类型
 * 和
 * 1.包装类型 Integer,String,Long,Double,Float,Short,Byte等
 * 2. 任意基本类型的数组，一维`BaseType[]`和多维。
 * 以上BaseType[]可以直接调用其`val = type.clone()`方法实现值拷贝。这种方法比较快速
 * @author Rnti
 *
 */
public class BaseTypeCopyExecutor {
	
	/**
	 * 拷贝基本类型，包装类型，以及其多维数组
	 * @param obj
	 * @return
	 */
	public static <T>T executor(T obj) {
		System.out.print(obj.getClass().getTypeName() + ": ");
		if(obj.getClass().isPrimitive()) {// 基本类型 int, long......
			System.out.println("基本类型拷贝");
			return obj;
		}
//		if(obj instanceof Object[]) {// 包装类型 BaseType[]和多维包装类型数组
//			System.out.println("Object[] 类型");
//			List<Object> objArray = new ArrayList<>();
//			for(Object o : (Object[])obj) {
//				objArray.add((Object)executor(o));
//			}
//			return (T) objArray.toArray();
//		}
		if(obj.getClass().isArray()) {// 基本类型的数组，， 这里和Object[] 类型位置不能颠倒
//			System.out.println("Array 类型");
//			Object cloneArr = Array.newInstance(obj.getClass().getComponentType(), Array.getLength(obj)); 
//			cloneArr(obj)
//			return (T)cloneArr;
		}
		if(obj instanceof Object) {// 必须放在最后 Integer, String, Double。。 这里直接返回也没事Integer包装类型只会拷贝值
			System.out.println("Object 类型");
			return obj;
		}
		return obj;
	}
	/** 
     * 多维数组深层克隆，如果数组类型是实现了Cloneable接口的某个类， 
     * 则会调用每个元素的clone方法实现深度克隆 
     *  
     * 虽然数组有clone方法，但我们不能使用反射来克隆数组，因为不能使用 
     * 反射来获取数组的clone方法，这个方法只能通过数组对象本身来调用， 
     * 所以这里使用了动态数组创建方法来实现。 
     *  
     * @param objArr 
     * @param cloneArr 
     * @throws Exception 
     */  
    static private void cloneArr(Object objArr, Object cloneArr) throws Exception {  
        Object objTmp;  
        Object val = null;  
        for (int i = 0; i < Array.getLength(objArr); i++) {  
            //注，如果是非数组的基本类型，则返回的是包装类型  
            objTmp = Array.get(objArr, i);  
            if (objTmp == null) {  
                val = null;  
            } else if (objTmp.getClass().isArray()) {//如果是数组  
  
                val = Array.newInstance(objTmp.getClass().getComponentType(), Array  
                        .getLength(objTmp));  
                //如果元素是数组，则递归调用  
                cloneArr(objTmp, val);  
            } else {//否则非数组  
                /* 
                 * 如果为基本类型或者是非Cloneable类型的引用类型，则直接对拷值 或 
                 * 者是对象的地址。没有实现Cloneable的引用类型会实行浅复制， 这对 
                 * 于像String不可变类来说是没有关系的，因为它们可以多实例或 多线程 
                 * 共享，但如果即没有实现Cloneable，又是可变以的类，浅复制 则会带来 
                 * 危险，因为这些类实例不能共享 ，一个实例里的改变会影响到 另一个实 
                 * 例。所以在使用克隆方案的时候一定要考虑可变对象的可克隆性，即需要 
                 * 实现Cloneable。 
                 *  
                 * 注，这里不能使用 objTmp.getClass.isPrimitive()来判断是元素是 
                 * 否是基本类型，因为objTmp是通过Array.get获得的，而Array.get返 
                 * 回的是Object 类型，也就是说如果是基本类型会自动转换成对应的包 
                 * 装类型后返回，所以 我们只能采用原始的类型来判断才行。 
                 */  
                if (objArr.getClass().getComponentType().isPrimitive()  
                        || !(objTmp instanceof Cloneable)) {//基本类型或非Cloneable引用类型  
                    val = objTmp;  
                } else if (objTmp instanceof Cloneable) {//引用类型，并实现了Cloneable  
                    /* 
                     *  用反射查找colone方法，注，先使用getDeclaredMethod获取自 
                     *  己类 中所定义的方法（包括该类所声明的公共、保护、默认访问 
                     *  及私有的 方法），如果没有的话，再使用getMethod，getMethod 
                     *  只能获取公有的方法，但还包括了从父类继承过来的公有方法 
                     */  
                    Method cloneMethod;
                    try {  
                        //先获取自己定义的clone方法  
                        cloneMethod = objTmp.getClass().getDeclaredMethod("clone",  
                                new Class[] {});  
  
                    } catch (NoSuchMethodException e) {  
                        //如果自身未定义clone方法，则从父类中找，但父类的clone一定要是public  
                        cloneMethod = objTmp.getClass()  
                                .getMethod("clone", new Class[] {});  
                    }  
                    cloneMethod.setAccessible(true);  
                    val = cloneMethod.invoke(objTmp, new Object[0]);  
                }  
            }  
            // 设置克隆数组元素值  
            Array.set(cloneArr, i, val);  
        }  
    }  
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		// 包装类型 OK
		Integer a = 12;
		Integer b = executor(a);
		a = 11;
		System.out.println(a + " " + b);
		
		String aa = "1";
		String bb = executor(aa);
		aa = "123";
		System.out.println(aa + " " + bb);
		
		// 基本类型数组 OK
		int ts [] = new int[] {1, 2};
		int newTs[] = executor(ts);
		ts[0] = 0;
		System.out.println(Arrays.toString(newTs));
		
		// 一维包装类型数组 OK
		Integer ts2 [] = new Integer[] {1, 2};
		Integer newTs2[] = executor(ts2);
		ts2[0] = 0;
		System.out.println(Arrays.toString(newTs2));
		
		// 二维包装类型数组 OK
		Integer ts3 [][] = new Integer[][] {{1, 2},{2, 90}};
		Integer newTs3[][] = (Integer[][])executor(ts3);
		ts3[0][0] = 0;
		for(Integer[] i : newTs3) {
			System.out.println(Arrays.toString(i));
		}
		
		// 三维包装类型数组 OK
		Integer ts4 [][][] = new Integer[][][] {{{1, 2},{2, 90}},{{1, 3},{3,5}}};
		Integer newTs4[][][] = executor(ts4);
		ts4[0][0][0] = 0;
		for(Integer[][] i : newTs4) {
			for(Integer[] j : i) {
				System.out.println(Arrays.toString(j));
			}
		}
		
	}
	
}

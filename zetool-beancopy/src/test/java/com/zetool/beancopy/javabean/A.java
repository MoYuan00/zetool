package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.util.CollectionUtils;

public class A {
	String name = "name is A";
    String password;
    List<Object> list = CollectionUtils.toList(new Object[] {"a", "aa"});
    List<Integer>[] listArray = new List[]
    		{CollectionUtils.toList(new Integer[] {11, 33}), 
    		CollectionUtils.toList(new Integer[] {11, 33}) };
    Integer objArray[] = {1, 2, 3};
    Integer multArray[][][] = {{{1, 3}, {3, 0}}, {{1, 3}, {9, 9}}};
    
}

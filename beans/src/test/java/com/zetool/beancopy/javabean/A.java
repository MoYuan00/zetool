package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.util.CollectionUtils;

public class A {
	public String name = "name is A";
    public String password = "aaa password";
    public List<Object> list = CollectionUtils.toList(new Object[] {"a", "aa"});
    public List<Integer>[] listArray = new List[]
    		{CollectionUtils.toList(new Integer[] {11, 33}), 
    		CollectionUtils.toList(new Integer[] {11, 33}) };
    public Integer objArray[] = {1, 2, 3};
    public Integer multArray[][][] = {{{1, 3}, {3, 0}}, {{1, 3}, {9, 9}}};
}

package com.zetool.beancopy.javabean;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.util.CollectionUtils;

@CopyFrom(sourceClass = A.class, thisFields ={"name","list", "listArray", "objArray", "multArray"})
public class B {
	String name = "name is B";
    String type = "class is B";
    List<Object> list;
    List<Integer>[] listArray = new List[]
    		{CollectionUtils.toList(new Integer[] {-1, -3}), 
    		CollectionUtils.toList(new Integer[] {-1, -3}) };
    Integer objArray[] = {-1, -1, -1};
    Integer multArray[][][] = {{{-1, -3}, {-3, -0}}, {{-1, -3}, {-9, -9}}};
}

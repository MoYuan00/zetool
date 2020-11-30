package com.zetool.beancopy.checkor;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(fromClass = A.class, fields ={"name","list"})
public class B {
	String name;
    String type;
    List<Object> list;
}

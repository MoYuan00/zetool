package com.zetool.beancopy.field.checkor;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(sourceClass = A.class, thisFields = {"name","list"})
public class B {
	String name;
    String type;
    List<Object> list;
}

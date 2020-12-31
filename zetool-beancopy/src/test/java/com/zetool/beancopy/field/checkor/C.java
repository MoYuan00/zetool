package com.zetool.beancopy.field.checkor;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(sourceClass = B.class, thisFields = {"list", "type"})
public class C {
	 String type;
	 List<Object> list;
}

package com.zetool.beancopy.checkor;

import java.util.List;

import com.zetool.beancopy.annotation.CopyFrom;

@CopyFrom(fromClass = B.class, fields = {"list", "type"})
public class C {
	 String type;
	 List<Object> list;
}

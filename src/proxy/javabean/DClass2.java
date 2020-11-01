package proxy.javabean;

import util.annotation.CopyFrom;

@CopyFrom(fromClass = CClass2.class,
fields = {"name", "where"})
public class DClass2 {
	String name = "D name";
	String where = "DClass2 where";
}

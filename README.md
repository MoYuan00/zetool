# copy evething
对象拷贝工具。

# 简单的拷贝class A copy to new class A
你只需要简单的这样的代码：
```java
A a = new A();
A a2 = Copier.copy(a); // clone new instance
Log.info(App2.class, "拷贝后的A为:" + FieldContext.toString(a2));
```
# 跨类的拷贝class A copy to new class B
对于不同类型间的 拷贝支持
设有如下类：(为了方便直接初始化了值)
```java
public class A {
	String name = "name is A";
    String password = "aaa password";
    List<Object> list = CollectionUtils.toList(new Object[] {"a", "aa"});
    List<Integer>[] listArray = new List[]
    		{CollectionUtils.toList(new Integer[] {11, 33}), 
    		CollectionUtils.toList(new Integer[] {11, 33}) };
    Integer objArray[] = {1, 2, 3};
    Integer multArray[][][] = {{{1, 3}, {3, 0}}, {{1, 3}, {9, 9}};
}

@CopyFrom(sourceClass = A.class, fields ={"name","list", "listArray", "objArray", "multArray"})
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

@CopyFrom(sourceClass = B.class, fields = {"list", "type"})
@CopyFrom(sourceClass = A.class, fields = {"list", "password"})
public class C {
	 String type = "";
	 String password = "ccc password";
	 List<Object> list;
}
```
> 通过注解CopyFrom标注你需要拷贝的类的类型。以及通过fields表示你需要拷贝过来的字段。
请注意：如果类型名在注解中指定了，而本类和来源类（被拷贝过来的类，注解中指定的类）。中不含有这个字段。为了避免这种情况发生。你可以在程序开始运行的时候使用类似如下代码进行检测。

> 如果发生注解不匹配的情况，将会抛出异常。这样能够保证你在程序运行时不会因为字段名修改过，或者类型不匹配而发生异常。
```java
Checkor.check(new SimpleClassScanner().addClassesByClassName(A.class.getName()));
```
拷贝只需要如下代码：
```java
A a = new A();
B b = Copier.copy(a, B.class);
Log.info(App2.class, "拷贝后的B为:" + FieldContext.toString(b));

C c = Copier.copy(a,  C.class);
Log.info(App2.class, "拷贝后的C为:" + FieldContext.toString(c));
```
如果你想拷贝所有字段，你可以不用填写fields的值。默认将会拷贝所有字段。

# 自定义日志
在实现日志的时候提供了抽出了一部分日志方法。
如果你想改变当前日志等级你可以使用如下代码，将日志等级调整为WORN。
```java
Log.LEVEL = Log.WORN;
```
当然我仅仅提供了控制台打印的日志实现类，
如果你想自己实现，或者接入好用的第三方日志类。
这也很简单，你只需要重新写一个类继承Log抽象类
然后实现其中的几个方法。
然后修改日志使用的实现类，通过如下代码：
```java
Log.logger = new StdLog();
```


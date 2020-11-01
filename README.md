# copy evething
对象拷贝工具。虽然说是个工具，但是这个工具的目标很远大，路还很长。

功能大致如下：
1. 同class间的拷贝
这个比较简单

2. 不同class间的拷贝
对于每个需要拷贝的对象source源，target目标。将source对象的实例拷贝给target。
对此做如下2种定义：对每一对<source,target>称为拷贝映射。source -> target(source copy to target) 或者 source <- target(target copy from source)。

实现目标：
1. 检查成功后，运行时实现copy A to B

2. 考虑如下实现方式：
    1. B b = CopyUtil.copy(A, BClass);// 直接通过工具类调用         √
    2. B b = new A().copy(BClass);// A中有某个方法，此方法再调用1.  
    3. B b = new A().copy(BClass);// A中有某个方法，此方法被动态代理并拦截下来
    4. User user = new AnyObject().coverToUser(); // 考虑在给定方法coverToUser上添加注解，然后扫描这个注解。通过注解内容获取目标类，然后代理这个方法并返回User对象。也就是将BClass信息添加到注解上。然后通过代理实现。
        1. User user = CopyProxy.poxy(new AnyObject()).coverToUser();// 返回了被代理后的AnyObject
        2. User user = new A().copyToUser();                    √
        // copyToUser: 内部调用CopyUtil.copy(this, User)    




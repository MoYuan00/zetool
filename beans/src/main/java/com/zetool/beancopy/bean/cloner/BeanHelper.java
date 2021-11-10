package com.zetool.beancopy.bean.cloner;

import java.beans.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * java bean 帮助类，获取一个类的属性
 */
public class BeanHelper {

    private Class<?> clazz;

    private BeanInfo beanInfo;

    private List<PropertyDescriptor> pds;

    public BeanHelper(Class<?> clazz) {
        this.clazz = clazz;
        try {
            this.beanInfo = Introspector.getBeanInfo(this.clazz);
            init();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        this.pds = new LinkedList<>();// 使用linkedList，考虑到会再进行增删，而不需要随机访问特性
        // 过滤掉getClass这个bean, 和只读只写bean
        for (PropertyDescriptor pd : this.beanInfo.getPropertyDescriptors()) {
            if(pd.getReadMethod() == null || pd.getWriteMethod() == null) continue;
            this.pds.add(pd);
        }
    }

    /**
     * 获取bean的属性描述器集合
     * @return
     */
    public List<PropertyDescriptor> getPropertyDescriptors() {
        return pds;
    }

    public static class Test{
        public static class Father{
            public String noGetSet;
            private String fatherName;
            public String getFatherName() {
                return fatherName;
            }

            public void setFatherName(String fatherName) {
                this.fatherName = fatherName;
            }

            public String getFakeGetName(){
                return null;
            }

            public void setFakeSetName(String fatherName){
            }
        }
        public static class A extends Father{
            private int tag = 1;
            private String name = "A name";
            private List<Integer> list = new ArrayList<>();
            public int getTag() {
                return tag;
            }
            public void setTag(int tag) {
                this.tag = tag;
            }
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public List<Integer> getList() {
                return list;
            }
            public void setList(List<Integer> list) {
                this.list = list;
            }
        }
        public static void main(String[] args) throws IntrospectionException {
            BeanHelper beanHelper = new BeanHelper(A.class);
            System.out.println(beanHelper.getPropertyDescriptors().size());
            for (PropertyDescriptor pd : beanHelper.getPropertyDescriptors()) {
                System.out.println("name:"+pd.getName());
                System.out.println("read method name:"+pd.getReadMethod().getName());
                System.out.println("display name:"+pd.getDisplayName());
            }

//            BeanInfo bif = Introspector.getBeanInfo(A.class);
//            PropertyDescriptor[] pd = bif.getPropertyDescriptors();
//            for(int i=0;i<pd.length;i++){
//                System.out.println("name:"+pd[i].getName());
//                System.out.println("read method name:"+pd[i].getReadMethod().getName());
//                System.out.println("display name:"+pd[i].getDisplayName());
//            }
//            MethodDescriptor[] md = bif.getMethodDescriptors();
//            for(int m = 0; m<md.length; m++){
//                System.out.println("name:"+md[m].getName());
//                System.out.println("method name:"+md[m].getMethod().getName());
//                System.out.println("displayname:"+md[m].getDisplayName());
//            }
//            BeanDescriptor bd = bif.getBeanDescriptor();
//            System.out.println("display name：" + bd.getDisplayName());
//            System.out.println("name：" + bd.getName());
//            EventSetDescriptor[] esd = bif.getEventSetDescriptors();
//            for(int m=0;m<esd.length;m++){
//                System.out.println("name:"+esd[m].getName());
//                System.out.println("method name:"+esd[m].getGetListenerMethod().getName());
//                System.out.println("method name:"+esd[m].getRemoveListenerMethod().getName());
//                System.out.println("method name:"+esd[m].getAddListenerMethod().getName());
//                System.out.println("displayname:"+esd[m].getDisplayName());
//            }
        }
    }
}

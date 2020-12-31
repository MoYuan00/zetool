package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.bean.BeanCloner;
import com.zetool.beancopy.bean.annotation.CloneBeanFor;
import com.zetool.beancopy.util.PerformanceGroupTimer;
import com.zetool.beancopy.util.PerformanceTimer;

public class TestDiffer {
    
    public static class A{
        private String name = "A name";
        private String password = "A password";
        private Double price = 1.0;
        private Integer count = null;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    @CloneBeanFor(sourceClassFor = A.class)
    public static class B {
        private String name = "B name";
        private Double price = 2.0;
        private Integer count = 2;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public static class C{
        private String name = "C name";
        private Double price = 3.0;
        private Integer count = 3;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    @CloneBeanFor(sourceClassFor = A.class, ignorePropertyNames = "name")
    public static class D{
        private String name = "D name";
        private Double price = 4.0;
        private Integer count = 4;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    @CloneBeanFor(sourceClassFor = A.class, requirePropertyNames = {"price", "name"})
    public static class E{
        private String name = "E name";
        private Double price = 5.0;
        private Integer count = 5;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public static void testDifferWithAnnotation(){
        A a = new A();
        B b = new B();
        b = BeanCloner.cloneSurfaceProperties(a, b);//.cloneSurfaceProperties(a, b);
    }

    public static void testDifferWithAnnotationIgnore(){
        A a = new A();
        D d = new D();
        d = BeanCloner.cloneSurfaceProperties(a, d);
    }

    public static void testDifferWithAnnotationRequire(){
        A a = new A();
        E e = new E();
        e = BeanCloner.cloneSurfaceProperties(a, e);
    }

    public static void testDiffer(){
        A a = new A();
        C c = new C();
        c = BeanCloner.cloneSurfaceProperties(a, c);
    }

    public static void main(String[] args) {
        int total = 1000000;
        int thread = 1000;
        PerformanceGroupTimer groupTimer = PerformanceGroupTimer.build(total, thread);
        groupTimer.build().setTag("testDiffer").setTask(()->{
            testDiffer();
            return true;
        });
        groupTimer.build().setTag("testDifferWithAnnotation").setTask(()->{
            testDifferWithAnnotation();
            return true;
        });
        groupTimer.build().setTag("testDifferWithAnnotationIgnore").setTask(()->{
            testDifferWithAnnotationIgnore();
            return true;
        });
        groupTimer.build().setTag("testDifferWithAnnotationRequire").setTask(()->{
            testDifferWithAnnotationRequire();
            return true;
        });
        groupTimer.run().shutdownNow();
    }
}

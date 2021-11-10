package com.zetool.beancopy.bean.cloner;

import com.zetool.beancopy.bean.BeanCloner;
import com.zetool.beancopy.field.FieldHelper;
import com.zetool.beancopy.util.CollectionUtils;
import com.zetool.beancopy.util.TimerInterval;
import net.sf.cglib.beans.BeanCopier;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Test {

    public static class Father{
        private String name = "father";
        private String[] tags = {null, null, "tag"};
        private List<String[]>[] homeTags = new List[] {
                CollectionUtils.toList(new String[]{null, "tag"}, null)
        };

        public List<String[]>[] getHomeTags() {
            return homeTags;
        }

        public void setHomeTags(List<String[]>[] homeTags) {
            this.homeTags = homeTags;
        }

        public String[] getTags() {
            return tags;
        }

        public void setTags(String[] tags) {
            this.tags = tags;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class A{
        private String name = "A";
        private Date time = new Date();
        private Long count = 1L;
        private String[] address = {"1", "1", "1"};
        private String[] nullObj = null;
        private Father[] fathers = {null, null, new Father()};
        private List<String> friends = CollectionUtils.toList(null, null, "123");
        private Map<Long, Father> prices = new HashMap<>();

        private List<List<String >> friendListNames = new ArrayList<>();

        public String[] getAddress() {
            return address;
        }

        public void setAddress(String[] address) {
            this.address = address;
        }

        public String[] getNullObj() {
            return nullObj;
        }

        public void setNullObj(String[] nullObj) {
            this.nullObj = nullObj;
        }

        public Father[] getFathers() {
            return fathers;
        }

        public void setFathers(Father[] fathers) {
            this.fathers = fathers;
        }

        public Map<Long, Father> getPrices() {
            return prices;
        }

        public void setPrices(Map<Long, Father> prices) {
            this.prices = prices;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public List<String> getFriends() {
            return friends;
        }

        public void setFriends(List<String> friends) {
            this.friends = friends;
        }

        public List<List<String>> getFriendListNames() {
            return friendListNames;
        }

        public void setFriendListNames(List<List<String>> friendListNames) {
            this.friendListNames = friendListNames;
        }
    }

    private static void testOwn(int n, int layout){
        TimerInterval timerInterval = TimerInterval.start();
        A a = new A();
        A b = new A();
        for (int i = 0; i < n; i++) {
            b = BeanCloner.cloneProperties(a, new A(), layout); // BeanCloner.cloneSurfaceProperties(a, new A());
        }
        System.out.println(layout + "used time:" + timerInterval.intervalMMTime());
        a.count = 2L;
        a.friends.add(2 + "");
        a.address[2] = "new";
        a.name = "a new name";
        a.friendListNames.add(CollectionUtils.toList("new friend name"));
        a.fathers[2].tags[0] = "new tag";
        a.fathers[2].homeTags[0].get(0)[0] = "new home tag";
        System.out.println(FieldHelper.toString(a));
        System.out.println(FieldHelper.toString(b));
    }

    private static void testOther(int n){
        TimerInterval timerInterval = TimerInterval.start();
        A a = new A();
        A b = new A();
        for (int i = 0; i < n; i++) {
            b = new A();
            BeanUtils.copyProperties(a, b);
        }
        System.out.println("Spring BeanUtils used time:" + timerInterval.intervalMMTime());
        a.count = 2L;
        a.friends.add(2 + "");
        a.address[2] = "new";
        a.name = "a new name";
        a.friendListNames.add(CollectionUtils.toList("new friend name"));
        a.fathers[2].tags[0] = "new tag";
        a.fathers[2].homeTags[0].get(0)[0] = "new home tag";
        System.out.println(FieldHelper.toString(a));
        System.out.println(FieldHelper.toString(b));
    }

    private static void testOtherCglib(int n){
        TimerInterval timerInterval = TimerInterval.start();
        A a = new A();
        A b = new A();
        for (int i = 0; i < n; i++) {
            b = new A();
            BeanCopier beanCopier = BeanCopier.create(A.class, A.class, false);
            beanCopier.copy(a, new A(), null);
        }
        System.out.println("cglib BeanCopier used time:" + timerInterval.intervalMMTime());
        a.count = 2L;
        a.friends.add(2 + "");
        a.address[2] = "new";
        a.name = "a new name";
        a.friendListNames.add(CollectionUtils.toList("new friend name"));
        a.fathers[2].tags[0] = "new tag";
        a.fathers[2].homeTags[0].get(0)[0] = "new home tag";
        System.out.println(FieldHelper.toString(a));
        System.out.println(FieldHelper.toString(b));
    }

    private static void testOwnThread(int n, int threadCount, int layout){
        TimerInterval timerInterval = TimerInterval.start();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for(int t = 0; t < threadCount; t++){
            new Thread(()->{
                A a = new A();
                A b = new A();
                for (int i = 0; i < n; i++) {
                    b = BeanCloner.cloneProperties(a, new A(), layout);  // BeanCloner.cloneSurfaceProperties(a, new A());
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(layout + "used time:" + timerInterval.intervalMMTime());
    }


    private static void testOtherThread(int n, int threadCount){
        TimerInterval timerInterval = TimerInterval.start();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for(int t = 0; t < threadCount; t++){
            new Thread(()->{
                A a = new A();
                A b = new A();
                for (int i = 0; i < n; i++) {
                    b = new A();
                    BeanUtils.copyProperties(a, b);
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Spring BeanUtils used time:" + timerInterval.intervalMMTime());
    }

    private static void testCglibThread(int n, int threadCount){
        TimerInterval timerInterval = TimerInterval.start();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for(int t = 0; t < threadCount; t++){
            new Thread(()->{
                A a = new A();
                A b = new A();
                for (int i = 0; i < n; i++) {
                    BeanCopier beanCopier = BeanCopier.create(A.class, A.class, false);
                    beanCopier.copy(a, new A(), null);
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cglib BeanCopier used time:" + timerInterval.intervalMMTime());
    }

    public static void main(String[] args) {
        int b = 0;
        for(int i = 0; i < 100000000; i++){// cpu预热
            int a = i * i;
            b = a * i - 2;
        }
        System.out.println("单线程运行：");
        int n = 1000000;
        testOther(n);
        testOtherCglib(n);
        testOwn(n, 1);
        testOwn(n, 5);
        testOwn(n, 6);


        System.out.println("多线程运行：");
        int threadCount = 5000;
        testOtherThread(n / threadCount, threadCount);
        testCglibThread(n / threadCount, threadCount);
        testOwnThread(n / threadCount, threadCount, 1);
        testOwnThread(n / threadCount, threadCount, 5);
        testOwnThread(n / threadCount, threadCount, 6);
    }
}

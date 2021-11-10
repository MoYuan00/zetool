package com.zetool.beancopy.util;

import java.util.concurrent.*;
import java.util.function.BooleanSupplier;

/**
 * 性能速度测试器
 */
public class PerformanceTimer {

    private TimerInterval timerInterval;

    private int threadCount = 1;

    private int total = 1;

    private ExecutorService executor;

    public static PerformanceTimer build(ExecutorService executor, int total, int threadCount){
        return new PerformanceTimer(executor, total, threadCount);
    }

    private PerformanceTimer(ExecutorService executor, int total, int threadCount){
        this.threadCount = threadCount;
        this.total = total;
        this.executor = executor;
    }

    private BooleanSupplier task;

    public PerformanceTimer setTask(BooleanSupplier booleanSupplier){
        task = booleanSupplier;
        return this;
    }

    private long spendMMTime = 0;

    private int successCount;

    public void run(){
        successCount = 0;
        timerInterval = TimerInterval.start();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        int count = total / threadCount;
        for (int t = 0; t < threadCount; t++) {
            //向Executor- Service提交一个 Callable对象
            final Future<Integer> futureRate = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
                    int successCount = 0;
                    for (int i = 0; i < count; i++) {
                        if(task.getAsBoolean()){
                            successCount++;
                        }
                    }
                    countDownLatch.countDown();
                    return successCount;
                }
            });
            try {
                successCount += futureRate.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        spendMMTime = timerInterval.intervalMMTime();
    }

    public void log(){
        System.out.println(String.format("%s->执行:%d, 线程数:%d, 成功:%d, 用时:%dmm",
                tag, total, threadCount, successCount, spendMMTime));
    }

    private String tag;

    public PerformanceTimer setTag(String message){
        tag = message;
        return this;
    }

}

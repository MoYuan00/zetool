package com.zetool.beancopy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一组性能测试
 */
public class PerformanceGroupTimer {

    /**
     * 每个测试的个数
     */
    private int eachCount;

    private List<PerformanceTimer> performanceTimerList;

    /**
     * 每个测试实验的线程数
     */
    private int eachThread;

    private ExecutorService executor;


    private PerformanceGroupTimer(int eachThread, int eachCount){
        this.eachCount = eachCount; this.eachThread = eachThread;
        executor = Executors.newSingleThreadExecutor();
        performanceTimerList = new ArrayList<>();
    }

    public static PerformanceGroupTimer build(int eachCount, int eachThread){
        return new PerformanceGroupTimer(eachThread, eachCount);
    }

    public PerformanceTimer build(){
        PerformanceTimer performanceTimer = PerformanceTimer.build(executor, eachCount, eachThread);
        performanceTimerList.add(performanceTimer);
        return performanceTimer;
    }

    public PerformanceGroupTimer run(){
        performanceTimerList.forEach(
                performanceTimer -> {
                    performanceTimer.run();
                    performanceTimer.log();
                }
        );
        return this;
    }

    public void shutdownNow(){
        executor.shutdownNow();
    }


}

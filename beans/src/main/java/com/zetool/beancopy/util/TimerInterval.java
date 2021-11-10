package com.zetool.beancopy.util;

/**
 * 计时器，记录时间
 */
public class TimerInterval {

    private long startTime;

    public static TimerInterval start(){
        return new TimerInterval();
    }

    private TimerInterval(){
        this.startTime = System.nanoTime();
    }

    /**
     * 返回纳秒时间差
     * @return
     */
    public long intervalNanoTime(){
        return System.nanoTime() - startTime;
    }

    /**
     * 返回毫秒时间差
     * @return
     */
    public long intervalMMTime(){
        return (System.nanoTime() - startTime) / 1000000;
    }

    /**
     * 重置计时器
     */
    public void reset(){
        this.startTime = System.nanoTime();
    }
}

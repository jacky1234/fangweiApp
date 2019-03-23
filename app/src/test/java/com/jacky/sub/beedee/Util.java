package com.jacky.sub.beedee;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 2018/11/3.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class Util {
    public static void log(Object info) {
        System.out.println(Thread.currentThread().getName() + "->" + info.toString());
    }

    public static void suspend(int second) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            if (countDownLatch.await(second, TimeUnit.SECONDS)) {
                log("the count reached zero");
            } else {
                log("the waiting time elapsed before the count reached zero");
            }
        } catch (InterruptedException e) {
            System.out.println("thread interrupt" + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

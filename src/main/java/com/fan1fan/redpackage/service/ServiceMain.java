/*
package com.fan1fan.redpackage.service;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Service
public class ServiceMain {

    static List<Integer> REDPACKAGE_LIST = new ArrayList<>();
    static ConcurrentHashMap<String, Integer> REDPACKAGE_RESULT = new ConcurrentHashMap<>();
    static int TOTAL_MONEY = 0;

    static AtomicInteger count = new AtomicInteger(0);

    static ExecutorService executorSerive = Executors.newSingleThreadExecutor();
    static ExecutorService cexecutorSerive = Executors.newCachedThreadPool();

    static BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

    public static void give(int money, int count) {
        for (int i = count; i > 1; i--) {
            int max = money - TOTAL_MONEY - i;
            int redPackage = max == 0 ? 1 : new Random().nextInt(max) + 1;
            REDPACKAGE_LIST.add(redPackage);
            TOTAL_MONEY += redPackage;
        }
        int left = money - TOTAL_MONEY;
        REDPACKAGE_LIST.add(left);
        TOTAL_MONEY += left;
        System.out.println(REDPACKAGE_LIST);
        consumer();
    }

    public static void provide(String name) {
        try {
            linkedBlockingQueue.put(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void consumer() {
        executorSerive.submit(() -> {
            for (; ; ) {
                String name = linkedBlockingQueue.take();
                if (!StringUtils.isEmpty(name)) {
                    int index = count.incrementAndGet();
                    int redPackage = REDPACKAGE_LIST.get(index - 1);
                    System.out.println(name + ":" + index + ":" + redPackage);
                    REDPACKAGE_RESULT.put(name, redPackage);
                }
            }
        });
    }


    public static void main(String[] args) {

        try {
            //发红包
            give(100, 6);

            //开始抢红包
            TimeUnit.MILLISECONDS.sleep(1);
            int threadCount = 1000;
            CountDownLatch countDownLatch = new CountDownLatch(threadCount);
            for (int i = 0; i < threadCount; i++) {
                cexecutorSerive.submit(() -> {
                    countDownLatch.countDown();
                    provide(Thread.currentThread().getName());
                });
            }
            countDownLatch.await();

            System.out.println(REDPACKAGE_RESULT);
            executorSerive.shutdown();
            cexecutorSerive.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
*/

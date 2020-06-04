package com.fan1fan.redpackage.service;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fan1fan
 */
@org.springframework.stereotype.Service
public class Service {

    /**抢红包用户列表**/
    private static Set<String> userSet = new HashSet<>();
    /**红包拆分结果**/
    private static List<Integer> redPackageList = new ArrayList<>();
    /**红包个数**/
    private static AtomicInteger redPackageCount = new AtomicInteger(0);
    /**红包分配结果**/
    private static ConcurrentHashMap<String, Integer> redPackageResult = new ConcurrentHashMap<>();
    /**消费者线程池**/
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    /**红包队列**/
    private static BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

    /**
     * 发红包
     * @param money 总额
     * @param count 个数
     */
    public void give(int money, int count) {
        //红包发放基本校验跳过
        redPackageList.clear();
        redPackageCount.set(0);
        int totalMoney = 0;
        for (int i = count; i > 1; i--) {
            int max = money - totalMoney - i;
            int redPackage = max == 0 ? 1 : new Random().nextInt(max) + 1;
            redPackageList.add(redPackage);
            totalMoney += redPackage;
        }
        int left = money - totalMoney;
        redPackageList.add(left);
        System.out.println("红包拆分结果" + redPackageList);
        consumer();
    }

    /**
     * 进队
     * @param userId 领红包用户userId
     */
    public void provide(String userId) {
        try {
            //限流，例如guava实现，跳过，只做去重判断
            if(!userSet.contains(userId)){
                userSet.add(userId);
                linkedBlockingQueue.put(userId);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 出队
     */
    public void consumer() {
        executorService.submit(() -> {
            for (; ; ) {
                String name = linkedBlockingQueue.take();
                if (!StringUtils.isEmpty(name)) {
                    int index = redPackageCount.incrementAndGet();
                    //不用remove()方法，存在线程不安全，用get()即可
                    int redPackage = redPackageList.get(index - 1);
                    redPackageResult.put(name, redPackage);
                    System.out.println("第" + index + "个抢到红包的是"+ name + "，金额：" + redPackage + "分");
                }
            }
        });
    }

    /**
     * 抢红包结果
     * @param userId 领红包用户userId
     * @return 0表示没抢到了
     */
    public int robResult(String userId){
        return redPackageResult.get(userId) == null ? 0 : redPackageResult.get(userId);
    }

}

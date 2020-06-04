package com.fan1fan.redpackage.controller;

import com.fan1fan.redpackage.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private Service service;

    /**
     * 发红包
     * @param money 单位（分）
     * @param count
     */
    @GetMapping("give")
    public void give(int money,int count){
        service.give(money,count);
    }

    /**
     * 抢红包
     * @param userId
     */
    @PostMapping("rob")
    public void rob(String userId){
        service.provide(userId);
    }

    /**
     * 轮询抢红包结果
     * @param userId
     * @return
     */
    @GetMapping("robResult")
    public int robResult(String userId){
        return service.robResult(userId);
    }


}

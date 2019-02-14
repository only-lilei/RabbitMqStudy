package com.foxconn.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import com.foxconn.rabbitmq.bean.Demo;
import com.foxconn.rabbitmq.constant.RabbitConstant;
import com.foxconn.rabbitmq.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author only-lilei
 * @create 2018-05-15 下午5:03
 **/
@RestController
@RequestMapping("/test")
public class TestRabbitController {
    @Autowired
    private RabbitService rabbitService;
    @GetMapping
    public String sendMessage(){
        Demo demo = new Demo();
        demo.setAge(12);
        demo.setName("张数");
        rabbitService.send(demo,RabbitConstant.ROUTING_KEY_TEST);
        return "aaaa";
    }

    @GetMapping("/sendFanoutExchangeMessage")
    public String sendFanoutExchangeMessage(){
        rabbitService.sendFanoutExchangeMessage("hello world");
        return "aaaa";
    }
}

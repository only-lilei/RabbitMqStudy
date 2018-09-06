package com.foxconn.rabbitmq.controller;

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
        rabbitService.send("hello world",RabbitConstant.ROUTING_KEY_TEST);
        return "aaaa";
    }

    @GetMapping("/sendFanoutExchangeMessage")
    public String sendFanoutExchangeMessage(){
        rabbitService.sendFanoutExchangeMessage("hello world");
        return "aaaa";
    }
}

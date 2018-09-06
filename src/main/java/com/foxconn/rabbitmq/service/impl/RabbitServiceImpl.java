package com.foxconn.rabbitmq.service.impl;

import com.foxconn.rabbitmq.constant.RabbitConstant;
import com.foxconn.rabbitmq.service.RabbitService;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RabbitServiceImpl implements RabbitService {
    @Autowired
    private RabbitTemplate rabbitMessagingTemplate;
    @Autowired
    private AmqpAdmin admin;

    /**
     * 申明交换器
     */
    @PostConstruct
    public void initExchange() {
        admin.declareExchange(RabbitConstant.TEST_EXCHANGE);
        admin.declareExchange(RabbitConstant.FANOUT_EXCHANGE);
    }

    @Override
    public void send(Object message) {
        rabbitMessagingTemplate.convertAndSend(RabbitConstant.TEST_EXCHANGE.getName(), message);
    }

    @Override
    public void send(Object message, String routingKey) {
        rabbitMessagingTemplate.convertAndSend(RabbitConstant.TEST_EXCHANGE.getName(), routingKey, message);
    }


    @Override
    public void sendFanoutExchangeMessage(Object message) {
        rabbitMessagingTemplate.convertAndSend(RabbitConstant.FANOUT_EXCHANGE.getName(), "",message);
    }
}
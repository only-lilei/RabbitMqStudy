package com.foxconn.rabbitmq.listener;

import com.foxconn.rabbitmq.constant.RabbitConstant;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestRabbitListener {
    private static Logger log = LoggerFactory.getLogger(TestRabbitListener.class);
    @Autowired
    private AmqpAdmin admin;


    //初始化队列
    @PostConstruct
    public void initListener() {
        Queue applyAutoReleaseQueue = new Queue(RabbitConstant.TEST_QUEUE);
        Binding binding = BindingBuilder.bind(applyAutoReleaseQueue).to(RabbitConstant.TEST_EXCHANGE).with(RabbitConstant.ROUTING_KEY_TEST);
        admin.declareExchange(RabbitConstant.TEST_EXCHANGE);
        admin.declareQueue(applyAutoReleaseQueue);
        admin.declareBinding(binding);
    }


    @RabbitListener(queues = RabbitConstant.TEST_QUEUE)
    public void applyAutoRelease(Message message, Channel channel) {
        System.out.println(message.getBody());
        throw new RuntimeException("123");
    }
}

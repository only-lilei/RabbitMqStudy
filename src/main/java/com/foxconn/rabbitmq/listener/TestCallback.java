package com.foxconn.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author only-lilei
 * @create 2018-06-22 下午2:25
 * 使用场景：
 *
 * 如果消息没有到exchange,则confirm回调,ack=false
 * 如果消息到达exchange,则confirm回调,ack=true
 * exchange到queue成功,则不回调return
 * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
 **/
@Component
public class TestCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            System.out.println("消息发送成功:" + correlationData);
        } else {
            System.out.println("消息发送失败:" + s);
        }

    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println(message.toString() + " 发送失败");
    }
}

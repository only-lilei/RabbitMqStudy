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
import java.io.IOException;

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
        admin.declareQueue(applyAutoReleaseQueue);
        admin.declareBinding(binding);


        Queue fanoutQueue = new Queue(RabbitConstant.FANOUT_QUEUE);
        Binding fanoutQueueBinding = BindingBuilder.bind(fanoutQueue).to(RabbitConstant.FANOUT_EXCHANGE);
        admin.declareQueue(fanoutQueue);
        admin.declareBinding(fanoutQueueBinding);

        Queue fanoutQueue2 = new Queue(RabbitConstant.FANOUT_QUEUE2);
        Binding fanoutQueueBinding2 = BindingBuilder.bind(fanoutQueue2).to(RabbitConstant.FANOUT_EXCHANGE);
        admin.declareQueue(fanoutQueue2);
        admin.declareBinding(fanoutQueueBinding2);
    }


    @RabbitListener(queues = RabbitConstant.TEST_QUEUE)
    public void applyAutoRelease(Message message, Channel channel) {

        System.out.println(message.getBody());
        //在ack设置为manual模式时  有如下消息确认方案
        try {
            //成功确认
            //deliveryTag:该消息的index
            //multiple：是否批量. true：将一次性ack所有小于deliveryTag的消息。
            //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            //失败确认
            //deliveryTag:该消息的index。
            //multiple：是否批量. true：将一次性拒绝所有小于deliveryTag的消息。
            //requeue：被拒绝的是否重新入队列。
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);

            //deliveryTag:该消息的index。
            //requeue：被拒绝的是否重新入队列。
            //channel.basicNack 与 channel.basicReject 的区别在于basicNack可以批量拒绝多条消息，而basicReject一次只能拒绝一条消息。
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //throw new RuntimeException("a");
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_QUEUE)
    public void fanoutQueueRelease(Message message) {
        System.out.println(message.getBody());
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_QUEUE2)
    public void fanoutQueueRelease2(Message message) {
        System.out.println(message.getBody());
    }
}

package com.foxconn.rabbitmq.constant;

import org.springframework.amqp.core.TopicExchange;

/**
 * @author only-lilei
 * @create 2018-05-15 下午4:57
 **/
public class RabbitConstant {
    public static final TopicExchange TEST_EXCHANGE = new TopicExchange("com.foxconn.rabbitMq.test");

    //queue
    public static final String TEST_QUEUE = "test-queue";

    public static final String ROUTING_KEY_TEST = "test";
}

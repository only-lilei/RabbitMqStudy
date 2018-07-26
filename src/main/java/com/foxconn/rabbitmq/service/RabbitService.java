package com.foxconn.rabbitmq.service;

public interface RabbitService {

    /**
     * 发送消息
     *
     * @param message
     */
    void send(Object message);

    /**
     * 发送消息
     *
     * @param message
     * @param routingKey
     */
    void send(Object message, String routingKey);

}
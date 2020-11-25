package com.kangfawei.publish_subscribe;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author kangfawei
 */
public class PrintLogToConsole {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        try {
            Channel channel = ConnectionUtil.getChannel(RabbitMQConstant.RABBITMQ_HOST,
                    RabbitMQConstant.RABBITMQ_USERNAME, RabbitMQConstant.RABBITMQ_PASSWORD);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println("等待接收消息......");

            DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("print-----" + message);
            });

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}

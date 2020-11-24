package com.kangfawei.hello_world;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Receive {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannel(RabbitMQConstant.RABBITMQ_HOST,
                RabbitMQConstant.RABBITMQ_USERNAME,
                RabbitMQConstant.RABBITMQ_PASSWORD);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // lambda表达式写法
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println(" [x] Received '" + message + "'");
//        };

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        boolean autoAck = true;  // 自动应答
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }
}

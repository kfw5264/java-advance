package com.kangfawei.work_queue;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Worker2 {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionUtil.getFactory(RabbitMQConstant.RABBITMQ_HOST,
                RabbitMQConstant.RABBITMQ_USERNAME,
                RabbitMQConstant.RABBITMQ_PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // lambda表达式写法
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println(" [x] Received '" + message + "'");
//        };

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[x] Done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) throws InterruptedException {
        for (char c : task.toCharArray()) {
            if (c == '.') Thread.sleep(1000);
        }
    }
}
